package compose.project.demo.common.base.bean

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class BaseProperties<V> {

    protected abstract fun getValue(type: KClass<*>, k: String): V?
    protected abstract fun setValue(type: KClass<*>, k: String, v: V?)

    protected inline fun <reified T : V> String.property(v: T? = null): Property<V, T> {
        val owner = this@BaseProperties
        val k = this
        val type = T::class
        if (v !== null) {
            owner.setValue(type, k, v)
        }
        return Property(k, type)
    }

    protected class Property<V, T : V>(
        private val k: String,
        private val type: KClass<*>,
    ) {

        operator fun getValue(owner: BaseProperties<V>, property: KProperty<*>): T? {
            val v = owner.getValue(type, k)
            if (v === null) {
                return null
            }
            if (!type.isInstance(v)) {
                return null
            }
            @Suppress("UNCHECKED_CAST")
            return v as? T
        }

        operator fun setValue(owner: BaseProperties<V>, property: KProperty<*>, v: T?) {
            if (v === null) {
                owner.setValue(type, k, null)
                return
            }
            if (!type.isInstance(v)) {
                return
            }
            owner.setValue(type, k, v)
        }
    }
}
