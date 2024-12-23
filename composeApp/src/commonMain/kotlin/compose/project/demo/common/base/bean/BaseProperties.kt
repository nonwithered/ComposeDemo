package compose.project.demo.common.base.bean

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface BasePropertyOwner<V> {

    fun getPropertyValue(type: KClass<*>, k: String): V?

    fun setPropertyValue(type: KClass<*>, k: String, v: V?)
}

data class PropertyProxy<V, T : V>(
    val k: String,
    val type: KClass<*>,
) {

    fun getValue(owner: BasePropertyOwner<V>): T? {
        val v = owner.getPropertyValue(type, k)
        if (v === null) {
            return null
        }
        if (!type.isInstance(v)) {
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return v as? T
    }

    fun setValue(owner: BasePropertyOwner<V>, v: T?) {
        if (v === null) {
            owner.setPropertyValue(type, k, null)
            return
        }
        if (!type.isInstance(v)) {
            return
        }
        owner.setPropertyValue(type, k, v)
    }

    operator fun getValue(owner: BasePropertyOwner<V>, property: KProperty<*>): T? {
        return getValue(owner)
    }

    operator fun setValue(owner: BasePropertyOwner<V>, property: KProperty<*>, v: T?) {
        setValue(owner, v)
    }
}

inline fun <V, reified T : V> createPropertyProxy(owner: BasePropertyOwner<V>, k: String, v: T?): PropertyProxy<V, T> {
    val type = T::class
    if (v !== null) {
        owner.setPropertyValue(type, k, v)
    }
    return PropertyProxy(k, type)
}

abstract class BaseProperties<V> : BasePropertyOwner<V> {

    protected inline fun <reified T : V> String.property(v: T? = null): PropertyProxy<V, T> {
        return createPropertyProxy(this@BaseProperties, this, v)
    }
}
