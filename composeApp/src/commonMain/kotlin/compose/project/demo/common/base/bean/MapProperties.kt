package compose.project.demo.common.base.bean

import kotlin.reflect.KClass

abstract class MapProperties<T>(
    private val map: MutableMap<String, T?> = mutableMapOf(),
) : BaseProperties<T>() {

    fun asMap(): Map<String, T?> = map

    override fun getValue(type: KClass<*>, k: String): T? {
        return map[k]
    }

    override fun setValue(type: KClass<*>, k: String, v: T?) {
        if (v === null) {
            map -= k
            return
        }
        map[k] = v
    }
}
