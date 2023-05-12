package com.example.profnotes.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.profnotes.core.gone
import com.example.profnotes.core.show
import com.example.profnotes.data.models.UserFindRequest
import com.example.profnotes.databinding.FragmentNotificationsBinding
import com.example.profnotes.ui.addNote.screen.ProfileFriends
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.mrz.profnotes.core.toEditable

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>() {

    override val viewModel: NotificationsViewModel by viewModels()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        showCircularProgress()
        viewModel.getData()
        listeners()
        setOpenOrCloseChangeUserInfo()
        hideCircularProgress()
    }

    private fun listeners() {
        with(binding) {
            btnExit.setOnClickListener {
                logout()
            }
        }
    }

    private fun closeChangeUserInfo() {
        with(binding) {
            etChangePassword.gone()
            etNameUser.gone()
            btnSaveData.gone()
        }
    }

    private fun openChangeUserInfo() {
        with(binding) {
            etChangePassword.show()
            etNameUser.show()
            btnSaveData.show()
        }
    }

    private fun closeSearchUserFriends() {
        with(binding) {
            etSearchFriendsUser.gone()
            rvFriendsUser.gone()
            cvMyFriends.gone()
            btnFindFriend.gone()
        }
    }

    private fun openSearchUserFriends() {
        with(binding) {
            etSearchFriendsUser.show()
            rvFriendsUser.show()
            cvMyFriends.show()
            btnFindFriend.show()
        }
    }

    private fun setOpenOrCloseChangeUserInfo() {
        if (viewModel.getCheckUserInfo()) {
            openChangeUserInfo()
        } else {
            closeChangeUserInfo()
        }
        lifecycleScope.launch {
            viewModel.searchUsers.collect {
                binding.rvFriendsUser.setContent {
                    SetupSearchFriends(it)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.userProfile.collect { user ->
                if (user != null) {
                    with(binding) {
                        tvUserName.text = user.username
                        tvUserLogin.text = user.login
                        etNameUser.text = user.username.toEditable()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.usersFriends.collect {
                binding.cvMyFriends.setContent {
                    SetupMyFriends(it)
                }
            }
        }
    }

    private fun setOpenOrCloseSearchUserFriends() {
        if (viewModel.getCheckSearchFriends())
            openSearchUserFriends()
        else
            closeSearchUserFriends()
    }

    private fun ui() {
        with(binding) {
            tvChangeUserInfo.setOnClickListener {
                viewModel.setChekUserInfo(!viewModel.getCheckUserInfo())
                setOpenOrCloseChangeUserInfo()
            }
            tvSearchFriends.setOnClickListener {
                viewModel.setCheckSearchFriends(!viewModel.getCheckSearchFriends())
                setOpenOrCloseSearchUserFriends()
            }
            sDarkTheme.setOnCheckedChangeListener { _, state ->
                Toast.makeText(requireContext(), state.toString(), Toast.LENGTH_SHORT).show()
            }
            sPushNotifications.setOnCheckedChangeListener { _, state ->
                Toast.makeText(requireContext(), state.toString(), Toast.LENGTH_SHORT).show()
            }
            etSearchFriendsUser.doAfterTextChanged {
                viewModel.searchUsername.value = etSearchFriendsUser.text.toString()
            }
            btnFindFriend.setOnClickListener {
                viewModel.findUsers()
            }
            btnSaveData.setOnClickListener {
                viewModel.updateUserInfo(
                    etNameUser.text.toString(),
                    etChangePassword.text.toString()
                )
            }
        }
    }

    @Composable
    private fun SetupMyFriends(userFindRequests: List<UserFindRequest>) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 10.dp)
        ) {
            userFindRequests.forEach { friend ->
                ProfileFriends(
                    friend.username,
                    friend.id,
                    true,
                    addFriendId = {},
                    deleteFriend = {
                        viewModel.deleteFriend(it)
                    },
                    clickable = false,
                    defaultSelect = true,
                    click = { false }
                )
            }
        }
    }

    @Composable
    private fun SetupSearchFriends(userFindRequests: List<UserFindRequest>) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 10.dp)
        ) {
            userFindRequests.forEach { findFriend ->
                ProfileFriends(
                    findFriend.username,
                    findFriend.id,
                    true,
                    addFriendId = {
                        viewModel.addFriend(it)
                    },
                    deleteFriend = {},
                    clickable = false,
                    defaultSelect = true,
                    click = { false }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ui()
    }
}
