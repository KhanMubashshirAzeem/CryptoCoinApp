package com.example.cryptocoinapp.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptocoinapp.fragment.TopGainLossFragment

// Adapter for ViewPager2 to switch between top gainers and losers.
class TopLossGainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // Returns the number of fragments.
    override fun getItemCount(): Int {
        return 2
    }

    // Creates the fragment for the given position.
    override fun createFragment(position: Int): Fragment {
        // Create and return TopGainLossFragment with position argument.
        val fragment = TopGainLossFragment()
        val bundle = Bundle()
        bundle.putInt("position", position)
        fragment.arguments = bundle
        return fragment
    }
}
