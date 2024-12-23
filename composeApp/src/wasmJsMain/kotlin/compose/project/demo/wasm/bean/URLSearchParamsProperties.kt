package compose.project.demo.wasm.bean

import compose.project.demo.common.base.bean.BaseProperties
import org.w3c.dom.url.URLSearchParams
import kotlin.reflect.KClass

open class URLSearchParamsProperties(
    private val search: URLSearchParams = URLSearchParams(),
) : BaseProperties<String>() {

    fun asURLSearchParams(): URLSearchParams = search

    override fun getPropertyValue(type: KClass<*>, k: String): String? {
        return search.get(k)
    }

    override fun setPropertyValue(type: KClass<*>, k: String, v: String?) {
        if (v === null) {
            search.delete(k)
            return
        }
        search.set(k, v)
    }
}
