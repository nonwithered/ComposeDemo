package compose.project.demo.android.base.bean

import android.annotation.SuppressLint
import android.os.Bundle
import compose.project.demo.common.base.bean.BaseProperties
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

open class BundleProperties(
    private val bundle: Bundle = Bundle(),
) : BaseProperties<Any>() {

    fun asBundle(): Bundle = bundle

    @SuppressLint("NewApi")
    override fun getValue(type: KClass<*>, k: String): Any? {
        return when (type) {
            Boolean::class -> bundle.getBoolean(k)
            Int::class -> bundle.getInt(k)
            Long::class -> bundle.getLong(k)
            Short::class -> bundle.getShort(k)
            Byte::class -> bundle.getByte(k)
            Float::class -> bundle.getFloat(k)
            Double::class -> bundle.getDouble(k)
            Char::class -> bundle.getChar(k)
            String::class -> bundle.getString(k)
            else -> when {
                type.isSubclassOf(Serializable::class) -> @Suppress("UNCHECKED_CAST") bundle.getSerializable(k, type.java as Class<out Serializable>)
                type.isSubclassOf(Bundle::class) -> bundle.getBundle(k)
                else -> throw ClassCastException("$k $type")
            }
        }
    }

    override fun setValue(type: KClass<*>, k: String, v: Any?) {
        if (v === null) {
            bundle.remove(k)
            return
        }
        when (type) {
            Boolean::class -> bundle.putBoolean(k, v as Boolean)
            Int::class -> bundle.putInt(k, v as Int)
            Long::class -> bundle.putLong(k, v as Long)
            Short::class -> bundle.putShort(k, v as Short)
            Byte::class -> bundle.putByte(k, v as Byte)
            Float::class -> bundle.putFloat(k, v as Float)
            Double::class -> bundle.putDouble(k, v as Double)
            Char::class -> bundle.putChar(k, v as Char)
            String::class -> bundle.putString(k, v as String)
            else -> when {
                type.isSubclassOf(Serializable::class) -> bundle.putSerializable(k, v as Serializable)
                type.isSubclassOf(Bundle::class) -> bundle.putBundle(k, v as Bundle)
                else -> throw ClassCastException("$k $type")
            }
        }
    }
}
