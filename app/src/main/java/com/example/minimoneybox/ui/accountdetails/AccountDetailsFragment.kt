package com.example.minimoneybox.ui.accountdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.minimoneybox.R
import com.example.minimoneybox.extensions.showToast
import com.example.minimoneybox.model.InvestorProductModel
import com.example.minimoneybox.ui.useraccounts.UserAccountsViewModel

class AccountDetailsFragment : Fragment() {

    private val args: AccountDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_account_details, container, false)
        return view
    }


}
