package com.example.minimoneybox.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.minimoneybox.utils.NavigationCommand


abstract class BaseFragment<DB : ViewBinding, VM : BaseViewModel> :
    Fragment() {
    protected val TAG: String = this::class.java.simpleName
    private var originalInputMode: Int? = null
    open var softInputAdjustResize: Boolean = false
    protected lateinit var binding: DB
    abstract val viewModel: VM
    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        binding = initBinding(inflater, container, savedInstanceState)
        initComponents(binding)
        addListeners(binding)
        addObservers(binding)

        viewModel.errorNotifier.observe(viewLifecycleOwner, Observer {
            errorHandler(it)
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer
        {
//            if (it) {
//                UiUtils.showLoadingDialog(context, false, "")
//            } else {
//                UiUtils.dismissDialog()
//            }
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

        modifySoftInput()

        return binding.root
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

                is NavigationCommand.WithArgs ->
                    TODO()
                is NavigationCommand.FirstOpen ->
                    findNavController().navigate(
                        command.directions,
                        NavOptions.Builder().setPopUpTo(command.destinationId, true).build()
                    )
            }
        })

    }


    private fun closeKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
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
        restoreSoftInput()
    }

    private fun restoreSoftInput() {
        originalInputMode?.let {
            activity?.window?.setSoftInputMode(it)
        }
    }

    private fun modifySoftInput() {
        if (softInputAdjustResize) {
            originalInputMode = activity?.window?.attributes?.softInputMode

            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//            Tracker.logDebug(
//                TAG,
//                "Setting softInputMode as adjustResize: ${WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE}"
//            )
//            Tracker.logDebug(
//                TAG,
//                "Current softInputMode as adjustResize: ${activity?.window?.attributes?.softInputMode}"
//            )
        }
    }
}