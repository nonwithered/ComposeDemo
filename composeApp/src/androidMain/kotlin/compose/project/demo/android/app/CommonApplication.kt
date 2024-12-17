package compose.project.demo.android.app

import android.app.Application
import android.content.Context

class CommonApplication : Application() {

    companion object {

        lateinit var globalApplication: Application
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        globalApplication = this
    }
}
