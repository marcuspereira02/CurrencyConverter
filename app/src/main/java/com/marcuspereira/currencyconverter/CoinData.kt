package com.marcuspereira.currencyconverter

import androidx.annotation.DrawableRes

data class CoinData(
    val name: String,
    val coin: Double,
    @DrawableRes val icon: Int,
    val isSelected: Boolean
)
