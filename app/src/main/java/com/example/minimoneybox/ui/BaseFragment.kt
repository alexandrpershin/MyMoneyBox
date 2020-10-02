package com.example.minimoneybox.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.extensions.setVisible
import com.example.minimoneybox.extensions.showErrorMessage
import com.example.minimoneybox.extensions.showSnackBar
import com.example.minimoneybox.utils.NavigationCommand


abstract class BaseFragment<DB : ViewBinding, VM : BaseViewModel> :
    Fragment() {
    protected val TAG: String = this::class.java.simpleName

    private var binding: DB? = null

    abstract val viewModel: VM

    protected open var progressBar: ProgressBar? = null

    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DB

    abstract fun initComponents(binding: DB)
    abstract fun addListeners(binding: DB)
    abstract fun addObservers(binding: DB)
    abstract fun errorHandler(errorType: ErrorType)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        initComponents(binding!!)
        addListeners(binding!!)
        addObservers(binding!!)

        viewModel.errorNotifier.observe(viewLifecycleOwner, Observer {
            errorHandler(it)
            handleTokenExpiration(it)
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer { visible ->
            progressBar?.setVisible(visible)
        })

        viewModel.showSnackBar.observe(viewLifecycleOwner, Observer { message ->
            showSnackBar(getString(message))
        })
        viewModel.forceKeyboardState.observe(viewLifecycleOwner, Observer
        { keyboardState ->
            keyboardState?.let { safeKeyboardState ->
                when (safeKeyboardState) {
                    KeyboardState.Show -> openKeyboard()
                    KeyboardState.Hide -> closeKeyboard()
                }
            }
        })

        return binding!!.root
    }

    private fun handleTokenExpiration(errorType: ErrorType) {
        if (errorType is ErrorType.TokenExpired) {
            viewModel.goFirstScreen(R.id.loginFragment)
            showErrorMessage(getString(errorType.resId))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer<NavigationCommand> { command ->
            when (command) {
                is NavigationCommand.To ->
                    findNavController().navigate(command.directions)
                is NavigationCommand.BackTo ->
                    findNavController().navigate(command.destinationId)
                is NavigationCommand.Back ->
                    findNavController().popBackStack().also {
                        if (!it) {
                            requireActivity().finish()
                        }
                    }

                is NavigationCommand.WithArgs -> {
                    findNavController().navigate(command.destinationId, command.args)
                }
                is NavigationCommand.FirstOpen -> {
                    val navHostFragment: NavHostFragment =
                        requireActivity().supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
                    val inflater = navHostFragment.navController.navInflater
                    val graph = inflater.inflate(R.navigation.main_nav_graph)
                    graph.startDestination = command.startDestination
                    findNavController().graph = graph
                }
            }
        })

    }

    private fun closeKeyboard() {
        activity?.let { safeActivity ->
            val view = safeActivity.currentFocus
            view?.let { v ->
                val imm =
                    safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    private fun openKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}