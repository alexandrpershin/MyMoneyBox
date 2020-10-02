package com.example.minimoneybox.ui.useraccounts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minimoneybox.R
import com.example.minimoneybox.databinding.ItemInvestmentAccountBinding
import com.example.minimoneybox.model.ProductAccount

typealias OnItemClick = (productId: Int) -> Unit

class UserAccountsAdapter(
    private val context: Context,
    private val accounts: MutableList<ProductAccount> = mutableListOf()
) :
    RecyclerView.Adapter<UserAccountsAdapter.ViewHolder>() {

    var onItemClick: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemInvestmentAccountBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = accounts.size

    fun updateData(productAccounts: List<ProductAccount>) {
        accounts.clear()
        accounts.addAll(productAccounts)

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemInvestmentAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val model = accounts[position]
            with(binding) {
                tvPlanValue.text = context.getString(R.string.text_pound_value, model.planValue)
                tvMoneybox.text =  context.getString(R.string.text_pound_value, model.moneybox)
                tvAccountName.text = model.friendlyName

                itemView.setOnClickListener {
                    onItemClick?.invoke(model.id)
                }
            }
        }

    }


}
