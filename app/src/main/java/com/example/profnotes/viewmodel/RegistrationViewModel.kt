package com.example.profnotes.viewmodel

import com.example.profnotes.data.models.SignInUser
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    var _name = MutableStateFlow("")
    var name = _name.asStateFlow()

    var _login = MutableStateFlow("")
    var login = _login.asStateFlow()

    var _password = MutableStateFlow("")
    var password = _password.asStateFlow()

    var _successSignIn = MutableStateFlow<Boolean?>(null)
    val successSignIn = _successSignIn.asStateFlow()

    fun registration() {
        if (_name.value.isNotEmpty() && _password.value.isNotEmpty() && _login.value.isNotEmpty()) {
            launchSafety {
                showLoading()
                runCatching {
                    authRepository.signIn(
                        SignInUser(
                            userName = name.value,
                            password = password.value,
                            login = login.value,
                        )
                    )
                }.onSuccess {
                    hideLoading()
                    _successSignIn.value = true
                }.onFailure {
                    hideLoading()
                    _successSignIn.value = false
                }
            }
        }
    }

}