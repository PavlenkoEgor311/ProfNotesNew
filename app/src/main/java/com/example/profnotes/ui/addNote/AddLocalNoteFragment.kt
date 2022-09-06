package com.example.profnotes.ui.addNote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.data.models.Notes
import com.example.profnotes.databinding.FragmentAddLocalNoteBinding
import com.example.profnotes.databinding.FragmentAddNoteBinding
import com.example.profnotes.viewmodel.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLocalNoteFragment : BaseFragment<FragmentAddLocalNoteBinding,AddNoteViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?) =
        FragmentAddLocalNoteBinding.inflate(inflater, container, false)

    override val viewModel: AddNoteViewModel by activityViewModels()

    private fun setETValue(){
        with(binding){
            etDateNote.setText(viewModel.getDate())
            etTitleNote.setText(viewModel.getTitle())
            etDescriptionNote.setText(viewModel.getDescription())
        }
    }

    private fun getNewValue(){
        with(binding){
            etTitleNote.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.setTitle(etTitleNote.text.toString())
                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
                }
                override fun afterTextChanged(s: Editable?) {
                    viewModel.setTitle(etTitleNote.text.toString())
                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
                }
            })
            etDescriptionNote.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.setDescription(etDescriptionNote.text.toString())
                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
                }
                override fun afterTextChanged(s: Editable?) {
                    viewModel.setDescription(etDescriptionNote.text.toString())
                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
                }
            })
            etDateNote.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.setDate(etDateNote.text.toString())
                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
                }
                override fun afterTextChanged(s: Editable?) {
                    viewModel.setDate(etDateNote.text.toString())
                    viewModel.setNote(Notes(0, title = etTitleNote.text.toString(), date = etDateNote.text.toString(), status = "Новое", description = etDescriptionNote.text.toString()))
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        setETValue()
        getNewValue()
    }
}


//            val etList = listOf(binding.etTitleNote,binding.etDescriptionNote,etDateNote)
//            val funcList = listOf(viewModel.setTitle(etTitleNote.text.toString()),
//                                  viewModel.setDescription(etDescriptionNote.text.toString()),
//                                  viewModel.setDate(etDateNote.text.toString()))
//            etList.forEachIndexed{
//                    i, el->
//                getText(el,funcList[i])
//            }
/////////////////////////////////////////
// private fun getText(editText: EditText, until: Unit) {
//        with(binding) {
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                until
//                viewModel.setNote(
//                    Notes(
//                        0,
//                        title = etTitleNote.text.toString(),
//                        date = etDateNote.text.toString(),
//                        status = "Новое",
//                        description = etDescriptionNote.text.toString()
//                    )
//                )
//            }
//            override fun afterTextChanged(s: Editable?) {
//                until
//                viewModel.setNote(
//                    Notes(
//                        0,
//                        title = etTitleNote.text.toString(),
//                        date = etDateNote.text.toString(),
//                        status = "Новое",
//                        description = etDescriptionNote.text.toString()
//                    )
//                )
//            }
//        })
//    }
//    }
