package com.example.cryptocoinapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocoinapp.R
import com.example.cryptocoinapp.databinding.TopCurrencyLayoutBinding
import com.example.cryptocoinapp.models.CryptoCurrency

// Adapter class to manage and display a list of cryptocurrencies in a RecyclerView.
class TopMarketAdapter(private var context: Context, val list: List<CryptoCurrency>) :
    RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    // ViewHolder class to hold references to the views in each item of the RecyclerView.
    inner class TopMarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = TopCurrencyLayoutBinding.bind(view) // Binding for the item layout
    }

    // Called when RecyclerView needs a new ViewHolder (a new item view).
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        // Inflate the item layout and create a new ViewHolder.
        return TopMarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.top_currency_layout, parent, false)
        )
    }

    // Called to bind data to the ViewHolder (to display data in the item).
    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position] // Get the current cryptocurrency item

        // Set the name of the cryptocurrency in the TextView.
        holder.binding.topCurrencyNameTextView.text = item.name

        // Use Glide to load the cryptocurrency image from the URL and display it in the ImageView.
        Glide.with(context).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        ).thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.topCurrencyImageView)

        // Set the color and text of the change percentage based on whether it's positive or negative.
        if (item.quotes!![0].percentChange24h > 0) {
            // Positive change: Set text color to green.
            holder.binding.topCurrencyChangeTextView.setTextColor(
                ContextCompat.getColor(context, R.color.green)
            )
            holder.binding.topCurrencyChangeTextView.text =
                "+ ${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        } else {
            // Negative change: Set text color to red.
            holder.binding.topCurrencyChangeTextView.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
            holder.binding.topCurrencyChangeTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        }
    }

    // Returns the total number of items in the list.
    override fun getItemCount(): Int {
        return list.size
    }
}
