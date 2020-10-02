package com.example.minimoneybox.ui.accountdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.databinding.FragmentAccountDetailsBinding
import com.example.minimoneybox.extensions.getString
import com.example.minimoneybox.extensions.playBounceAnimation
import com.example.minimoneybox.extensions.showErrorMessage
import com.example.minimoneybox.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AccountDetailsFragment :
    BaseFragment<FragmentAccountDetailsBinding, AccountDetailsViewModel>() {

    private val args: AccountDetailsFragmentArgs by navArgs()

    override val viewModel: AccountDetailsViewModel by viewModel { parametersOf(args.accountDetailsId) }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountDetailsBinding =
        FragmentAccountDetailsBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentAccountDetailsBinding) {
        progressBar = binding.partialProgress.progressBar
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
                    tvPlanValue.text = R.string.text_pound_value.getString(model.planValue)
                    tvMoneybox.text = R.string.text_pound_value.getString(model.moneybox)
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
            is ErrorType.GenericError -> showErrorMessage(errorType.message)
            is ErrorType.InternetError -> showErrorMessage(errorType.message)
        }
    }


}
