package com.example.profnotes.viewmodel

import com.example.profnotes.viewmodel.core.BaseViewModel

class AddLocalNoteViewModel : BaseViewModel() {
    private var titleInput:String = ""
    private var dateInput:String = ""
    private var descriptionInput:String = ""

    fun getTitle():String = titleInput
    fun getDate():String = dateInput
    fun getDescription():String = descriptionInput

    fun setTitle(title:String){
        titleInput = title
    }
    fun setDate(date:String){
        dateInput = date
    }
    fun setDescription(description:String){
        descriptionInput = description
    }
}