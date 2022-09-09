package com.example.profnotes.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentLoginBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.LoginViewModel
import ru.mrz.profnotes.core.spanString

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater, container, false)

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spanText()
        goRegistration()
    }

    private fun goRegistration(){
        binding.btRegistration.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun spanText(){
        binding.tvLoginWithoutAuth.apply {
            text = text.spanString(
                startIndex = 4,
                endIndex = 24,
                color = requireContext().getColor(R.color.green)
            )
        }
        binding.tvRecoverPassword.apply {
            text = text.spanString(
                startIndex = 24,
                endIndex = 43,
                color = requireContext().getColor(R.color.yellow)
            )
        }
        binding.btnLogin.apply {
            isEnabled = true
            setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        mainActivity.showBottomBar(false)
    }
}
