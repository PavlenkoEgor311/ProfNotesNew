package com.example.profnotes.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private var checkUserInfo = false
    private var checkSearchFriends = false
    private var itemsOfColorsTheme = false

    fun getCheckUserInfo():Boolean = checkUserInfo

    fun setChekUserInfo(status:Boolean){
        checkUserInfo = status
    }

    fun getCheckSearchFriends():Boolean = checkSearchFriends

    fun setCheckSearchFriends(status: Boolean){
        checkSearchFriends = status
    }

    fun getItemsOfColorsTheme():Boolean = itemsOfColorsTheme

    fun setItemsOfColorsTheme(status: Boolean){
        itemsOfColorsTheme = status
    }

    fun getColorList():List<Int> = authRepository.getColorList()
}