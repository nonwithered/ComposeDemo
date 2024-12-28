package compose.project.demo.common.utils

import compose.project.demo.common.base.bean.BaseValueProxy
import kotlinx.atomicfu.atomic

sealed class BaseAtomicProxy<T> : BaseValueProxy<T>() {

    abstract fun lazySet(value: T)

    abstract fun compareAndSet(expect: T, update: T): Boolean

    abstract fun getAndSet(value: T): T
}

class AtomicIntProxy(initial: Int) : BaseAtomicProxy<Int>() {

    private val atomicProperty = atomic(initial)

    override var value: Int
        get() = atomicProperty.value
        set(value) {
            atomicProperty.value = value
        }

    override fun lazySet(value: Int) {
        atomicProperty.lazySet(value)
    }

    override fun compareAndSet(expect: Int, update: Int): Boolean {
        return atomicProperty.compareAndSet(expect, update)
    }

    override fun getAndSet(value: Int): Int {
        return atomicProperty.getAndSet(value)
    }
}

class AtomicLongProxy(initial: Long) : BaseAtomicProxy<Long>() {

    private val atomicProperty = atomic(initial)

    override var value: Long
        get() = atomicProperty.value
        set(value) {
            atomicProperty.value = value
        }

    override fun lazySet(value: Long) {
        atomicProperty.lazySet(value)
    }

    override fun compareAndSet(expect: Long, update: Long): Boolean {
        return atomicProperty.compareAndSet(expect, update)
    }

    override fun getAndSet(value: Long): Long {
        return atomicProperty.getAndSet(value)
    }
}

class AtomicBooleanProxy(initial: Boolean) : BaseAtomicProxy<Boolean>() {

    private val atomicProperty = atomic(initial)

    override var value: Boolean
        get() = atomicProperty.value
        set(value) {
            atomicProperty.value = value
        }

    override fun lazySet(value: Boolean) {
        atomicProperty.lazySet(value)
    }

    override fun compareAndSet(expect: Boolean, update: Boolean): Boolean {
        return atomicProperty.compareAndSet(expect, update)
    }

    override fun getAndSet(value: Boolean): Boolean {
        return atomicProperty.getAndSet(value)
    }
}

class AtomicRefProxy<T>(initial: T) : BaseAtomicProxy<T>() {

    private val atomicProperty = atomic(initial)

    override var value: T
        get() = atomicProperty.value
        set(value) {
            atomicProperty.value = value
        }

    override fun lazySet(value: T) {
        atomicProperty.lazySet(value)
    }

    override fun compareAndSet(expect: T, update: T): Boolean {
        return atomicProperty.compareAndSet(expect, update)
    }

    override fun getAndSet(value: T): T {
        return atomicProperty.getAndSet(value)
    }
}
