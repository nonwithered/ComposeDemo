package compose.project.demo.android.utils

import android.app.Application
import compose.project.demo.android.app.CommonApplication

val app: Application
    get() = CommonApplication.globalApplication
