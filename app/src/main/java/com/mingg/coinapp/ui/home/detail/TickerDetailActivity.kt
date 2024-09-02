package com.mingg.coinapp.ui.home.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mingg.coinapp.R
import com.mingg.coinapp.databinding.ActivityTickerDetailBinding
import com.mingg.coinapp.extension.collectWithLifecycle
import com.mingg.coinapp.model.myasset.MyTickerInfo
import com.mingg.coinapp.ui.base.BaseActivity
import com.mingg.coinapp.ui.myasset.dialog.AddMyAssetDialogFragment
import com.mingg.coinapp.utils.AppThemeManager
import com.mingg.coinapp.utils.TradingViewUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TickerDetailActivity :
    BaseActivity<ActivityTickerDetailBinding>(R.layout.activity_ticker_detail) {
    private val _tickerDetailViewModel: TickerDetailViewModel by viewModels()

    private var _myTickerInfo: MyTickerInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _myTickerInfo = intent.getParcelableExtra(KEY_MY_TICKER)

        setupActionBar()
        setupWebView()
        setupObserver()
        observeTicker()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        _myTickerInfo?.let {
            val symbol = it.symbol + it.currency.name
            binding.webviewChart.webViewClient = WebViewClient()
            binding.webviewChart.settings.javaScriptEnabled = true
            binding.webviewChart.loadData(
                TradingViewUtil.getScript(
                    symbol = symbol,
                    isDarkMode = AppThemeManager.isDarkMode(resources)
                ), "text/html", "base64"
            )
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _tickerDetailViewModel.ticker.collectWithLifecycle(lifecycle) {
                binding.ticker = it
            }
        }
    }

    private fun observeTicker() {
        _myTickerInfo?.let {
            _tickerDetailViewModel.observeTicker(
                it.symbol,
                it.currency
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.ticker_detail_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish();
                return true;
            }

            R.id.menu_add_my_asset -> {
                _myTickerInfo?.let {
                    AddMyAssetDialogFragment.newInstance(it).show(supportFragmentManager, null)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun startActivity(context: Context, ticker: MyTickerInfo) {
            val intent = Intent(context, TickerDetailActivity::class.java)
            intent.putExtra(KEY_MY_TICKER, ticker)
            context.startActivity(intent)
        }

        private const val KEY_MY_TICKER = "KEY_MY_TICKER"
    }
}