package com.example.profnotes.ui.addNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentAddLocalNoteBinding
import com.example.profnotes.viewmodel.AddLocalNoteViewModel
import com.example.profnotes.viewmodel.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLocalNoteFragment(
    private val vm:AddNoteViewModel)
    : BaseFragment<FragmentAddLocalNoteBinding,AddLocalNoteViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddLocalNoteBinding.inflate(inflater, container, false)

    override val viewModel: AddLocalNoteViewModel by viewModels()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setETValue()
//    }
//
//    private fun setETValue(){
//        with(binding){
//            etDateNote.setText(vm.getDateLocal())
//            etTitleNote.setText(vm.getTitleLocal())
//            etDescriptionNote.setText(vm.getDescriptionLocal())
//        }
//    }
//
//    private fun getNewValue(){
//        with(binding){
//            etTitleNote.doAfterTextChanged {
//                vm.setTitleLocal(etTitleNote.text.toString())
//                vm.setLocalNote(Notes(vm.getIdLocal(),
//                                        title = etTitleNote.text.toString(),
//                                        date = etDateNote.text.toString(),
//                                        status = "Новое",
//                                        description = etDescriptionNote.text.toString()))
//            }
//            etDescriptionNote.doAfterTextChanged {
//                vm.setDescriptionLocal(etDescriptionNote.text.toString())
//                vm.setLocalNote(Notes(vm.getIdLocal(),
//                                        title = etTitleNote.text.toString(),
//                                        date = etDateNote.text.toString(),
//                                        status = "Новое",
//                                        description = etDescriptionNote.text.toString()))
//            }
//            etDateNote.doAfterTextChanged {
//                vm.setDateLocal(etDateNote.text.toString())
//                vm.setLocalNote(Notes(vm.getIdLocal(),
//                                        title = etTitleNote.text.toString(),
//                                        date = etDateNote.text.toString(),
//                                        status = "Новое",
//                                        description = etDescriptionNote.text.toString()))
//            }
//        }
//    }
//
//    fun getViewModel(): AddNoteViewModel {
//        return vm
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        mainActivity.showBottomBar(false)
//        getNewValue()
//    }
}
