package compose.project.demo.android.base.bean

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import compose.project.demo.common.base.bean.BaseProperties
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

open class BundleProperties(
    private val bundle: Bundle = Bundle(),
) : BaseProperties<Any>() {

    fun asBundle(): Bundle = bundle

    @SuppressLint("NewApi")
    override fun getPropertyValue(type: KClass<*>, k: String): Any? {
        return bundle.run {
            when (type) {
                Boolean::class -> getBoolean(k)
                Int::class -> getInt(k)
                Long::class -> getLong(k)
                Short::class -> getShort(k)
                Byte::class -> getByte(k)
                Float::class -> getFloat(k)
                Double::class -> getDouble(k)
                Char::class -> getChar(k)
                String::class -> getString(k)
                else -> when {
                    type.isSubclassOf(Bundle::class) -> getBundle(k)
                    type.isSubclassOf(Serializable::class) -> @Suppress("UNCHECKED_CAST") getSerializable(k, type.java as Class<out Serializable>)
                    type.isSubclassOf(Parcelable::class) -> @Suppress("UNCHECKED_CAST") getParcelable(k, type.java as Class<out Parcelable>)
                    else -> throw ClassCastException("$k $type")
                }
            }
        }
    }

    override fun setPropertyValue(type: KClass<*>, k: String, v: Any?) {
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
