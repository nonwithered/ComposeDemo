package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.forEach
import compose.project.demo.common.utils.swap
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.getOrElse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object TestCommon021Loadmore : TestCase<TestCommon021Loadmore> {

    @Composable
    override fun BoxScope.Content() {
        val repo = remember {
            Repo()
        }
        LaunchedEffect(repo) {
            repo.loop()
        }
        val repoState by repo.state.collectAsState()
        val state = rememberLazyListState()
        // TODO
        when (repoState) {
            State.FAIL -> {
                // TODO
            }
            else -> {
                RepoList(repo.itemsChannel, state)
            }
        }
    }

    @Composable
    private fun RepoList(itemsChannel: Channel<List<Item>>, state: LazyListState) {
        var items by remember {
            mutableStateOf(
                value = itemsChannel.tryReceive().getOrElse { listOf() },
                policy = neverEqualPolicy(),
            )
        }
        LaunchedEffect(itemsChannel) {
            itemsChannel.forEach {
                items = it
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
        ) {
            itemsIndexed(
                items = items,
                key = { index, item -> item.id},
                contentType = { index, item -> 0 },
            ) { index, item ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().background(Color.Blue).padding(2.dp),
                        text = item.run { "$id $name ${count.collectAsState()}" },
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }

    private class Item(
        val id: Int,
        val name: String,
    ) {
        var count = MutableStateFlow(0)
    }

    private class Repo : BaseRepo<Item, Unit, List<Item>>() {

        private var ids by atomic(0)

        override suspend fun onRequest(type: Type, params: Unit): Result<List<Item>> {
            delay(2000)
            val list = mutableListOf<Item>()
            repeat(6) { i ->
                val id = ++ids
                list += Item(id, ('a' + (id % 26)).toString() + " " + type)
            }
            return Result.success(list)
        }

        override suspend fun onConvert(type: Type, params: Unit, response: List<Item>): List<Item> {
            return response
        }
    }

    private abstract class BaseRepo<T, P, R> {

        val itemsChannel = Channel<List<T>>(
            capacity = Channel.CONFLATED,
        )

        val state: StateFlow<State>
            get() = stateMut

        abstract suspend fun onRequest(type: Type, params: P): Result<R>

        abstract suspend fun onConvert(type: Type, params: P, response: R): List<T>?

        private val stateMut = MutableStateFlow(State.INIT)

        private val items = mutableListOf<T>()

        private val paramsChannel = Channel<LoopParams<P>>(
            capacity = Channel.RENDEZVOUS,
        )

        private val eventChannel = Channel<LoopEvent>(
            capacity = Channel.UNLIMITED,
        )

        private val paramsQueue = ArrayDeque<LoopParams<P>>()

        private val coroutineScope = atomic<CoroutineScope?>(null)

        private suspend fun notifyChanged() {
            itemsChannel.send(items)
        }

        suspend fun loop() {
            coroutineScope {
                try {
                    coroutineScope.swap(this)?.cancel()
                    launch {
                        filterParams()
                    }
                    onLoop()
                } finally {
                    coroutineScope.compareAndSet(this, null)
                }
            }
        }

        private suspend fun filterParams() {
            eventChannel.forEach { ev ->
                when (ev) {
                    is LoopNotify -> {
                        val params = paramsQueue.removeFirstOrNull()
                        if (params !== null) {
                            paramsChannel.send(params)
                        }
                    }
                    is LoopParams<*> -> {
                        @Suppress("UNCHECKED_CAST")
                        val params = ev as LoopParams<P>
                        // TODO
                        paramsQueue.addLast(params)
                    }
                }
            }
        }

        private suspend fun onLoop() {
            paramsChannel.forEach {
                loopOnce(it)
                eventChannel.send(LoopNotify)
            }
        }

        private suspend fun loopOnce(params: LoopParams<P>) {
            val type = params.type
            val p = params.p
            val r = onRequest(type, p).onFailure { e ->
                if (stateMut.value == State.INIT && items.isEmpty()) {
                    stateMut.value = State.FAIL
                }
            }.getOrNull() ?: return
            val result = onConvert(type, p, r)
            if (type == Type.REFRESH) {
                items.clear()
            }
            if (result === null) {
                stateMut.value = State.END
            } else {
                items += result
                stateMut.value = State.SHOW
            }
            notifyChanged()
        }

        fun request(type: Type, p: P) {
            val params = LoopParams(
                type = type,
                p = p,
            )
            eventChannel.trySend(params)
        }

        suspend fun <R> change(block: suspend (MutableList<T>) -> R): Result<R> {
            val coroutineScope = coroutineScope.value ?: return Result.failure(NullPointerException())
            return withContext(coroutineScope.coroutineContext) {
                val r = block(items)
                notifyChanged()
                Result.success(r)
            }
        }

        suspend fun <R> collect(block: suspend (List<T>) -> R): Result<R> {
            val coroutineScope = coroutineScope.value ?: return Result.failure(NullPointerException())
            return withContext(coroutineScope.coroutineContext) {
                val r = block(items)
                Result.success(r)
            }
        }

        fun tryChange(block: suspend (MutableList<T>) -> Unit): Boolean {
            val coroutineScope = coroutineScope.value ?: return false
            coroutineScope.launch {
                block(items)
                notifyChanged()
            }
            return true
        }

        fun tryCollect(block: suspend (List<T>) -> Unit): Boolean {
            val coroutineScope = coroutineScope.value ?: return false
            coroutineScope.launch {
                block(items)
            }
            return true
        }

        private sealed class LoopEvent

        private class LoopParams<P>(
            val type: Type,
            val p: P,
        ) : LoopEvent()

        private object LoopNotify : LoopEvent()
    }

    private enum class Type {

        REFRESH,
        LOAD,
    }

    private enum class State {

        INIT,
        SHOW,
        FAIL,
        END,
    }
}
