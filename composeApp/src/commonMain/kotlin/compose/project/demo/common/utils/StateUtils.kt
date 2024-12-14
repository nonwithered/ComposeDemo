package compose.project.demo.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MutableStateProxy<T>(
    private val state: State<T>,
    private val put: (T) -> Unit,
) : MutableState<T> {

    override var value: T
        get() = state.value
        set(value) {
            put(value)
        }

    override fun component1() = value

    override fun component2() = { it: T -> value = it }
}

@Composable
fun <T> MutableStateFlow<T>.collectAsMutableState(
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T> {
    return MutableStateProxy(collectAsState(context)) {
        value = it
    }
}

val <T> StateFlow<T>.asState: State<T>
    @Composable
    get() = collectAsState()

val <T> MutableStateFlow<T>.asState: MutableState<T>
    @Composable
    get() = collectAsMutableState()

private fun <T> LifecycleOwner.collect(flow: Flow<T>, collector: FlowCollector<T>): () -> Unit {
    val job = lifecycleScope.launch {
        flow.collect(collector)
    }
    return {
        job.cancel()
    }
}

fun <T> LifecycleOwner.bind(state: Flow<T>, block: (T) -> Unit): () -> Unit {
    return collect(state, block)
}
