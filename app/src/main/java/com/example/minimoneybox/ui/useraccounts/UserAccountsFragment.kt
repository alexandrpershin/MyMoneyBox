package com.example.minimoneybox.ui.useraccounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.minimoneybox.R
import com.example.minimoneybox.ui.login.LoginFragmentDirections

class UserAccountsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_user_accounts, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAccountDetails).setOnClickListener {

            val directions =
                UserAccountsFragmentDirections.fromUserAccountsFragmentToAccountDetailsFragment()
            findNavController().navigate(directions)
        }
    }


}