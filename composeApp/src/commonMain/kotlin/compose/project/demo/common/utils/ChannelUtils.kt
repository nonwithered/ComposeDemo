package compose.project.demo.common.utils

import kotlinx.coroutines.channels.ReceiveChannel

suspend fun <E> ReceiveChannel<E>.forEach(block: suspend (E) -> Unit) {
    val iterator = iterator()
    while (iterator.hasNext()) {
        block(iterator.next())
    }
}
