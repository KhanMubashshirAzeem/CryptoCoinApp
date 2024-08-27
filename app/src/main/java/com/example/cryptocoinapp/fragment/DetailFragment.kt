package com.example.cryptocoinapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptocoinapp.R
import com.example.cryptocoinapp.databinding.FragmentDetailBinding
import com.example.cryptocoinapp.databinding.FragmentWatchListBinding
import com.example.cryptocoinapp.models.CryptoCurrency

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val item: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)

        val data: CryptoCurrency = item.data!!

        setUpDetails(data)
        loadChart(data)
        setButtonOnClick(data)

        return binding.root
    }


    private fun setUpDetails(data: CryptoCurrency) {
        binding.detailSymbolTextView.text = data.symbol
        // Load and display the cryptocurrency logo using Glide.
        Glide.with(requireContext()).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png"
        ).thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
            .into(binding.detailImageView)

        // Set the current price of the cryptocurrency.
        binding.detailPriceTextView.text = String.format("$%.4f", data.quotes[0].price)

        // Change the text color based on the percentage change in 24 hours.
        val percentChange = data.quotes[0].percentChange24h
        if (percentChange > 0) {
            binding.detailChangeTextView.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.green)
            )
            binding.detailChangeTextView.text = "+${String.format("%.2f", percentChange)}%"
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)

        } else {
            binding.detailChangeTextView.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.red)
            )
            binding.detailChangeTextView.text = "${String.format("%.2f", percentChange)}%"
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)

        }
    }


    private fun loadChart(data: CryptoCurrency) {
        binding.detailChartWebView.settings.javaScriptEnabled = true
        binding.detailChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detailChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol" + item.data.toString() + "USD&interval=D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies" + "=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=" + "[]&disabled_features=[]&locale=en&utm_source=coinmarketcap" + ".com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }


    private fun setButtonOnClick(data: CryptoCurrency) {
        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fifteenMinute = binding.button5

        val clickListener = View.OnClickListener {
            when (it.id) {
                fifteenMinute.id -> loadChartData(
                    it, "15", item, oneDay, oneMonth, oneWeek, fourHour, oneHour
                )

                oneHour.id -> loadChartData(
                    it, "1H", item, oneDay, oneMonth, oneWeek, fourHour, fifteenMinute
                )

                fourHour.id -> loadChartData(
                    it, "4H", item, oneDay, oneMonth, oneWeek, fifteenMinute, oneHour
                )

                oneDay.id -> loadChartData(
                    it, "D", item, fifteenMinute, oneMonth, oneWeek, fourHour, oneHour
                )

                oneWeek.id -> loadChartData(
                    it, "W", item, oneDay, oneMonth, fifteenMinute, fourHour, oneHour
                )

                oneMonth.id -> loadChartData(
                    it, "M", item, oneDay, fifteenMinute, oneWeek, fourHour, oneHour
                )
            }
        }

        fifteenMinute.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)

    }

    private fun loadChartData(
        it: View?,
        s: String,
        item: DetailFragmentArgs,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {
        disableButton(oneDay, oneMonth, oneWeek, fourHour, oneHour)
        it!!.setBackgroundResource(R.drawable.active_button)
        binding.detailChartWebView.settings.javaScriptEnabled = true
        binding.detailChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detailChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol" + item.data.toString() + "USD&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" + "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies" + "=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides=" + "{}&overrides={}&enabled_features=" + "[]&disabled_features=[]&locale=en&utm_source=coinmarketcap" + ".com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

    private fun disableButton(
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {
        oneDay.background = null
        oneMonth.background = null
        oneWeek.background = null
        fourHour.background = null
        oneHour.background = null
    }

}