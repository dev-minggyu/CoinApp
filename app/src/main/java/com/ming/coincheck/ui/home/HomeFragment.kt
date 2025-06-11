package com.ming.coincheck.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.ming.coincheck.R
import com.ming.coincheck.databinding.FragmentHomeBinding
import com.ming.coincheck.extension.collectWithLifecycle
import com.ming.coincheck.model.MyTickerInfo
import com.ming.coincheck.ui.base.BaseFragment
import com.ming.coincheck.ui.custom.SortButton
import com.ming.coincheck.ui.home.adapter.TickerListAdapter
import com.ming.coincheck.ui.main.SharedSettingIntent
import com.ming.coincheck.ui.main.SharedSettingViewModel
import com.ming.coincheck.ui.tickerdetail.TickerDetailFragment
import com.ming.domain.model.ticker.Currency
import com.ming.domain.model.ticker.SortModel
import com.ming.domain.model.ticker.Ticker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val sharedSettingViewModel: SharedSettingViewModel by viewModels()

    private var tickerListAdapter: TickerListAdapter? = null
    private var tickerList: List<Ticker>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupRecyclerView()
        setupListCategoryButton()
        setupObserver()

        sharedSettingViewModel.setEvent(SharedSettingIntent.LoadSettings)
        homeViewModel.setEvent(HomeIntent.LoadTickers)
    }

    private fun setupListener() {
        val sortChangedListener = object : SortButton.OnSortChangedListener {
            override fun onChanged(sortModel: SortModel) {
                homeViewModel.setEvent(HomeIntent.Sort(sortModel))
            }
        }

        with(binding) {
            layoutError.btnRetry.setOnClickListener {
                homeViewModel.setEvent(HomeIntent.Subscribe)
            }
            layoutSearch.etSearchTicker.doOnTextChanged { text, _, _, _ ->
                homeViewModel.setEvent(HomeIntent.Search(text.toString()))
            }
            with(layoutSort) {
                btnSortName.setOnSortChangedListener(sortChangedListener)
                btnSortPrice.setOnSortChangedListener(sortChangedListener)
                btnSortRate.setOnSortChangedListener(sortChangedListener)
                btnSortVolume.setOnSortChangedListener(sortChangedListener)
            }
        }
    }

    private fun setupRecyclerView() {
        val favoriteClickListener = object : TickerListAdapter.FavoriteClickListener {
            override fun onAddFavorite(symbol: String) {
                homeViewModel.setEvent(HomeIntent.InsertFavorite(symbol))
            }

            override fun onDeleteFavorite(symbol: String) {
                homeViewModel.setEvent(HomeIntent.DeleteFavorite(symbol))
            }
        }

        tickerListAdapter = TickerListAdapter(favoriteClickListener) { ticker ->
            navigationManager.navigateToTickerDetail(
                bundleOf(
                    TickerDetailFragment.KEY_MY_TICKER to MyTickerInfo(
                        symbol = ticker.symbol,
                        currency = ticker.currencyType,
                        ticker.koreanSymbol,
                        ticker.englishSymbol
                    )
                )
            )
        }

        binding.rvTicker.apply {
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = tickerListAdapter
        }
    }

    private fun setupListCategoryButton() {
        binding.layoutListCategory.radioGroupCatecory.setOnCheckedChangeListener { _, id ->
            tickerListAdapter?.submitList(null)
            setListOfCategory(id)
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            homeViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                tickerList = state.tickerList
                setListOfCategory(binding.layoutListCategory.radioGroupCatecory.checkedRadioButtonId)
                updateUI(state)
            }
        }

        lifecycleScope.launch {
            sharedSettingViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                tickerListAdapter?.setTickerChangeColor(state.tickerChangeColor)
            }
        }
    }

    private fun updateUI(state: HomeState) {
        with(binding) {
            progress.isVisible = state.isLoading
            rvTicker.isVisible = !state.isLoading && state.error == null
            layoutError.root.isVisible = state.error != null

            state.sortModel?.let {
                with(layoutSort) {
                    btnSortName.setSortState(it)
                    btnSortPrice.setSortState(it)
                    btnSortRate.setSortState(it)
                    btnSortVolume.setSortState(it)
                }
            }

            state.error?.let { errorType ->
                layoutError.tvError.text = when (errorType) {
                    is HomeErrorType.NetworkError -> getString(R.string.home_error_network)
                    is HomeErrorType.UnexpectedError -> getString(R.string.home_error_unexpected)
                }
                layoutError.root.isVisible = true
            }
        }
    }

    private fun setListOfCategory(categoryId: Int) {
        val filteredList = when (categoryId) {
            R.id.btn_krw -> tickerList?.filter { it.currencyType == Currency.KRW }
            R.id.btn_btc -> tickerList?.filter { it.currencyType == Currency.BTC }
            R.id.btn_usdt -> tickerList?.filter { it.currencyType == Currency.USDT }
            R.id.btn_favorite -> tickerList?.filter { it.isFavorite }
            else -> null
        }
        tickerListAdapter?.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tickerListAdapter = null
        tickerList = null
    }
}