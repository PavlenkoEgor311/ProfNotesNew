package com.example.profnotes.viewmodel

import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.model.request.LoginResponse
import com.example.profnotes.model.request.UserRequest
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    var onSuccessLogin = MutableStateFlow<Boolean?>(null)
    private lateinit var loginResponse: LoginResponse

    fun login() {
        launchSafety {
            showLoading()
            runCatching {
                loginResponse = authRepository.logIn(
                    user = UserRequest(
                        login = login.value,
                        password = password.value
                    )
                )
            }.onSuccess {
                authRepository.setUserToken(loginResponse.token)
                authRepository.setUserID(loginResponse.idUser)
                onSuccessLogin.value = true
                hideLoading()
            }.onFailure {
                onSuccessLogin.value = false
                hideLoading()
            }
        }
    }

}