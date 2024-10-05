package com.mingg.coincheck.ui.setting.floatingwindow.selector

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.mingg.coincheck.databinding.FragmentSettingFloatingTickerBinding
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.setting.adapter.CheckTickerListAdapter
import com.mingg.coincheck.ui.setting.floatingwindow.setting.FloatingWindowSettingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FloatingTickerSelectFragment : BaseFragment<FragmentSettingFloatingTickerBinding>(FragmentSettingFloatingTickerBinding::inflate) {

    private val floatingTickerSelectViewModel: FloatingTickerSelectViewModel by viewModels()

    private var checkTickerListAdapter: CheckTickerListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListener()
        setupObserver()

        floatingTickerSelectViewModel.setEvent(FloatingTickerSelectIntent.LoadFloatingTickerList)
    }

    private fun setupAdapter() {
        checkTickerListAdapter = CheckTickerListAdapter()
        binding.rvTicker.apply {
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = checkTickerListAdapter
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            floatingTickerSelectViewModel.uiState.collect { state ->
                state.tickerList?.let { list ->
                    checkTickerListAdapter?.submitList(list)
                }
            }
        }
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            saveSelectedTickerList()
        }
    }

    private fun saveSelectedTickerList() {
        val result = checkTickerListAdapter?.currentList?.filter { it.isChecked }?.map { it.symbol } ?: listOf()
        floatingTickerSelectViewModel.setEvent(FloatingTickerSelectIntent.SetFloatingTickerList(result))

        setFragmentResult(
            FloatingWindowSettingFragment.REQUEST_CHECKED_FLOATING_TICKER,
            Bundle().apply {
                putStringArrayList(KEY_CHECKED_FLOATING_TICKER_LIST, ArrayList(result))
            }
        )
        navigationManager.goBack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        checkTickerListAdapter = null
    }

    companion object {
        const val KEY_CHECKED_FLOATING_TICKER_LIST = "KEY_CHECKED_FLOATING_TICKER_LIST"
    }
}