package compose.project.demo.common.utils

import compose.project.demo.common.base.bean.BaseValueProxy
import kotlinx.atomicfu.atomic

class AtomicIntProxy(value: Int) : BaseValueProxy<Int>() {

    override var value: Int by atomic(value)
}

class AtomicLongProxy(value: Long) : BaseValueProxy<Long>() {

    override var value: Long by atomic(value)
}

class AtomicBooleanProxy(value: Boolean) : BaseValueProxy<Boolean>() {

    override var value: Boolean by atomic(value)
}

class AtomicRefProxy<T>(value: T) : BaseValueProxy<T>() {

    override var value: T by atomic(value)
}
