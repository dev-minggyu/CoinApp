package com.example.coinapp.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.coinapp.R
import com.example.coinapp.base.BaseActivity
import com.example.coinapp.databinding.ActivitySettingFloatingSymbolBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.ui.setting.adapter.CheckSymbolListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFloatingSymbolActivity : BaseActivity<ActivitySettingFloatingSymbolBinding>(R.layout.activity_setting_floating_symbol) {
    private val _settingFloatingSymbolViewModel: SettingFloatingSymbolViewModel by viewModels()

    private var _checkSymbolListAdapter: CheckSymbolListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        setupObserver()
        setupListener()

        _settingFloatingSymbolViewModel.getSymbolList()
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            val result = _checkSymbolListAdapter?.currentList?.filter { ticker ->
                ticker.isChecked
            }?.map { ticker ->
                ticker.symbol
            }
            result?.let {
                _settingFloatingSymbolViewModel.setFloatingTickerList(it)
                setResult(RESULT_OK, Intent().putStringArrayListExtra("test", ArrayList(it)))
            }
            finish()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _settingFloatingSymbolViewModel.symbolList.collectWithLifecycle(lifecycle) {
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
        fun createIntent(context: Context?): Intent =
            Intent(context, SettingFloatingSymbolActivity::class.java)
    }
}