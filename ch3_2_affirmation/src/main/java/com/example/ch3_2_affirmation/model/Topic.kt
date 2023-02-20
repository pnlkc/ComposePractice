package com.example.ch3_2_affirmation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val stringId: Int,
    val numId: Int,
    @DrawableRes val imageId: Int
)
