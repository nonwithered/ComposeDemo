package compose.project.demo.common.utils

import compose.project.demo.common.base.bean.BaseValueProxy
import kotlinx.atomicfu.atomic

class AtomicIntWrapper(value: Int) : BaseValueProxy<Int>() {

    override var value: Int by atomic(value)
}

class AtomicLongWrapper(value: Long) : BaseValueProxy<Long>() {

    override var value: Long by atomic(value)
}

class AtomicBooleanWrapper(value: Boolean) : BaseValueProxy<Boolean>() {

    override var value: Boolean by atomic(value)
}

class AtomicRefWrapper<T>(value: T) : BaseValueProxy<T>() {

    override var value: T by atomic(value)
}
