package com.example.profnotes.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.profnotes.R
import com.example.profnotes.core.CardRecommendationPageTransformer
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.model.NewNote
import com.example.profnotes.model.helper
import com.example.profnotes.note_for_viewpager.NoteVPAdapter
import com.example.profnotes.note_for_viewpager.noteVPActionListener
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.ui.home.adapter.NoteActionListener
import com.example.profnotes.ui.home.adapter.RVAdapter
import com.example.profnotes.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun inflateViewBinding(
          inflater: LayoutInflater,
          container: ViewGroup?) =
          FragmentHomeBinding.inflate(inflater, container, false)

    private lateinit var adapter : NoteVPAdapter
    private lateinit var adapterRV: RVAdapter
    private lateinit var rvAllNotes : RecyclerView

    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDateNow()
        setViewPager()
        setRecyclerView()
        searchLocalNotes()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateNow(){
        val df = SimpleDateFormat("EEEE, dd MMMM",Locale("ru"))
        binding.tvTodayDescription.text = df.format(Calendar.getInstance().time)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewPager(){
        adapter = NoteVPAdapter(object : noteVPActionListener{
            override fun clickNote(newNote: NewNote) {
                val elem = helper(newNote,null)
                val action = HomeFragmentDirections.actionNavigationHomeToAddNoteFragment(elem)
                findNavController().navigate(action)
            }
        })
        setMarginViewPager()
        adapter.setItems(viewModel.getLstForViewPager())
        with(binding){
            vpNewnotes.adapter = adapter
            tvCountNewNotes.text = adapter.itemCount.toString()
            tvNewsDescription.text = "${adapter.itemCount.toString()} новые заметки"
        }
    }

    private fun setMarginViewPager(){
        with(binding.vpNewnotes){
            val pageMargin = resources.getDimensionPixelSize(R.dimen.margin_25dp)
            val viewPagerPadding = pageMargin + resources.getDimensionPixelSize(R.dimen.margin_8dp)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 4
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(CardRecommendationPageTransformer())
                addTransformer(MarginPageTransformer(pageMargin))
            })
            setPadding(viewPagerPadding, 10, viewPagerPadding, 10)
        }
    }

    private fun setElemToRV(){
        lifecycleScope.launch {
            viewModel.getAllNotes().collectLatest {
                adapterRV.setdataNote(it)
                binding.tvCountNotes.text = adapterRV.itemCount.toString()
            }
        }
    }

    private fun setElementsToRecyclerViewSearch(query:String){
        val searchQuery = "%$query%"
        lifecycleScope.launch {
            viewModel.searchInLocalNotes(searchQuery).collectLatest {
                adapterRV.setdataNote(it)
                binding.tvCountNotes.text = adapterRV.itemCount.toString()
            }
        }
    }

    private fun setRecyclerView(){
        adapterRV = RVAdapter(object: NoteActionListener{
            override fun deleteNote(note: Notes){
                viewModel.delNote(note)
                Toast.makeText(requireContext(),"Заметка успешно удалена", Toast.LENGTH_SHORT).show()
                setElemToRV()
            }
            override fun changeNote(note: Notes) {
                val elem = helper(null,note)
                val action = HomeFragmentDirections.actionNavigationHomeToAddNoteFragment(elem)
                findNavController().navigate(action)
            }
            override fun changestatusNote(note: Notes) {
                val action = HomeFragmentDirections.actionNavigationHomeToChangeStatusFragment(note)
                findNavController().navigate(action)
            }
        })
        rvAllNotes = binding.rvAllnotes
        rvAllNotes.layoutManager = LinearLayoutManager(requireContext())
        rvAllNotes.adapter = adapterRV
        setElemToRV()
    }

    private fun searchLocalNotes(){
        binding.etSearchNotes.doAfterTextChanged {
            setElementsToRecyclerViewSearch(binding.etSearchNotes.text.toString())
        }
    }
}

