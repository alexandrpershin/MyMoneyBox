package com.example.minimoneybox.ui.useraccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minimoneybox.R
import com.example.minimoneybox.databinding.ItemInvestmentAccountBinding
import com.example.minimoneybox.extensions.getString
import com.example.minimoneybox.model.InvestorProductModel

typealias OnItemClick = (productId: Int) -> Unit

class UserAccountsAdapter(private val accounts: MutableList<InvestorProductModel> = mutableListOf()) :
    RecyclerView.Adapter<UserAccountsAdapter.ViewHolder>() {

    var onItemClick: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInvestmentAccountBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = accounts.size

    fun updateData(products: List<InvestorProductModel>) {
        accounts.clear()
        accounts.addAll(products)

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemInvestmentAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val model = accounts[position]
            with(binding) {
                tvPlanValue.text = R.string.text_pound_value.getString(model.planValue)
                tvMoneybox.text = R.string.text_pound_value.getString(model.moneybox)
                tvAccountName.text = model.friendlyName

                itemView.setOnClickListener {
                    onItemClick?.invoke(model.id)
                }
            }
        }

    }


}
