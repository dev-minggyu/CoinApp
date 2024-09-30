package com.mingg.coincheck.ui.setting.floatingwindow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.mingg.coincheck.databinding.ActivitySettingFloatingTickerBinding
import com.mingg.coincheck.ui.base.BaseActivity
import com.mingg.coincheck.ui.setting.adapter.CheckSymbolListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloatingTickerSelectActivity :
    BaseActivity<ActivitySettingFloatingTickerBinding>(ActivitySettingFloatingTickerBinding::inflate) {

    private val floatingSymbolSelectViewModel: FloatingSymbolSelectViewModel by viewModels()

    private lateinit var checkSymbolListAdapter: CheckSymbolListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        setupObserver()
        setupListener()

        floatingSymbolSelectViewModel.setEvent(FloatingSymbolSelectIntent.LoadFloatingSymbolList)
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            val result = checkSymbolListAdapter.currentList.filter { ticker ->
                ticker.isChecked
            }.map { ticker ->
                ticker.symbol
            }
            floatingSymbolSelectViewModel.setEvent(FloatingSymbolSelectIntent.SetFloatingTickerList(result))
            setResult(
                FloatingWindowSettingActivity.REQUEST_CHECKED_FLOATING_SYMBOL,
                Intent().putStringArrayListExtra(
                    KEY_CHECKED_FLOATING_SYMBOL_LIST,
                    ArrayList(result)
                )
            )
            finish()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            floatingSymbolSelectViewModel.uiState.collect { state ->
                state.symbolList?.let { list ->
                    checkSymbolListAdapter.submitList(list)
                }
            }
        }
    }

    private fun setupAdapter() {
        checkSymbolListAdapter = CheckSymbolListAdapter()
        binding.rvSymbol.apply {
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = checkSymbolListAdapter
        }
    }

    companion object {
        const val KEY_CHECKED_FLOATING_SYMBOL_LIST = "KEY_CHECKED_FLOATING_SYMBOL_LIST"

        fun createIntent(context: Context): Intent =
            Intent(context, FloatingTickerSelectActivity::class.java)
    }
}