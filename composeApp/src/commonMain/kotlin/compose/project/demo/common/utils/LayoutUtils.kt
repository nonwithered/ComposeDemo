package compose.project.demo.common.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import kotlin.reflect.KClass
import kotlin.reflect.cast

val Constraints.minSize: IntSize
    get() = minWidth intSize minHeight

val Constraints.maxSize: IntSize
    get() = maxWidth intSize maxHeight

val Measured.measuredSize: IntSize
    get() = measuredWidth intSize measuredHeight

val Placeable.size: IntSize
    get() = width intSize height

inline fun <reified T> IntrinsicMeasurable.parentData(): T? {
    return parentData as? T
}

inline fun <reified T> Measured.parentData(): T? {
    return parentData as? T
}

fun Measurable.measure(
    minWidth: Int = 0,
    maxWidth: Int = Constraints.Infinity,
    minHeight: Int = 0,
    maxHeight: Int = Constraints.Infinity,
): Placeable = measure(Constraints(
    minWidth = minWidth,
    maxWidth = maxWidth,
    minHeight = minHeight,
    maxHeight = maxHeight,
))

interface BaseParentData<T : BaseParentData<T>> {

    fun update(other: T)
}

sealed class ParentDataWeakConverter<T : BaseParentData<T>>(
    private val type: KClass<T>,
) {

    open fun parentData(owner: IntrinsicMeasurable): T? {
        return cast(owner.parentData)
    }

    open fun parentData(owner: Measured): T? {
        return cast(owner.parentData)
    }

    open fun cast(parentData: Any?): T? {
        if (!type.isInstance(parentData)) {
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return parentData as? T
    }
}

class ParentDataConverter<T : BaseParentData<T>>(
    type: KClass<T>,
    val createDefault: () -> T,
) : ParentDataWeakConverter<T>(type) {

    override fun parentData(owner: IntrinsicMeasurable): T {
        return super.parentData(owner) ?: createDefault()
    }

    override fun parentData(owner: Measured): T {
        return super.parentData(owner) ?: createDefault()
    }

    override fun cast(parentData: Any?): T {
        return super.cast(parentData) ?: createDefault()
    }
}

interface ParentDataHolder<T : BaseParentData<T>> {

    val parentData: T
}

abstract class BaseModifierNodeElement<N, T : BaseParentData<T>>(
    private val type: KClass<out BaseModifierNodeElement<N, T>>,
) : ModifierNodeElement<N>() where N : Modifier.Node, N : ParentDataHolder<T> {

    protected abstract fun createParentData(): T

    override fun hashCode(): Int {
        return createParentData().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (!type.isInstance(other)) {
            return false
        }
        return createParentData() == type.cast(other).createParentData()
    }

    override fun update(node: N) {
        node.parentData.update(createParentData())
    }
}
