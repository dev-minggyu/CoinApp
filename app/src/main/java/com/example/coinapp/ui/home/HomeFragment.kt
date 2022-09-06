package com.example.coinapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.coinapp.R
import com.example.coinapp.base.BaseFragment
import com.example.coinapp.databinding.FragmentHomeBinding
import com.example.coinapp.extension.collectWithLifecycle
import com.example.coinapp.model.myasset.MyTickerInfo
import com.example.coinapp.ui.home.adapter.TickerListAdapter
import com.example.coinapp.ui.home.detail.TickerDetailActivity
import com.example.coinapp.ui.main.MainSettingViewModel
import com.example.domain.model.ticker.Currency
import com.example.domain.model.ticker.Ticker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), LifecycleEventObserver {
    private val _homeViewModel: HomeViewModel by viewModels()

    private val _settingViewModel: MainSettingViewModel by activityViewModels()

    private var _tickerListAdapter: TickerListAdapter? = null

    private var _tickerList: List<Ticker>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        binding.vm = _homeViewModel

        setupRecyclerView()
        setupListCategoryButton()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            _homeViewModel.tickerList.collectWithLifecycle(lifecycle) { tickerList ->
                _tickerList = tickerList
                setListOfCategory(binding.layoutListCategory.radioGroupCatecory.checkedRadioButtonId)
            }
        }

        lifecycleScope.launch {
            _settingViewModel.tickerChangeColor.collectWithLifecycle(lifecycle) {
                _tickerListAdapter?.setTickerChangeColor(it)
            }
        }
    }

    private fun setupRecyclerView() {
        val favoriteClickListener = object : TickerListAdapter.FavoriteClickListener {
            override fun onAddFavorite(symbol: String) {
                _homeViewModel.insertFavoriteTicker(symbol)
            }

            override fun onDeleteFavorite(symbol: String) {
                _homeViewModel.deleteFavoriteTicker(symbol)
            }
        }
        _tickerListAdapter = TickerListAdapter(favoriteClickListener) { ticker ->
            TickerDetailActivity.startActivity(
                context, MyTickerInfo(
                    ticker.symbol,
                    ticker.currencyType,
                    ticker.koreanSymbol,
                    ticker.englishSymbol
                )
            )
        }

        binding.rvTicker.apply {
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = _tickerListAdapter
        }
    }

    private fun setupListCategoryButton() {
        binding.layoutListCategory.radioGroupCatecory.setOnCheckedChangeListener { _, id ->
            _tickerListAdapter?.submitList(null)
            setListOfCategory(id)
        }
    }

    private fun setListOfCategory(categoryId: Int) {
        when (categoryId) {
            R.id.btn_krw ->
                _tickerListAdapter?.submitList(_tickerList?.filter { it.currencyType == Currency.KRW })
            R.id.btn_btc ->
                _tickerListAdapter?.submitList(_tickerList?.filter { it.currencyType == Currency.BTC })
            R.id.btn_usdt ->
                _tickerListAdapter?.submitList(_tickerList?.filter { it.currencyType == Currency.USDT })
            R.id.btn_favorite ->
                _tickerListAdapter?.submitList(_tickerList?.filter { it.isFavorite })
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> _homeViewModel.subscribeTicker()
            Lifecycle.Event.ON_STOP -> _homeViewModel.unsubscribeTicker()
            else -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}