package com.example.profnotes.ui.changeStatus

import android.os.Bundle
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
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.ChangeStatusViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeStatusFragment :
    BaseFragment<FragmentChangeStatusBinding, ChangeStatusViewModel>() {

    private lateinit var adapterRV :RVAdapterStatus
    override val viewModel:ChangeStatusViewModel by viewModels()

    private val args by navArgs<ChangeStatusFragmentArgs>()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangeStatusBinding  =
        FragmentChangeStatusBinding.inflate(inflater, container, false)


    private fun setStatusBottom(){
        binding.btChangeStatusNote.isEnabled = viewModel.getStatusButton()
    }

    private fun backToHome(){
        binding.btBackToHome.setOnClickListener{
            findNavController().navigate(R.id.action_changeStatusFragment_to_navigation_home)
        }
    }

    private fun setAllStatus(){
        adapterRV = RVAdapterStatus(object : statusActionListener{
            override fun clickStatus(statusNote: StatusNote) {
                viewModel.setSelectedStatus(status = statusNote.status)
                viewModel.setStatusButton(true)
                setStatusBottom()
            }
        })
        with(binding){
            containerStatus.layoutManager= LinearLayoutManager(requireContext())
            containerStatus.adapter = adapterRV
        }
        adapterRV.setStatus(viewModel.getAllStatusEX())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBottom()
        setAllStatus()
        backToHome()
    }

    override fun onStart() {
        super.onStart()
        changeStatusNote()
        mainActivity.showBottomBar(false)
    }

    private fun changeStatusNote() {
        binding.btChangeStatusNote.setOnClickListener{
            viewModel.updateStatusNote(Notes(args.selectedNote.id,
                args.selectedNote.title,
                args.selectedNote.date,
                viewModel.getSelectedStatus(),
                args.selectedNote.description))
            Toast.makeText(requireContext(),"Статус заметки успешно обновлен",Toast.LENGTH_LONG).show()
            setFalseBottom()
        }
        binding.btCancel.setOnClickListener{
            viewModel.updateStatusNote(args.selectedNote)
            Toast.makeText(requireContext(),"Изменение статуса заметки отменено",Toast.LENGTH_LONG).show()
            setFalseBottom()
        }
    }
    private fun setFalseBottom(){
        viewModel.setSelectedStatus(status = "")
        viewModel.setStatusButton(false)
        setStatusBottom()
    }
}