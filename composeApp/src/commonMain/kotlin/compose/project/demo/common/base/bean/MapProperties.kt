package compose.project.demo.common.base.bean

import kotlin.reflect.KClass

open class MapProperties(
    private val map: MutableMap<String, Any?> = mutableMapOf(),
) : BaseProperties<Any>() {

    fun asMap(): Map<String, Any?> = map

    override fun getValue(type: KClass<*>, k: String): Any? {
        return map[k]
    }

    override fun setValue(type: KClass<*>, k: String, v: Any?) {
        if (v === null) {
            map -= k
            return
        }
        map[k] = v
    }
}
