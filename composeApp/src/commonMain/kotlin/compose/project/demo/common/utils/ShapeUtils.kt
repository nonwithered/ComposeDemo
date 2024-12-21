package compose.project.demo.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

operator fun Rect.plus(offset: Offset): Rect {
    return translate(offset)
}

operator fun Rect.minus(offset: Offset): Rect {
    return translate(-offset)
}

infix fun Float.offset(rhs: Float): Offset {
    val lhs = this
    return Offset(lhs, rhs)
}

infix fun Offset.rect(rhs: Offset): Rect {
    val lhs = this
    return Rect(lhs, rhs)
}
