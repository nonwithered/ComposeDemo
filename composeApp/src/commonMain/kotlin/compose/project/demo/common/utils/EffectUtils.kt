package compose.project.demo.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember

enum class RememberObserverState {
    REMEMBERED,
    FORGOTTEN,
    ABANDONED,
}

private class RememberObserverProxy<T>(
    private val owner: T,
    private val block: (owner: T, state: RememberObserverState) -> Unit
) : RememberObserver {

    override fun onRemembered() {
        block(owner, RememberObserverState.REMEMBERED)
    }

    override fun onForgotten() {
        block(owner, RememberObserverState.FORGOTTEN)
    }

    override fun onAbandoned() {
        block(owner, RememberObserverState.ABANDONED)
    }
}

@Composable
fun <T> T.rememberAsObserver(block: T.(state: RememberObserverState) -> Unit) {
    rememberWithObserver(this) { owner, state ->
        owner.block(state)
    }
}

@Composable
fun <K, T> K.rememberWithObserver(owner: T, block: (it: T, state: RememberObserverState) -> Unit) {
    val key = this
    remember(key) {
        RememberObserverProxy(owner, block)
    }
}
