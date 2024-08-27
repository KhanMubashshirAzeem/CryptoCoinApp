package com.example.cryptocoinapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocoinapp.databinding.FragmentHomeBinding
import com.example.cryptocoinapp.adapter.TopLossGainPagerAdapter
import com.example.cryptocoinapp.adapter.TopMarketAdapter
import com.example.cryptocoinapp.api.ApiInterface
import com.example.cryptocoinapp.api.ApiUtilities
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Fragment that shows top cryptocurrencies and manages tab layout.
class HomeFragment : Fragment() {

    // Binding object for the fragment’s layout.
    private lateinit var binding: FragmentHomeBinding

    // Inflates the fragment’s layout and initializes the UI components.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout and set up the binding.
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Fetch and display top cryptocurrency data.
        getTopCurrencyList()

        // Set up the tab layout with ViewPager2 for switching between tabs.
        setTabLayout()

        // Return the root view of the fragment.
        return binding.root
    }

    // Fetches the list of top cryptocurrencies using the API.
    private fun getTopCurrencyList() {
        // Use coroutine for network call to avoid blocking the main thread.
        lifecycleScope.launch(Dispatchers.IO) {
            // Make API call to fetch market data.
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            // Switch to main thread to update UI.
            withContext(Dispatchers.Main) {
                // Set the adapter to display the fetched data in RecyclerView.
                binding.topCurrencyRecyclerView.adapter = TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }

            // Log the fetched data for debugging.
            Log.d("COIN", "getTopCurrency: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }

    // Sets up the tab layout with two tabs: Top Gainers and Top Losers.
    private fun setTabLayout() {
        // Initialize the adapter for the ViewPager.
        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        // Handle tab change events to show indicators for selected tab.
        binding.contentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                } else {
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        // Attach TabLayout with ViewPager and set tab titles.
        TabLayoutMediator(binding.tabLayout, binding.contentViewPager){
                tab, position ->
            val title = if (position == 0){
                "Top 10 Gainers"
            } else {
                "Top 10 Losers"
            }
            tab.text = title
        }.attach()
    }
}
