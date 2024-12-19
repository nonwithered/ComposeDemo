package compose.project.demo.android.base.bean

import compose.project.demo.common.base.bean.BaseProperties
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

abstract class JsonProperties(
    private val json: JSONObject = JSONObject(),
) : BaseProperties<Any>() {

    fun asJson(): JSONObject = json

    override fun getValue(type: KClass<*>, k: String): Any? {
        return json.runCatching { when (type) {
            Boolean::class -> getBoolean(k)
            Int::class -> getInt(k)
            Long::class -> getLong(k)
            Short::class -> getInt(k).toShort()
            Byte::class -> getInt(k).toByte()
            Float::class -> getDouble(k).toFloat()
            Double::class -> getDouble(k)
            Char::class -> getString(k).takeIf { it.length == 1 }?.first()
            String::class -> getString(k)
            else -> when {
                type.isSubclassOf(JSONArray::class) -> getJSONArray(k)
                type.isSubclassOf(JSONObject::class) -> getJSONObject(k)
                else -> throw ClassCastException("$k $type")
            }
        } }.getOrNull()
    }

    override fun setValue(type: KClass<*>, k: String, v: Any?) {
        if (v === null) {
            json.remove(k)
            return
        }
        json.put(k, v)
    }
}
