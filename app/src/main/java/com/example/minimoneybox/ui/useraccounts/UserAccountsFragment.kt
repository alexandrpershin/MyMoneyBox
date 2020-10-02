package com.example.minimoneybox.ui.useraccounts

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimoneybox.R
import com.example.minimoneybox.api.ErrorType
import com.example.minimoneybox.databinding.FragmentUserAccountsBinding
import com.example.minimoneybox.extensions.*
import com.example.minimoneybox.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserAccountsFragment : BaseFragment<FragmentUserAccountsBinding, UserAccountsViewModel>() {

    private lateinit var accountsAdapter : UserAccountsAdapter

    override val viewModel: UserAccountsViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserAccountsBinding = FragmentUserAccountsBinding.inflate(inflater, container, false)

    override fun initComponents(binding: FragmentUserAccountsBinding) {
        progressBar = binding.partialProgress.progressBar

        accountsAdapter = UserAccountsAdapter(requireContext())

        binding.rvAccounts.apply {
            accountsAdapter.onItemClick = { productId ->
                viewModel.goToProductAccountDetails(productId)
            }
            adapter = accountsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun addListeners(binding: FragmentUserAccountsBinding) {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.fetchInvestorProductsFromServer()
                stopRefreshing()
            }
        }
    }

    override fun addObservers(binding: FragmentUserAccountsBinding) {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            if (user != null && user.userName.isNotEmpty()) {
                with(binding) {
                    tvGreeting.text =
                        getGreetingMessage(user.userName).also { tvGreeting.makeVisible() }
                }
            }
        })

        viewModel.getInvestorProduct().observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                with(binding) {
                    accountsAdapter.updateData(data.productAccounts)
                    tvTotalPlanValue.text = getTotalPlanValueMessage(it.totalPlanValue)
                }
            }
        })

    }

    private fun getGreetingMessage(userName: String): SpannableStringBuilder {
        val greeting = getString(R.string.text_greeting)
        val string = "$greeting $userName!"
        val spannable = SpannableStringBuilder(string)

        spannable.setSpan(
            ForegroundColorSpan(R.color.shakespeare.getColor()),
            greeting.length, greeting.length + userName.length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannable
    }

    private fun getTotalPlanValueMessage(totalPlanValue: Double): SpannableStringBuilder {
        val planValueLabel = getString(R.string.text_total_plan_value)
        val planValue = totalPlanValue.parseToString()
        val string = "$planValueLabel $planValue"
        val spannable = SpannableStringBuilder(string)

        spannable.setSpan(
            ForegroundColorSpan(R.color.shakespeare.getColor()),
            string.lastIndex - planValue.length, string.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannable
    }

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.GenericError -> {
                showErrorMessage(
                    errorType.message
                        ?: getString(R.string.error_message_unexpected_error)
                )
            }

            is ErrorType.InternetError -> {
                showErrorMessage(getString(errorType.resId))
            }
        }

    }


}