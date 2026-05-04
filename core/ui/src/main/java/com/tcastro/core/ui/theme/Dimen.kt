package com.tcastro.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Immutable
object Dimen {
    object Dimensions {
        val extraSmall: Dp = 4.dp
        val small:  Dp = 8.dp
        val smallPlus:  Dp = 12.dp
        val medium:  Dp = 16.dp
        val large:  Dp = 20.dp
        val xlarge:  Dp = 24.dp
        val xxlarge: Dp = 32.dp
        val xxxlarge: Dp = 40.dp
        val xxxxlarge: Dp = 56.dp
    }

    object Elevation {
        val small: Dp = 2.dp
    }

    object Spacing{
        val small:  Dp = 4.dp
        val default:  Dp = 8.dp
        val defaultPlus:  Dp = 12.dp
        val medium:  Dp = 16.dp
        val mediumPlus:  Dp = 20.dp
        val large:  Dp = 24.dp
    }

    object Images{
        val iconDefault:Dp = 18.dp
        val iconMedium:Dp = 38.dp
        val xLarge: Dp = 160.dp
        val xxLarge: Dp = 260.dp
    }


}