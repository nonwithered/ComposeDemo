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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.forEach
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.locks.ReentrantLock
import kotlinx.atomicfu.locks.withLock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.Volatile

object TestCommon021Loadmore : TestCase<TestCommon021Loadmore> {

    private const val BUF = 10

    @Composable
    override fun BoxScope.Content() {
        val repo = remember {
            Repo()
        }
        LaunchedEffect(Unit) {
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
                RepoList(repo.items, state)
            }
        }
    }

    @Composable
    private fun RepoList(items: List<Item>, state: LazyListState) {
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

    private class Repo : BaseRepo<Item, List<Item>>() {

        private var ids by atomic(0)

        override suspend fun onRequest(type: Type): Result<List<Item>> {
            delay(2000)
            val list = mutableListOf<Item>()
            repeat(6) { i ->
                val id = ++ids
                list += Item(id, ('a' + (id % 26)).toString() + " " + type)
            }
            return Result.success(list)
        }

        override suspend fun onConvert(response: List<Item>): List<Item> {
            return response
        }
    }

    private abstract class BaseRepo<T, R> {

        val items: List<T>
            get() = list

        val state: StateFlow<State>
            get() = stateFlow

        abstract suspend fun onRequest(type: Type): Result<R>

        abstract suspend fun onConvert(response: R): List<T>

        private val stateFlow = MutableStateFlow(State.INIT)

        private val list = mutableStateListOf<T>()

        private val channel = Channel<Type>(capacity = Channel.UNLIMITED)

        @Volatile
        private var coroutineScope: CoroutineScope? = null

        private val lock = ReentrantLock()

        suspend fun loop() {
            coroutineScope {
                try {
                    lock.withLock {
                        coroutineScope?.cancel()
                        coroutineScope = this
                    }
                    onLoop()
                } finally {
                    lock.withLock {
                        if (coroutineScope === this) {
                            coroutineScope = null
                        }
                    }
                }
            }
        }

        private suspend fun onLoop() {
            channel.forEach { type ->
                onRequest(type).onFailure { e ->
                    if (list.isEmpty()) {
                        stateFlow.value = State.FAIL
                    }
                }.onSuccess { r ->
                    val result = onConvert(r)
                    list.clear()
                    list += result
                    if (list.isEmpty()) {
                        stateFlow.value = State.END
                    } else {
                        stateFlow.value = State.SHOW
                    }
                }
            }
        }

        fun request(type: Type) {
            channel.trySend(type)
        }

        suspend fun <R> change(block: suspend (MutableList<T>) -> R): Result<R> {
            val coroutineScope = coroutineScope ?: return Result.failure(NullPointerException())
            return runCatching {
                withContext(coroutineScope.coroutineContext) { block(list) }
            }
        }

        fun tryChange(block: suspend (MutableList<T>) -> Unit): Boolean {
            lock.withLock {
                val coroutineScope = coroutineScope
                if (coroutineScope !== null) {
                    coroutineScope.launch { block(list) }
                    return true
                }
                return false
            }
        }
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