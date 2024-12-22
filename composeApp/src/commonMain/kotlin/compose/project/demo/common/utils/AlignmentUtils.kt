package compose.project.demo.common.utils

import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.BiasAlignment

val Alignment.verticalBias: Float
    get() = when (this) {
        is BiasAlignment -> verticalBias
        is BiasAbsoluteAlignment -> verticalBias
        else -> 0f
    }

val Alignment.horizontalBias: Float
    get() = when (this) {
        is BiasAlignment -> horizontalBias
        is BiasAbsoluteAlignment -> horizontalBias
        else -> 0f
    }
