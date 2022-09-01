package com.example.profnotes.ui.changeStatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentChangeStatusBinding
import com.example.profnotes.model.StatusNote
import com.example.profnotes.mynote_rv.RVAdapterStatus
import com.example.profnotes.mynote_rv.statusActionListener
import com.example.profnotes.viewmodel.ChangeStatusViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeStatusFragment : Fragment() {

    private var _binding: FragmentChangeStatusBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterRV :RVAdapterStatus

    private val viewModel:ChangeStatusViewModel by viewModels()

    private val args by navArgs<ChangeStatusFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setStatusBottom(){
        binding.btChangeStatusNote.isEnabled = viewModel.getStatusButton()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBottom()
        adapterRV = RVAdapterStatus(object : statusActionListener{
            override fun clickStatus(statusNote: StatusNote) {
                viewModel.setSelectedStatus(status = statusNote.status)
                viewModel.setStatusButton(true)
                setStatusBottom()
            }
        })
        setAllStatus()
        binding.btChangeStatusNote.setOnClickListener{
            viewModel.updateStatusNote(Notes(args.selectedNote.id,
                                             args.selectedNote.title,
                                             args.selectedNote.date,
                                             viewModel.getSelectedStatus(),
                                             args.selectedNote.description))
            Toast.makeText(requireContext(),"Статус заметки успешно обновлен",Toast.LENGTH_LONG).show()
            viewModel.setSelectedStatus(status = "")
            viewModel.setStatusButton(false)
            setStatusBottom()
        }

        binding.btCancel.setOnClickListener{
            viewModel.updateStatusNote(args.selectedNote)
            Toast.makeText(requireContext(),"Изменение статуса заметки отменено",Toast.LENGTH_LONG).show()
            viewModel.setSelectedStatus(status = "")
            viewModel.setStatusButton(false)
            setStatusBottom()
        }
        backToHome()
    }

    private fun backToHome(){
        binding.btBackToHome.setOnClickListener{
            findNavController().navigate(R.id.action_changeStatusFragment_to_navigation_home)
        }
    }

    private fun setAllStatus(){
        with(binding){
            containerStatus.layoutManager= LinearLayoutManager(requireContext())
            containerStatus.adapter = adapterRV
        }
        adapterRV.setStatus(viewModel.getAllStatusEX())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ChangeStatusFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ChangeStatusFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}