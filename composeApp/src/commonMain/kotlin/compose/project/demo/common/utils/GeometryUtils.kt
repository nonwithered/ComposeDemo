package compose.project.demo.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.roundToIntSize
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import kotlin.jvm.JvmName

// Rect operator Offset

@Stable
operator fun Rect.plus(rhs: Offset): Rect {
    val lhs = this
    return lhs.translate(rhs)
}

@Stable
operator fun Rect.plus(rhs: IntOffset): Rect {
    val lhs = this
    return lhs + rhs.toOffset()
}

@Stable
operator fun Rect.minus(rhs: Offset): Rect {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun Rect.minus(rhs: IntOffset): Rect {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun IntRect.plus(rhs: IntOffset): IntRect {
    val lhs = this
    return lhs.translate(rhs)
}

@Stable
operator fun IntRect.plus(rhs: Offset): IntRect {
    val lhs = this
    return lhs + rhs.round()
}

@Stable
operator fun IntRect.minus(rhs: IntOffset): IntRect {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun IntRect.minus(rhs: Offset): IntRect {
    val lhs = this
    return lhs + -rhs
}

// Rect operator Size

@Stable
operator fun Rect.plus(rhs: Size): Rect {
    val lhs = this
    return lhs.topLeft rect (lhs.size + rhs)
}

@Stable
operator fun Rect.plus(rhs: IntSize): Rect {
    val lhs = this
    return lhs + rhs.toSize()
}

@Stable
operator fun Rect.minus(rhs: Size): Rect {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun Rect.minus(rhs: IntSize): Rect {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun IntRect.plus(rhs: IntSize): IntRect {
    val lhs = this
    return lhs.topLeft intRect (lhs.size + rhs)
}

@Stable
operator fun IntRect.plus(rhs: Size): IntRect {
    val lhs = this
    return lhs + rhs.roundToIntSize()
}

@Stable
operator fun IntRect.minus(rhs: Size): IntRect {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun IntRect.minus(rhs: IntSize): IntRect {
    val lhs = this
    return lhs + -rhs
}

// Offset & Size to Rect

@Stable
infix fun Offset.rect(rhs: Size): Rect {
    val lhs = this
    return Rect(lhs, rhs)
}

@Stable
infix fun Offset.rect(rhs: IntSize): Rect {
    val lhs = this
    return lhs rect rhs.toSize()
}

@Stable
infix fun IntOffset.rect(rhs: Size): Rect {
    val lhs = this
    return lhs.toOffset() rect rhs
}

@Stable
infix fun IntOffset.rect(rhs: IntSize): Rect {
    val lhs = this
    return lhs.toOffset() rect rhs.toSize()
}

@Stable
infix fun IntOffset.intRect(rhs: IntSize): IntRect {
    val lhs = this
    return IntRect(lhs, rhs)
}

@Stable
infix fun IntOffset.intRect(rhs: Size): IntRect {
    val lhs = this
    return lhs intRect rhs.roundToIntSize()
}

@Stable
infix fun Offset.intRect(rhs: IntSize): IntRect {
    val lhs = this
    return lhs.round() intRect rhs
}

@Stable
infix fun Offset.intRect(rhs: Size): IntRect {
    val lhs = this
    return lhs.round() intRect rhs.roundToIntSize()
}

// Number to Offset

@Stable
infix fun Number.offset(rhs: Number): Offset {
    val lhs = this
    return Offset(lhs.toFloat(), rhs.toFloat())
}

@Stable
infix fun Number.intOffset(rhs: Number): IntOffset {
    val lhs = this
    return (lhs offset rhs).round()
}

// Number to Size

@Stable
infix fun Number.size(rhs: Number): Size {
    val lhs = this
    return Size(lhs.toFloat(), rhs.toFloat())
}

@Stable
infix fun Number.intSize(rhs: Number): IntSize {
    val lhs = this
    return (lhs size rhs).roundToIntSize()
}

// Offset operator

@Stable
operator fun Offset.times(rhs: Vec2<Number>): Offset {
    val lhs = this
    return (lhs.x * rhs.first.toFloat()) offset (lhs.y * rhs.second.toFloat())
}

@Stable
operator fun Offset.div(rhs: Vec2<Number>): Offset {
    val lhs = this
    return lhs * ((1f / rhs.first.toFloat()) to (1f / rhs.second.toFloat()))
}

@Stable
operator fun IntOffset.times(rhs: Vec2<Number>): IntOffset {
    val lhs = this.toOffset()
    return (lhs * rhs).round()
}

@Stable
operator fun IntOffset.div(rhs: Vec2<Number>): IntOffset {
    val lhs = this.toOffset()
    return (lhs / rhs).round()
}

// Size operator

@Stable
operator fun Size.times(rhs: Vec2<Number>): Size {
    val lhs = this
    return (lhs.width * rhs.first.toFloat()) size (lhs.height * rhs.second.toFloat())
}

@Stable
operator fun Size.div(rhs: Vec2<Number>): Size {
    val lhs = this
    return lhs * ((1f / rhs.first.toFloat()) to (1f / rhs.second.toFloat()))
}

@Stable
operator fun IntSize.times(rhs: Vec2<Number>): IntSize {
    val lhs = this.toSize()
    return (lhs * rhs).roundToIntSize()
}

@Stable
operator fun IntSize.div(rhs: Vec2<Number>): IntSize {
    val lhs = this.toSize()
    return (lhs / rhs).roundToIntSize()
}

@Stable
operator fun Size.unaryMinus(): Size {
    return -width size -height
}

@Stable
operator fun IntSize.unaryMinus(): IntSize {
    return -width intSize -height
}

@Stable
operator fun Size.plus(rhs: Size): Size {
    val lhs = this
    return (lhs.width + rhs.width) size (lhs.height + rhs.height)
}

@Stable
operator fun Size.plus(rhs: IntSize): Size {
    val lhs = this
    return lhs + rhs.toSize()
}

@Stable
operator fun Size.minus(rhs: Size): Size {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun Size.minus(rhs: IntSize): Size {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun IntSize.plus(rhs: IntSize): IntSize {
    val lhs = this
    return (lhs.width + rhs.width) intSize (lhs.height + rhs.height)
}

@Stable
operator fun IntSize.plus(rhs: Size): IntSize {
    val lhs = this
    return lhs + rhs.roundToIntSize()
}

@Stable
operator fun IntSize.minus(rhs: IntSize): IntSize {
    val lhs = this
    return lhs + -rhs
}

@Stable
operator fun IntSize.minus(rhs: Size): IntSize {
    val lhs = this
    return lhs + -rhs
}

// Size coerce

@Stable
fun Size.coerceIn(
    minimumValue: Size? = null,
    maximumValue: Size? = null,
): Size {
    val minimumSize = minimumValue ?: (Float.MIN_VALUE size Float.MIN_VALUE)
    val maximumSize = maximumValue ?: (Float.MAX_VALUE size Float.MAX_VALUE)
    return width.coerceIn(minimumSize.width, maximumSize.width) size height.coerceIn(minimumSize.height, maximumSize.height)
}

@Stable
fun Size.coerceAtLeast(minimumValue: Size): Size {
    return coerceIn(minimumValue = minimumValue)
}

@Stable
fun Size.coerceAtMost(maximumValue: Size): Size {
    return coerceIn(maximumValue = maximumValue)
}

@Stable
fun Size.coerceAtLeast(minimumValue: IntSize): Size {
    return coerceAtLeast(minimumValue.toSize())
}

@Stable
fun Size.coerceAtMost(maximumValue: IntSize): Size {
    return coerceAtMost(maximumValue.toSize())
}

@Stable
fun IntSize.coerceIn(
    minimumValue: IntSize? = null,
    maximumValue: IntSize? = null,
): IntSize {
    val minimumSize = minimumValue ?: (Int.MIN_VALUE intSize Int.MIN_VALUE)
    val maximumSize = maximumValue ?: (Int.MAX_VALUE intSize Int.MAX_VALUE)
    return width.coerceIn(minimumSize.width, maximumSize.width) intSize height.coerceIn(minimumSize.height, maximumSize.height)
}

@Stable
fun IntSize.coerceAtLeast(minimumValue: IntSize): IntSize {
    return coerceIn(minimumValue = minimumValue)
}

@Stable
fun IntSize.coerceAtMost(maximumValue: IntSize): IntSize {
    return coerceIn(maximumValue = maximumValue)
}

@Stable
fun IntSize.coerceAtLeast(minimumValue: Size): IntSize {
    return coerceAtLeast(minimumValue.roundToIntSize())
}

@Stable
fun IntSize.coerceAtMost(maximumValue: Size): IntSize {
    return coerceAtMost(maximumValue.roundToIntSize())
}

// Density

val Dp.px: Float
    @Composable
    get() = LocalDensity.current.run { toPx() }

val TextUnit.px: Float
    @Composable
    get() = LocalDensity.current.run { toPx() }

val Number.dp: Dp
    @Composable
    get() = LocalDensity.current.run { toFloat().toDp() }

val Number.sp: TextUnit
    @Composable
    get() = LocalDensity.current.run { toFloat().toSp() }

val DpSize.px: Size
    @Composable
    get() = LocalDensity.current.run { toSize() }

val Size.dp: DpSize
    @Composable
    get() = LocalDensity.current.run { toDpSize() }

val IntSize.dp: DpSize
    @JvmName("getDpIntSize")
    @Composable
    get() = LocalDensity.current.run { toSize().dp }

