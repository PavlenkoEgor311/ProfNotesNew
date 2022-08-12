package com.example.profnotes.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.profnotes.MainActivity
import com.example.profnotes.R
import com.example.profnotes.core.gone
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.model.Note
import com.example.profnotes.mynote_rv.RVAdapter
import com.example.profnotes.note_for_viewpager.NoteVPAdapter
import com.google.android.material.behavior.SwipeDismissBehavior
import ru.mrz.profnotes.core.spanString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteVPAdapter
    private lateinit var viewPager2: ViewPager2

    private val adapterRV by lazy { RVAdapter() }
    private lateinit var rvAllNotes : RecyclerView

    private val mainActivity by lazy { requireActivity() as MainActivity}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity.setNavigati()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = NoteVPAdapter(this)
        viewPager2 = binding.vpNewnotes
        viewPager2.adapter = adapter

        rvAllNotes = binding.rvAllnotes
        rvAllNotes.layoutManager = LinearLayoutManager(requireContext())
        rvAllNotes.adapter = adapterRV

//        binding.vpNewnotes.setPageTransformer(
//            CompositePageTransformer()
//        )

        val note1 = Note(1,"Выполнение дз к понедельнику", "17:00","Новое", "Сделать все")
        val note2 = Note(2,"Выполнение дз к вторнику", "11:00","Завершено", "Сделать все")
        val note3 = Note(3,"Выучить англ", "","В работе", "Что-то запомнить")
        val note4 = Note(4,"Выполнение дз к вторнику", "11:00","Завершено", "Сделать все")
        val note5 = Note(5,"Выполнение дз к вторнику", "11:00","Завершено", "Сделать все")
        val lstNotes = listOf(note1,note2,note3,note4,note5)
        adapterRV.setdataNote(lstNotes)

        binding.apply {
            tvCountNewNotes.text = "${adapter.itemCount}"
            tvCountNotes.text = "${lstNotes.size}"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}