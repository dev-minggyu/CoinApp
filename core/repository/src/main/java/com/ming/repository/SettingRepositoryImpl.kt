package com.ming.repository

import com.ming.database.setting.SettingLocalDataSource
import com.ming.domain.model.setting.FloatingTicker
import com.ming.domain.model.ticker.AtomicTickerList
import com.ming.domain.repository.setting.SettingRepository
import com.ming.network.mapper.setting.FloatingTickerMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingLocalDataSource: SettingLocalDataSource,
    private val atomicTickerList: AtomicTickerList
) : SettingRepository {
    override fun getSettingTickerChangeColor(): Boolean =
        settingLocalDataSource.getSettingTickerChangeColor()

    override fun setSettingTickerChangeColor(value: Boolean) {
        settingLocalDataSource.setSettingTickerChangeColor(value)
    }

    override fun getSettingFloatingWindow(): Boolean =
        settingLocalDataSource.getSettingFloatingWindow()

    override fun setSettingFloatingWindow(isEnable: Boolean) =
        settingLocalDataSource.setSettingFloatingWindow(isEnable)

    override fun setSettingFloatingTickerList(list: List<String>) =
        settingLocalDataSource.setSettingFloatingTickerList(list)

    override fun getSettingFloatingTickerList(): List<String> =
        settingLocalDataSource.getSettingFloatingTickerList()

    override suspend fun getFloatingTickerList(): List<FloatingTicker> =
        withContext(Dispatchers.Default) {
            val symbolList = atomicTickerList.getSymbolList()
            val settingFloatingTickerList = settingLocalDataSource.getSettingFloatingTickerList()
            if (settingFloatingTickerList.isEmpty()) {
                return@withContext symbolList.map {
                    FloatingTickerMapper.fromTickerSymbol(it)
                }
            } else {
                val floatingTickerList = symbolList.map {
                    FloatingTickerMapper.fromTickerSymbol(it)
                }
                settingFloatingTickerList.forEach { checkedTicker ->
                    floatingTickerList.find { floatingTicker ->
                        floatingTicker.symbol == checkedTicker
                    }?.apply {
                        isChecked = true
                    }
                }
                return@withContext floatingTickerList
            }
        }

    override fun getSettingFloatingTransparent(): Int =
        settingLocalDataSource.getSettingFloatingTransparent()

    override fun setSettingFloatingTransparent(value: Int) {
        settingLocalDataSource.setSettingFloatingTransparent(value)
    }

    override fun getSettingAppTheme(): String = settingLocalDataSource.getSettingAppTheme()

    override fun setSettingAppTheme(theme: String) =
        settingLocalDataSource.setSettingAppTheme(theme)
}