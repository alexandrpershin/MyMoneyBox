package com.example.minimoneybox.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.databinding.FragmentSplashScreenBinding
import com.example.minimoneybox.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentSplashScreenBinding) {
        hideSystemBottomNavigationAndStatusBar()
        binding.animSplashScreen.playAnimation()
    }

    private fun hideSystemBottomNavigationAndStatusBar() {
        requireActivity().window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }


    private fun showSystemBottomNavigationAndStatusBar() {
        requireActivity().window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        showSystemBottomNavigationAndStatusBar()
    }

    override fun addListeners(binding: FragmentSplashScreenBinding) {}

    override fun addObservers(binding: FragmentSplashScreenBinding) {}

    override fun errorHandler(errorType: ErrorType) {}

}