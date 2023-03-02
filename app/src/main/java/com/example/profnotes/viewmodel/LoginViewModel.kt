package com.example.profnotes.viewmodel

import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private var login: String = ""
    private var password: String = ""

    fun getLogin(): String = login
    fun getPassword(): String = password

    fun setLogin(_login: String) {
        login = _login
    }

    fun setPassword(_password: String) {
        password = _password
    }
}