package compose.project.demo.android.select

import android.os.Bundle
import androidx.fragment.app.Fragment
import compose.project.demo.android.base.bean.BundleProperties

class SelectPageData(
    bundle: Bundle? = null,
): BundleProperties(bundle ?: Bundle()) {

    private var fragmentClassName: String? by "fragmentClassName".property()

    var fragmentClass: Class<out Fragment>?
        get() {
            val fragmentClassName = fragmentClassName ?: return null
            val clazz = runCatching {
                Class.forName(fragmentClassName)
            }.getOrNull()
            @Suppress("UNCHECKED_CAST")
            return clazz as? Class<out Fragment>
        }
        set(value) {
            fragmentClassName = value?.name
        }

    var pageName: String? by "pageName".property()

    var showActionBar: Boolean? by "show".property()

    var extras: Bundle? by "extras".property()
}
