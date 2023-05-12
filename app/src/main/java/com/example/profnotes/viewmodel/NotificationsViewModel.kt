package com.example.profnotes.viewmodel

import com.example.profnotes.data.models.User
import com.example.profnotes.data.models.UserFindRequest
import com.example.profnotes.data.repo.AuthRepository
import com.example.profnotes.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.mrz.profnotes.core.isValid
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private var checkUserInfo = false
    private var checkSearchFriends = false
    private val _searchUsers = MutableStateFlow<List<UserFindRequest>>(listOf())
    val searchUsers = _searchUsers.asStateFlow()

    val searchUsername = MutableStateFlow("")

    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile = _userProfile.asStateFlow()
    private val _usersFriends = MutableStateFlow<List<UserFindRequest>>(listOf())
    val usersFriends = _usersFriends.asStateFlow()


    fun getCheckUserInfo(): Boolean = checkUserInfo

    fun setChekUserInfo(status: Boolean) {
        checkUserInfo = status
    }

    fun getCheckSearchFriends(): Boolean = checkSearchFriends

    fun setCheckSearchFriends(status: Boolean) {
        checkSearchFriends = status
    }

    fun findUsers() {
        launchSafety {
            showLoading()
            _searchUsers.value = authRepository.findFriends(searchUsername.value) ?: listOf()
            hideLoading()
        }
    }

    fun getData() {
        launchSafety {
            showLoading()
            _userProfile.value = authRepository.getData()
            if (userProfile.value != null && userProfile.value!!.friendsId.isNotEmpty()) {
                _usersFriends.value =
                    authRepository.getListFriends(userProfile.value!!.friendsId) ?: listOf()
            }
            hideLoading()
        }
    }

    fun updateUserInfo(userName: String, password: String) {
        if (userName.isValid() && password.isValid()) {
            launchSafety {
                showLoading()
                authRepository.updateUser(userName, password)
                hideLoading()
            }
        }
    }

    fun deleteFriend(idFriend: Long) {
        launchSafety {
            showLoading()
            runCatching {
                authRepository.deleteFriend(idFriend)
            }.onSuccess {
                getData()
            }
            hideLoading()
        }
    }

    fun addFriend(idFriend: Long) {
        launchSafety {
            showLoading()
            runCatching {
                authRepository.addFriend(idFriend)
            }.onSuccess {
                getData()
            }
            hideLoading()
        }
    }
}