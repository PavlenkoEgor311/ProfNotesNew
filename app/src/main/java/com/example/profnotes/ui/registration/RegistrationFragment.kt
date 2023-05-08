package com.example.profnotes.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.profnotes.databinding.FragmentRegistrationBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>() {

    override val viewModel: RegistrationViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding =
        FragmentRegistrationBinding.inflate(inflater, container, false)

    private fun backToLogin() {
        binding.btLoginWithAcc.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToLogin()
        setupListener()
        showCircularProgress()
    }

    override fun onStart() {
        super.onStart()
        hideBottomNavigation()
        hideCircularProgress()
        setupLoading()
    }

    private fun setupListener() {
        with(binding) {
            binding.btnLogin.setOnClickListener {
                viewModel.registration()
            }
            etNameUser.doAfterTextChanged {
                viewModel._name.value = etNameUser.text.toString()
            }
            etPassword.doAfterTextChanged {
                viewModel._password.value = etPassword.text.toString()
            }
            etEmailOrPhone.doAfterTextChanged {
                viewModel._login.value = etEmailOrPhone.text.toString()
            }
            lifecycleScope.launchWhenStarted {
                viewModel.successSignIn.collect { success ->
                    when (success) {
                        true -> {
                            Toast.makeText(
                                requireContext(),
                                "Вы успешно зарегистрировались. Теперь войдите в свой аккаунт",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        }
                        false -> {
                            Toast.makeText(
                                requireContext(),
                                "Проверьте данные или повторите позднее",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel._successSignIn.value = null
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}