package compose.project.demo.android.utils

import androidx.annotation.StringRes

val @receiver:StringRes Int.resString: String
    get() = app.resources.getString(this)
