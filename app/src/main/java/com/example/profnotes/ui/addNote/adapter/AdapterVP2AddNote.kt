package com.example.profnotes.ui.addNote.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.profnotes.ui.addNote.AddNoteFragment
//private val onSaveNote: () -> Unit
interface FragmentListener{
    fun onFragmentActivated(fragment:Fragment)
}
class AdapterVP2AddNote(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager,lifecycle) {

    private val lst : MutableList<Fragment> =ArrayList()
    private val lstTitle : MutableList<String> =ArrayList()

    fun addFragment(fragment: Fragment, title: String){
        lst.add(fragment)
        lstTitle.add(title)
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun createFragment(position: Int): Fragment {
        return lst[position]
    }

    fun getPageTitle(position: Int): CharSequence? {
        return lstTitle[position]
    }
}