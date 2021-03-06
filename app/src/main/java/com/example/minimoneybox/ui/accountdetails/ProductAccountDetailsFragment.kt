package com.example.minimoneybox.ui.accountdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.databinding.FragmentAccountDetailsBinding
import com.example.minimoneybox.extensions.playBounceAnimation
import com.example.minimoneybox.extensions.showErrorMessage
import com.example.minimoneybox.ui.BaseFragment
import com.example.minimoneybox.ui.cutomview.ToolbarPrimaryButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductAccountDetailsFragment :
    BaseFragment<FragmentAccountDetailsBinding, ProductAccountDetailsViewModel>() {

    private val args: ProductAccountDetailsFragmentArgs by navArgs()

    override val viewModel: ProductAccountDetailsViewModel by viewModel { parametersOf(args.accountDetailsId) }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountDetailsBinding =
        FragmentAccountDetailsBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentAccountDetailsBinding) {
        binding.toolbar.title = getString(R.string.title_individual_account_screen)
        binding.toolbar.setNavigationIcon(ToolbarPrimaryButton.Type.Back) {
            viewModel.goBack()
        }

    }

    override fun addListeners(binding: FragmentAccountDetailsBinding) {
        binding.btnAddMoney.setOnClickListener {
            val amount = 10 // this is hardcode amount, real one should be from user input
            viewModel.addMoneyToMoneyBox(amount)
        }
    }

    override fun addObservers(binding: FragmentAccountDetailsBinding) {
        viewModel.getInvestorProductModel().observe(viewLifecycleOwner, Observer { model ->
            model?.let {
                with(binding) {
                    tvPlanValue.text = getString(R.string.text_pound_value, model.planValue)
                    tvMoneybox.text = getString(R.string.text_pound_value, model.moneybox)
                    tvAccountName.text = model.friendlyName
                }
            }
        })

        viewModel.playAnimationLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvMoneybox.playBounceAnimation()
        })
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.GenericError -> {
                showErrorMessage(errorType.message ?: getString(errorType.resId))
            }

            is ErrorType.InternetError -> showErrorMessage(getString(errorType.resId))
        }
    }


}
