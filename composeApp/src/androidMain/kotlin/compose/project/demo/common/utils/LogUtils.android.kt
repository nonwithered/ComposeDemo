package compose.project.demo.common.utils

import android.util.Log

actual fun String.log(level: LogLevel, tag: String, msg: String, e: Throwable?) {
    val priority = when (level) {
        LogLevel.VERBOSE -> Log.VERBOSE
        LogLevel.DEBUG -> Log.DEBUG
        LogLevel.INFO -> Log.INFO
        LogLevel.WARN -> Log.WARN
        LogLevel.ERROR -> Log.ERROR
        LogLevel.ASSERT -> Log.ASSERT
    }
    Log.println(priority, tag, "$msg\n${e?.stackTraceToString() ?: ""}")
}
