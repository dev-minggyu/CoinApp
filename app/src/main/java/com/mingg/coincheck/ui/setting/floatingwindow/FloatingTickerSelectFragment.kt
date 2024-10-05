package com.mingg.coincheck.ui.setting.floatingwindow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.mingg.coincheck.databinding.FragmentSettingFloatingTickerBinding
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.setting.adapter.CheckSymbolListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloatingTickerSelectFragment : BaseFragment<FragmentSettingFloatingTickerBinding>(FragmentSettingFloatingTickerBinding::inflate) {

    private val floatingSymbolSelectViewModel: FloatingSymbolSelectViewModel by viewModels()

    private lateinit var checkSymbolListAdapter: CheckSymbolListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            setFragmentResult(
                FloatingWindowSettingFragment.REQUEST_CHECKED_FLOATING_SYMBOL,
                Bundle().apply {
                    putStringArrayList(KEY_CHECKED_FLOATING_SYMBOL_LIST, ArrayList(result))
                }
            )
            navigationManager.goBack()
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
    }
}