package compose.project.demo.common.utils

import androidx.compose.ui.layout.Measured
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

val Constraints.minSize: IntSize
    get() = minWidth intSize minHeight

val Constraints.maxSize: IntSize
    get() = maxWidth intSize maxHeight

val Measured.measuredSize: IntSize
    get() = measuredWidth intSize measuredHeight

val Placeable.size: IntSize
    get() = width intSize height
