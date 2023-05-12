package com.example.profnotes.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentLoginBinding
import com.example.profnotes.ui.core.BaseFragment
import com.example.profnotes.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.mrz.profnotes.core.spanString

@SuppressLint("ResourceType")
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spanText()
        listeners()
        setupLoading()
    }

    override fun onStart() {
        super.onStart()
        hideBottomNavigation()
    }

    private fun spanText() {
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
    }

    private fun listeners() {
        with(binding) {
            etEmailOrPhone.doAfterTextChanged {
                viewModel._login.value = etEmailOrPhone.text.toString()
            }
            etPassword.doAfterTextChanged {
                viewModel._password.value = etPassword.text.toString()
            }
            btnLogin.apply {
                isEnabled = true
                setOnClickListener {
                    if (viewModel.checkData()) {
                        viewModel.login()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Введите корректные данные! Поля должны быть не менее 5 символов",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            btRegistration.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }
            lifecycleScope.launchWhenStarted {
                viewModel.onSuccessLogin.collect { isSuccess ->
                    when (isSuccess) {
                        true -> {
                            navigateToHome()
                        }
                        false -> {
                            Toast.makeText(
                                requireContext(),
                                "Введите корректные данные или повторите позже",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.onSuccessLogin.value = null
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        val options =
            NavOptions.Builder().setPopUpTo(findNavController().graph.startDestinationId, true)
                .build()
        findNavController().navigate(R.id.navigation_home, null, options)
    }
}
