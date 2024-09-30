package com.mingg.coincheck.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.FragmentHomeBinding
import com.mingg.coincheck.extension.collectWithLifecycle
import com.mingg.coincheck.extension.isServiceRunning
import com.mingg.coincheck.model.myasset.MyTickerInfo
import com.mingg.coincheck.navigation.NavigationManager
import com.mingg.coincheck.ui.base.BaseFragment
import com.mingg.coincheck.ui.custom.SortButton
import com.mingg.coincheck.ui.floating.FloatingWindowService
import com.mingg.coincheck.ui.home.adapter.TickerListAdapter
import com.mingg.coincheck.ui.main.SharedSettingIntent
import com.mingg.coincheck.ui.main.SharedSettingViewModel
import com.mingg.coincheck.ui.tickerdetail.TickerDetailFragment
import com.mingg.domain.model.ticker.Currency
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.Ticker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    LifecycleEventObserver {

    private val homeViewModel: HomeViewModel by viewModels()

    private val sharedSettingViewModel: SharedSettingViewModel by viewModels()

    private lateinit var navigationManager: NavigationManager

    private var tickerListAdapter: TickerListAdapter? = null

    private var tickerList: List<Ticker>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        navigationManager = NavigationManager(findNavController())

        setupListener()
        setupRecyclerView()
        setupListCategoryButton()
        setupObserver()

        homeViewModel.setEvent(HomeIntent.LoadTickers)
    }

    private fun setupListener() {
        with(binding) {
            layoutError.btnRetry.setOnClickListener {
                homeViewModel.setEvent(HomeIntent.Unsubscribe)
            }
            layoutSearch.etSearchTicker.doOnTextChanged { text, _, _, _ ->
                homeViewModel.setEvent(HomeIntent.Search(text.toString()))
            }
            object : SortButton.OnSortChangedListener {
                override fun onChanged(sortModel: SortModel) {
                    homeViewModel.setEvent(HomeIntent.Sort(sortModel))
                }
            }.let {
                with(layoutSort) {
                    btnSortName.setOnSortChangedListener(it)
                    btnSortPrice.setOnSortChangedListener(it)
                    btnSortRate.setOnSortChangedListener(it)
                    btnSortVolume.setOnSortChangedListener(it)
                }
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

                with(binding) {
                    state.sortModel?.let {
                        with(layoutSort) {
                            btnSortName.setSortState(it)
                            btnSortPrice.setSortState(it)
                            btnSortRate.setSortState(it)
                            btnSortVolume.setSortState(it)
                        }
                    }
                    progress.isVisible = state.isLoading
                    rvTicker.isVisible = !state.isLoading
                    layoutError.root.isVisible = !state.error.isNullOrEmpty()
                    state.error?.let { errorMessage -> layoutError.tvError.text = errorMessage }
                }
            }
        }

        lifecycleScope.launch {
            sharedSettingViewModel.uiState.collectWithLifecycle(lifecycle) { state ->
                tickerListAdapter?.setTickerChangeColor(state.tickerChangeColor)
            }
        }
    }

    private fun setListOfCategory(categoryId: Int) {
        when (categoryId) {
            R.id.btn_krw ->
                tickerListAdapter?.submitList(tickerList?.filter { it.currencyType == Currency.KRW })

            R.id.btn_btc ->
                tickerListAdapter?.submitList(tickerList?.filter { it.currencyType == Currency.BTC })

            R.id.btn_usdt ->
                tickerListAdapter?.submitList(tickerList?.filter { it.currencyType == Currency.USDT })

            R.id.btn_favorite ->
                tickerListAdapter?.submitList(tickerList?.filter { it.isFavorite })
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            sharedSettingViewModel.setEvent(SharedSettingIntent.LoadSettings)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> homeViewModel.setEvent(HomeIntent.Subscribe)
            Lifecycle.Event.ON_STOP -> {
                if (requireContext().isServiceRunning(FloatingWindowService::class.java)) {
                    homeViewModel.setEvent(HomeIntent.Unsubscribe)
                }
            }

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
