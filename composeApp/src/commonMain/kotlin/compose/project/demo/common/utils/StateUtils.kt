package compose.project.demo.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T, R> State<T>.map(block: (T) -> R): State<R> {
    val state = this
    return object : State<R> {

        override val value: R
            get() = block(state.value)
    }
}

fun <T> State<T>.toMutableState(block: (T) -> Unit): MutableState<T> {
    val state = this
    return object : MutableState<T> {

        override var value: T
            get() = state.value
            set(value) {
                block(value)
            }

        override fun component1() = value

        override fun component2() = { it: T -> value = it }
    }
}

@Composable
fun <T> MutableStateFlow<T>.collectAsMutableState(
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T> {
    return collectAsState(context).toMutableState {
        value = it
    }
}

val <T> StateFlow<T>.asState: State<T>
    @Composable
    get() = collectAsState()

val <T> MutableStateFlow<T>.asState: MutableState<T>
    @Composable
    get() = collectAsMutableState()

fun <T> MutableStateFlowVolatile(value: T): MutableStateFlow<T> = MutableStateFlowVolatileImpl(value)

private class MutableStateFlowVolatileImpl<T>(
    value: T,
) : MutableStateFlow<T> {

    private val data = MutableStateFlow(0 to value)

    private val version: Int
        get() = data.value.first

    private val nextVersion: Int
        get() = version + 1

    override var value: T
        get() = data.value.second
        set(value) {
            data.value = nextVersion to value
        }

    override fun compareAndSet(expect: T, update: T): Boolean {
        return data.compareAndSet(version to expect, nextVersion to update)
    }

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        data.collect {
            collector.emit(it.second)
        }
    }

    override suspend fun emit(value: T) {
        data.emit(nextVersion to value)
    }

    override fun tryEmit(value: T): Boolean {
        return data.tryEmit(nextVersion to value)
    }

    override fun resetReplayCache() {
        data.resetReplayCache()
    }

    override val replayCache: List<T>
        get() = data.replayCache.map { it.second }

    override val subscriptionCount: StateFlow<Int>
        get() = data.subscriptionCount
}

@Composable
fun <T> StateFlow<T>.collectAsStateVolatile(
    context: CoroutineContext = EmptyCoroutineContext,
): State<T> {
    var version by AtomicIntWrapper(0)
    val state = produceState(version to value, this, context) {
        if (context == EmptyCoroutineContext) {
            collect { value = ++version to it }
        } else withContext(context) {
            collect { value = ++version to it }
        }
    }
    return state.map { it.second }
}

@Composable
fun <T> MutableStateFlow<T>.collectAsMutableStateVolatile(
    context: CoroutineContext = EmptyCoroutineContext,
): MutableState<T> {
    return collectAsStateVolatile(context).toMutableState {
        value = it
    }
}

val <T> StateFlow<T>.asStateVolatile: State<T>
    @Composable
    get() = collectAsStateVolatile()

val <T> MutableStateFlow<T>.asStateVolatile: MutableState<T>
    @Composable
    get() = collectAsMutableStateVolatile()
