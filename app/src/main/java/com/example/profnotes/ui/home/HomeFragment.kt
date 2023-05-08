package com.example.profnotes.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.profnotes.R
import com.example.profnotes.core.CardRecommendationPageTransformer
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.model.TransitNote
import com.example.profnotes.note_for_viewpager.NoteVPAdapter
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyGlobalNoteShow
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyLocalNoteEdit
import com.example.profnotes.ui.viewingNote.ViewingNoteFragment.Companion.keyLocalNoteShow
import com.example.profnotes.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    private lateinit var adapter: NoteVPAdapter
    override val viewModel: HomeViewModel by viewModels()

    @SuppressLint("MutableCollectionMutableState")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLoading()
        lifecycleScope.launch {
            showCircularProgress()
            showBottomNavigation()
            setDateNow()
            setViewPager()
            searchLocalNotes()
            updateListLocalNote()
            delay(3000)
            hideCircularProgress()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateNow() {
        val df = SimpleDateFormat("EEEE, dd MMMM", Locale("ru"))
        binding.tvTodayDescription.text = df.format(Calendar.getInstance().time)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    private fun updateListLocalNote() {
        lifecycleScope.launch {
            delay(500)
            viewModel.getAllNotes().collectLatest { listNotes ->
                binding.tvCountNotes.text = listNotes.size.toString()
                setLocalNote(listNotes)
            }
        }
    }

    private fun setLocalNote(localListNote: List<Notes>) {
        binding.rvMyNote.setContent {
            Column {
                localListNote.forEach {
                    ItemNote(
                        note = it,
                        onClickDel = { deleteLocalNote(it) },
                        onClickChangeNote = {
                            findNavController().navigate(
                                HomeFragmentDirections.actionNavigationHomeToViewingNoteFragment(
                                    TransitNote.Local(it, keyLocalNoteEdit)
                                )
                            )
                        },
                        onClickChangeStatus = {
                            findNavController().navigate(
                                HomeFragmentDirections.actionNavigationHomeToChangeStatusFragment(it)
                            )
                        },
                        onClickShow = {
                            findNavController().navigate(
                                HomeFragmentDirections.actionNavigationHomeToViewingNoteFragment(
                                    TransitNote.Local(it, keyLocalNoteShow)
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    private fun deleteLocalNote(note: Notes) {
        viewModel.delNote(note)
        Toast.makeText(requireContext(), "Заметка успешно удалена", Toast.LENGTH_SHORT).show()
        updateListLocalNote()
    }

    @SuppressLint("SetTextI18n")
    private fun setViewPager() {
        adapter = NoteVPAdapter(onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToViewingNoteFragment(
                    TransitNote.Global(
                        it,
                        keyGlobalNoteShow
                    )
                )
            )
        })
        setMarginViewPager()
        adapter.setItems(viewModel.getLstForViewPager())
        with(binding) {
            vpNewnotes.adapter = adapter
            tvCountNewNotes.text = adapter.itemCount.toString()
            tvNewsDescription.text = "${adapter.itemCount} новые заметки"
        }
    }

    private fun setMarginViewPager() {
        with(binding.vpNewnotes) {
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

    private fun setElementsToRecyclerViewSearch(query: String) {
        val searchQuery = "%$query%"
        lifecycleScope.launch {
            viewModel.searchInLocalNotes(searchQuery).collectLatest {
                setLocalNote(it)
                binding.tvCountNotes.text = it.size.toString()
            }
        }
    }

    private fun searchLocalNotes() {
        binding.etSearchNotes.doAfterTextChanged {
            setElementsToRecyclerViewSearch(binding.etSearchNotes.text.toString())
        }
    }

    override fun onDestroy() {
        binding.vpNewnotes.adapter = null
        super.onDestroy()
    }

    companion object {
        const val STATUS_COMPLETED = "Выполнено"
        const val STATUS_NEW = "Новое"
        const val STATUS_COMPLETED_RED = "Завершено"
        const val STATUS_POSTEPONED = "Отложено"
    }
}


//    private fun setRecyclerView() {
//        adapterRV = RVAdapter(object : NoteActionListener {
//            override fun deleteNote(note: Notes) {
//                viewModel.delNote(note)
//                Toast.makeText(requireContext(), "Заметка успешно удалена", Toast.LENGTH_SHORT)
//                    .show()
//                setElemToRV()
//            }
//
//            override fun changeNote(note: Notes) {
//                val elem = TransitionNote(null, null, "")
//                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToAddNoteFragment())
//            }
//
//            override fun changestatusNote(note: Notes) {
//                val action = HomeFragmentDirections.actionNavigationHomeToChangeStatusFragment(note)
//                findNavController().navigate(action)
//            }
//        })
//        rvAllNotes = binding.rvAllnotes
//        rvAllNotes.layoutManager = LinearLayoutManager(requireContext())
//        rvAllNotes.adapter = adapterRV
//        setElemToRV()
//    }

//    private fun setElemToRV() {
//        lifecycleScope.launch {
//            viewModel.getAllNotes().collectLatest {
//                adapterRV.setdataNote(it)
//                binding.tvCountNotes.text = adapterRV.itemCount.toString()
//            }
//        }
//    }