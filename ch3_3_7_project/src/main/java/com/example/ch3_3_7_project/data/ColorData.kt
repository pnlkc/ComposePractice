package com.example.ch3_3_7_project.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.ch3_3_7_project.R

data class ColorData(
    @StringRes val title: Int,
    @DrawableRes val image: Int,
    @StringRes val name: Int,
    @StringRes val description: Int
)

val colorList = listOf(
    ColorData(R.string.color1, R.drawable.color1, R.string.color1_name, R.string.color1_description),
    ColorData(R.string.color2, R.drawable.color2, R.string.color2_name, R.string.color2_description),
    ColorData(R.string.color3, R.drawable.color3, R.string.color3_name, R.string.color3_description),
    ColorData(R.string.color4, R.drawable.color4, R.string.color4_name, R.string.color4_description),
    ColorData(R.string.color5, R.drawable.color5, R.string.color5_name, R.string.color5_description),
    ColorData(R.string.color6, R.drawable.color6, R.string.color6_name, R.string.color6_description),
)
