package com.example.coinapp.ui.setting.floatingwindow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.coinapp.R
import com.example.coinapp.ui.base.BaseActivity
import com.example.coinapp.databinding.ActivitySettingFloatingTickerBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.ui.setting.adapter.CheckSymbolListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloatingTickerSelectActivity : BaseActivity<ActivitySettingFloatingTickerBinding>(R.layout.activity_setting_floating_ticker) {
    private val _floatingSymbolSelectViewModel: FloatingSymbolSelectViewModel by viewModels()

    private var _checkSymbolListAdapter: CheckSymbolListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        setupObserver()
        setupListener()

        _floatingSymbolSelectViewModel.getFloatingSymbolList()
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            val result = _checkSymbolListAdapter?.currentList?.filter { ticker ->
                ticker.isChecked
            }?.map { ticker ->
                ticker.symbol
            } ?: listOf()
            _floatingSymbolSelectViewModel.setFloatingTickerList(result)
            setResult(
                FloatingWindowSettingActivity.REQUEST_CHECKED_FLOATING_SYMBOL,
                Intent().putStringArrayListExtra(KEY_CHECKED_FLOATING_SYMBOL_LIST, ArrayList(result))
            )
            finish()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _floatingSymbolSelectViewModel.symbolList.collectWithLifecycle(lifecycle) {
                it?.let { list ->
                    _checkSymbolListAdapter?.submitList(list)
                }
            }
        }
    }

    private fun setupAdapter() {
        _checkSymbolListAdapter = CheckSymbolListAdapter()
        binding.rvSymbol.apply {
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = _checkSymbolListAdapter
        }
    }

    companion object {
        const val KEY_CHECKED_FLOATING_SYMBOL_LIST = "KEY_CHECKED_FLOATING_SYMBOL_LIST"

        fun createIntent(context: Context): Intent =
            Intent(context, FloatingTickerSelectActivity::class.java)
    }
}