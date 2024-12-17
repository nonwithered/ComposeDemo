package compose.project.demo.common.utils

enum class LogLevel(
    val priorityName: String,
    val simpleName: String,
) {
    VERBOSE(
        priorityName = "VERBOSE",
        simpleName = "V",
    ),
    DEBUG(
        priorityName = "DEBUG",
        simpleName = "D",
    ),
    INFO(
        priorityName = "INFO",
        simpleName = "I",
    ),
    WARN(
        priorityName = "WARN",
        simpleName = "W",
    ),
    ERROR(
        priorityName = "ERROR",
        simpleName = "E",
    ),
    ASSERT(
        priorityName = "ASSERT",
        simpleName = "A",
    ),
}

expect fun String.log(level: LogLevel, tag: String, msg: String, e: Throwable?)

inline fun String.log(level: LogLevel, e: Throwable? = null, crossinline msg: () -> String) {
    val tag = "log $this"
    log(level, tag, msg(), e)
}

inline fun String.logV(e: Throwable? = null, crossinline msg: () -> String) = log(LogLevel.VERBOSE, e, msg)
inline fun String.logD(e: Throwable? = null, crossinline msg: () -> String) = log(LogLevel.DEBUG, e, msg)
inline fun String.logI(e: Throwable? = null, crossinline msg: () -> String) = log(LogLevel.INFO, e, msg)
inline fun String.logW(e: Throwable? = null, crossinline msg: () -> String) = log(LogLevel.WARN, e, msg)
inline fun String.logE(e: Throwable? = null, crossinline msg: () -> String) = log(LogLevel.ERROR, e, msg)
inline fun String.logA(e: Throwable? = null, crossinline msg: () -> String) = log(LogLevel.ASSERT, e, msg)
