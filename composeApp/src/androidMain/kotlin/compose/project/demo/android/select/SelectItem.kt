package compose.project.demo.android.select

import android.os.Bundle
import androidx.fragment.app.Fragment

interface SelectItem {

    val fragmentClass: Class<out Fragment>

    val pageName: String
        get() = fragmentClass.simpleName

    val showActionBar: Boolean
        get() = true

    val extras: Bundle?
        get() = null
}
