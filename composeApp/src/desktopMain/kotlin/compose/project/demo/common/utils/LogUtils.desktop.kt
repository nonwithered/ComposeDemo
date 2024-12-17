package compose.project.demo.common.utils

actual fun String.log(level: LogLevel, tag: String, msg: String, e: Throwable?) {
    println("${level.simpleName} $tag: $msg\n${e?.stackTraceToString() ?: ""}")
}
