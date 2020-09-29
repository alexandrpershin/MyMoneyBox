package com.example.minimoneybox.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minimoneybox.databinding.FragmentSplashScreenBinding
import com.example.minimoneybox.ui.BaseFragment
import com.example.minimoneybox.ui.ErrorType
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentSplashScreenBinding) {
        hideSystemBottomNavigationAndStatusBar()
        binding.animSplashScreen.playAnimation()
    }

    private fun hideSystemBottomNavigationAndStatusBar() {
        requireActivity().window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    override fun addListeners(binding: FragmentSplashScreenBinding) {}

    override fun addObservers(binding: FragmentSplashScreenBinding) {}

    override fun errorHandler(errorType: ErrorType) {}

}