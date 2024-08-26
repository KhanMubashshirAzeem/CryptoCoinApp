package com.example.cryptocoinapp.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocoinapp.R
import com.example.cryptocoinapp.databinding.CurrencyItemLayoutBinding
import com.example.cryptocoinapp.models.CryptoCurrency

// Adapter for RecyclerView to display list of cryptocurrencies.
class MarketAdapter(private var context: Context, private var list: List<CryptoCurrency>) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    // ViewHolder class to hold views for each item in the list.
    inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CurrencyItemLayoutBinding.bind(view)
    }

    // Inflates item layout and returns a ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false)
        )
    }

    // Returns the total number of items in the list.
    override fun getItemCount(): Int {
        return list.size
    }

    // Binds data to the ViewHolder for each item.
    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        // Get the cryptocurrency item at the current position.
        val item = list[position]

        // Set the cryptocurrency name and symbol.
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

        // Load and display the cryptocurrency logo using Glide.
        Glide.with(context).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        ).thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        // Load and display the 7-day sparkline chart using Glide.
        Glide.with(context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png") // Assuming item.sparkline is the URL for the sparkline chart
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        // Set the current price of the cryptocurrency.
        holder.binding.currencyPriceTextView.text = String.format("$%.2f", item.quotes[0].price)

        // Change the text color based on the percentage change in 24 hours.
        val percentChange = item.quotes[0].percentChange24h
        if (percentChange > 0) {
            holder.binding.currencyChangeTextView.setTextColor(
                ContextCompat.getColor(context, R.color.green)
            )
            holder.binding.currencyChangeTextView.text = "+${String.format("%.2f", percentChange)}%"
        } else {
            holder.binding.currencyChangeTextView.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
            holder.binding.currencyChangeTextView.text = "${String.format("%.2f", percentChange)}%"
        }
    }
}

