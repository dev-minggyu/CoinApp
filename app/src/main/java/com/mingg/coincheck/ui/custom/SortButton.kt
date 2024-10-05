package com.mingg.coincheck.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mingg.coincheck.R
import com.mingg.coincheck.databinding.ButtonSortBinding
import com.mingg.coincheck.extension.getEnum
import com.mingg.domain.model.ticker.SortCategory
import com.mingg.domain.model.ticker.SortModel
import com.mingg.domain.model.ticker.SortType

class SortButton(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val binding: ButtonSortBinding by lazy {
        ButtonSortBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private lateinit var sortCategory: SortCategory

    private var sortModel: SortModel = SortModel(SortCategory.NAME, SortType.ASC)

    private var onSortChangedListener: OnSortChangedListener? = null

    init {
        init()
        getAttrs(attrs)
        setListener()
    }

    private fun init() {
        binding.ivSortArrow.setImageResource(R.drawable.ic_arrow_normal)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortButton)

        sortCategory = typedArray.getEnum(R.styleable.SortButton_sortCategory)

        binding.tvSortName.apply {
            text = when (sortCategory) {
                SortCategory.NAME -> context.getString(R.string.sort_coin_name)
                SortCategory.PRICE -> context.getString(R.string.sort_coin_price)
                SortCategory.RATE -> context.getString(R.string.sort_coin_rate)
                SortCategory.VOLUME -> context.getString(R.string.sort_coin_volume)
            }
        }

        typedArray.recycle()
    }

    private fun setListener() {
        setOnClickListener {
            binding.apply {
                if (sortModel.category == sortCategory) {
                    when (sortModel.type) {
                        SortType.ASC -> sortModel.type = SortType.DESC
                        SortType.DESC -> sortModel.type = SortType.ASC
                    }
                } else {
                    sortModel.type = SortType.DESC
                }
                sortModel.category = sortCategory
            }
            onSortChangedListener?.onChanged(sortModel)
        }
    }

    fun setSortState(sortModel: SortModel) {
        this.sortModel = sortModel.copy()
        binding.apply {
            if (this@SortButton.sortModel.category == sortCategory) {
                when (sortModel.type) {
                    SortType.DESC -> ivSortArrow.setImageResource(R.drawable.ic_arrow_down)
                    SortType.ASC -> ivSortArrow.setImageResource(R.drawable.ic_arrow_up)
                }
            } else {
                ivSortArrow.setImageResource(R.drawable.ic_arrow_normal)
            }
        }
    }

    fun getSortState(): SortModel = sortModel.copy()

    fun setOnSortChangedListener(listener: OnSortChangedListener) {
        onSortChangedListener = listener
    }

    interface OnSortChangedListener {
        fun onChanged(sortModel: SortModel)
    }
}