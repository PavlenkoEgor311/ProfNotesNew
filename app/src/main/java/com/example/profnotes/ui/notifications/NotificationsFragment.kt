package com.example.profnotes.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.profnotes.databinding.FragmentNotificationsBinding
import com.example.profnotes.ui.core.BaseFragment

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>() {

    override val viewModel:NotificationsViewModel by viewModels()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding  = FragmentNotificationsBinding.inflate(inflater, container, false)

    override fun onStart() {
        super.onStart()
    }
}