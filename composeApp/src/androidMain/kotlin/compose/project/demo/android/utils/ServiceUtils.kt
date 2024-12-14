package compose.project.demo.android.utils

import java.util.ServiceLoader

fun <T> loadService(type: Class<T>): List<T> {
    return ServiceLoader.load(type).iterator().asSequence().toList()
}

inline fun <reified T> loadService(): List<T> {
    return loadService(T::class.java)
}
