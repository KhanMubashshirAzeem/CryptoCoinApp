package com.example.cryptocoinapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cryptocoinapp.R
import com.example.cryptocoinapp.databinding.FragmentTopGainLossBinding
import com.example.cryptocoinapp.adapter.MarketAdapter
import com.example.cryptocoinapp.api.ApiInterface
import com.example.cryptocoinapp.api.ApiUtilities
import com.example.cryptocoinapp.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.Collections

// Fragment to display top gainers or losers based on position.
class TopGainLossFragment : Fragment() {

    // Binding object for the fragment’s layout.
    lateinit var binding: FragmentTopGainLossBinding

    // Inflates the fragment’s layout and initializes UI components.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate layout and initialize binding.
        binding = FragmentTopGainLossBinding.inflate(layoutInflater)

        // Fetch and display market data.
        getMarketData()

        // Return the root view of the fragment.
        return binding.root
    }

    // Fetches market data using the API and updates the UI.
    private fun getMarketData() {
        // Get position argument passed to determine gainers/losers.
        val position = requireArguments().getInt("position")

        // Use coroutine for network call.
        lifecycleScope.launch(Dispatchers.IO) {
            // Make API call to fetch market data.
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            // Switch to main thread to update UI.
            withContext(Dispatchers.Main) {
                // Extract and sort the list by percentage change in 24 hours.
                val dataItem = res.body()!!.data.cryptoCurrencyList
                Collections.sort(dataItem) { o1, o2 ->
                    (o2.quotes[0].percentChange24h.toInt()).compareTo(o1.quotes[0].percentChange24h.toInt())
                }

                // Hide loading spinner.
                binding.spinKitView.visibility = GONE
                val list = ArrayList<CryptoCurrency>()

                // Populate RecyclerView with top 10 gainers or losers.
                if (position == 0) {
                    // Show top gainers.
                    list.clear()
                    for (i in 0..9) {
                        list.add(dataItem[i])
                    }
                } else {
                    // Show top losers.
                    list.clear()
                    for (i in 0..9) {
                        list.add(dataItem[dataItem.size - 1 - i])
                    }
                }

                // Set adapter with the list.
                binding.topGainLoseRecyclerView.adapter = MarketAdapter(requireContext(), list)
            }
        }
    }
}
