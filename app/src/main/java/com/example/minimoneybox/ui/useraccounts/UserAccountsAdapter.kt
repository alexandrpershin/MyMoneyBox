package com.example.minimoneybox.ui.useraccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minimoneybox.databinding.ItemInvestmentAccountBinding
import com.example.minimoneybox.extensions.parseToString
import com.example.minimoneybox.model.InvestorProductModel

typealias OnItemClick = (InvestorProductModel) -> Unit

class UserAccountsAdapter(private val accounts: MutableList<InvestorProductModel> = mutableListOf()) :
    RecyclerView.Adapter<UserAccountsAdapter.ViewHolder>() {

    var onItemClick : OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInvestmentAccountBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(accounts[position])
    }

    override fun getItemCount(): Int = accounts.size

    fun updateData(products: List<InvestorProductModel>) {
        accounts.clear()
        accounts.addAll(products)

        notifyDataSetChanged()
    }

   inner class ViewHolder(private val binding: ItemInvestmentAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: InvestorProductModel) {
            with(binding) {
                tvPlanValue.text = "£${model.planValue.parseToString()}"
                tvMoneybox.text = "£${model.moneybox.parseToString()}"
                tvAccountName.text = model.friendlyName

                itemView.setOnClickListener {
                    onItemClick?.invoke(model)
                }
            }
        }

    }


}
