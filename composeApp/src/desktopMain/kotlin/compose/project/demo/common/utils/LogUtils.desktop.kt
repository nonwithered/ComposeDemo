package compose.project.demo.common.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual fun String.log(level: LogLevel, tag: String, msg: String, e: Throwable?) {
    val zone = TimeZone.currentSystemDefault()
    val time = Clock.System.now().toLocalDateTime(zone)
    val priority = level.simpleName
    val stackTrace = e?.stackTraceToString() ?: ""
    println("$time $priority $tag $msg\n$stackTrace")
}
