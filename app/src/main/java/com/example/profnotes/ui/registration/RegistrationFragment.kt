package com.example.profnotes.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentRegistrationBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.RegistrationViewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding,RegistrationViewModel>() {

    override val viewModel:RegistrationViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding =
        FragmentRegistrationBinding.inflate(inflater, container, false)

    private fun backToLogin(){
        binding.btLoginWithAcc.setOnClickListener{
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToLogin()
    }

    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(false)
    }
}