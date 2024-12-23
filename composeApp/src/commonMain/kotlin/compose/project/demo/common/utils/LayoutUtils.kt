package compose.project.demo.common.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import compose.project.demo.common.base.bean.BasePropertyOwner
import compose.project.demo.common.base.bean.MapProperties
import compose.project.demo.common.base.bean.PropertyProxy
import compose.project.demo.common.base.bean.createPropertyProxy
import kotlin.reflect.KClass

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

interface BaseParentData<P : BaseParentData<P>>

class ParentDataConverter<P : BaseParentData<P>>(
    private val type: KClass<P>,
    private val factory: () -> P? = { null },
) {

    fun castOrNull(owner: IntrinsicMeasurable): P? {
        return castOrNull(owner.parentData)
    }

    fun castOrNull(owner: Measured): P? {
        return castOrNull(owner.parentData)
    }

    fun castOrNull(parentData: Any?): P? {
        if (!type.isInstance(parentData)) {
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return parentData as? P
    }

    fun cast(owner: IntrinsicMeasurable): P {
        return cast(owner.parentData)
    }

    fun cast(owner: Measured): P {
        return cast(owner.parentData)
    }

    fun cast(parentData: Any?): P {
        return castOrNull(parentData) ?: factory()!!
    }
}

interface BaseParentDataModifierNode<P : BaseParentData<P>> : ParentDataModifierNode {

    val converter: ParentDataConverter<P>

    val optional: Boolean
        get() = false

    fun update(parentData: P, density: Density)

    override fun Density.modifyParentData(parentData: Any?): P? {
        return modifyParentData(parentData, this)
    }

    fun modifyParentData(parentData: Any?, density: Density): P? {
        val attr = if (optional) {
            converter.castOrNull(parentData)
        } else {
            converter.cast(parentData)
        }
        if (attr !== null) {
            update(attr, density)
        }
        return attr
    }
}

abstract class BaseModifierNodeElement<N : Modifier.Node>(
    private val properties: MapProperties<Any> = MapProperties(),
) : ModifierNodeElement<N>() , BasePropertyOwner<Any> by properties {

    protected abstract val type: KClass<out BaseModifierNodeElement<N>>

    protected open val debugInspectorInfo: Boolean
        get() = true

    protected val propertyProxyList: MutableList<PropertyProxy<Any, out Any>> = mutableListOf()

    private val propertyList: List<Any?>
        get() = propertyProxyList.map {
            it.getValue(this)
        }

    protected inline fun <reified T : Any> String.property(v: T): T {
        propertyProxyList += createPropertyProxy(this@BaseModifierNodeElement, this, v)
        return v
    }

    override fun hashCode(): Int {
        return propertyList.combinedHashCode
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (!type.isInstance(other)) {
            return false
        }
        other as BaseModifierNodeElement<*>
        return propertyList combinedEquals other.propertyList
    }

    override fun InspectorInfo.inspectableProperties() {
        if (!debugInspectorInfo) {
            inspectorInfo()
        } else debugInspectorInfo {
            inspectorInfo()
        }
    }

    private fun InspectorInfo.inspectorInfo() {
        name = type.simpleName
        propertyProxyList.forEach {
            properties[it.k] = it.getValue(this@BaseModifierNodeElement).toString()
        }
    }
}
