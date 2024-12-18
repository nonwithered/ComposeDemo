package compose.project.demo.common.base.bean

import kotlin.reflect.KProperty

abstract class BaseValueProxy<T> {

    protected abstract var value: T

    override fun equals(other: Any?): Boolean {
        return value == other
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }

    operator fun getValue(owner: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(owner: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}
