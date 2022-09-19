package com.example.profnotes.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.profnotes.R
import com.example.profnotes.core.gone
import com.example.profnotes.core.visible
import com.example.profnotes.databinding.FragmentNotificationsBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.ui.notifications.adapter.AdapterChangeColorTheme
import com.example.profnotes.ui.notifications.adapter.ColorActionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>() {

    override val viewModel:NotificationsViewModel by viewModels()
    private lateinit var adapter: AdapterChangeColorTheme
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding  = FragmentNotificationsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHome()
        setOpenOrCloseChangeUserInfo()
        setColorRecyclerView()
        setColorsLst()
    }

    private fun setColorRecyclerView(){
            adapter = AdapterChangeColorTheme(object: ColorActionListener{
                override fun changeColor(color: String) {
                    Toast.makeText(requireContext(),"Меняем цвет на $color",Toast.LENGTH_SHORT).show()
                }
            })
            adapter.setItems(viewModel.getColorList())
            binding.rvColorTheme.layoutManager = GridLayoutManager(requireContext(),5)
            binding.rvColorTheme.adapter = adapter
    }

    private fun backToHome(){
        binding.btBackToHome.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_notifications_to_navigation_home)
        }
    }

    private fun closeChangeUserInfo(){
        with(binding){
            etLoginUser.gone()
            etNameUser.gone()
        }
    }

    private fun openChangeUserInfo(){
        with(binding){
            etLoginUser.visible()
            etNameUser.visible()
        }
    }
    private fun closeSearchUserFriends(){
        with(binding){
            etSearchFriendsUser.gone()
            rvFriendsUser.gone()
        }
    }

    private fun openSearchUserFriends(){
        with(binding){
            etSearchFriendsUser.visible()
            rvFriendsUser.visible()
        }
    }

    private fun setOpenOrCloseChangeUserInfo(){
        if (viewModel.getCheckUserInfo())
            openChangeUserInfo()
        else
            closeChangeUserInfo()
    }

    private fun setColorsLst(){
        if (viewModel.getItemsOfColorsTheme())
            binding.rvColorTheme.visible()
        else
            binding.rvColorTheme.gone()
    }

    private fun setOpenOrCloseSearchUserFriends(){
        if (viewModel.getCheckSearchFriends())
            openSearchUserFriends()
        else
            closeSearchUserFriends()
    }

    private fun ui(){
        with(binding){
            tvChangeUserInfo.setOnClickListener{
                viewModel.setChekUserInfo(!viewModel.getCheckUserInfo())
                setOpenOrCloseChangeUserInfo()
            }
            tvSearchFriends.setOnClickListener{
                viewModel.setCheckSearchFriends(!viewModel.getCheckSearchFriends())
                setOpenOrCloseSearchUserFriends()
            }
            sDarkTheme.setOnCheckedChangeListener{ _, _ ->
                Toast.makeText(requireContext(),"qweree",Toast.LENGTH_SHORT).show()
            }
            sPushNotifications.setOnCheckedChangeListener{ _, _ ->
                Toast.makeText(requireContext(),"ytyty",Toast.LENGTH_SHORT).show()
            }
            tvChangeColorTheme.setOnClickListener{
                viewModel.setItemsOfColorsTheme(!viewModel.getItemsOfColorsTheme())
                setColorsLst()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ui()
    }
}