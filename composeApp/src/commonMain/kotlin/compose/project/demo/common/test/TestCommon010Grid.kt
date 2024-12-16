package compose.project.demo.common.test

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AtomicReference
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.asState
import compose.project.demo.common.utils.logD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object TestCommon010Grid : TestCase<TestCommon010Grid> {

    private val ids = AtomicReference(0)

    private val list = mutableStateListOf<ItemData>()

    private val updateEvent = MutableStateFlow(Uuid.random() to "")

    init {
        reset()
    }

    private fun reset() {
        ids.compareAndSet(ids.get(), 0)
        list.clear()
        list += mutableStateListOf(
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
            newItem(),
        )
        updateEvent.value = Uuid.random() to "empty"
    }

    private fun updateData(itemData: ItemData, action: String) {
        val id = itemData.id
        val name = itemData.name.value
        val style = itemData.style
        updateEvent.value = Uuid.random() to "$action $id $name $style"
    }

    private data class ItemData(
        val id: Int,
        val name: MutableStateFlow<Int>,
        val style: Int,
    )

    private fun newId(): Int {
        while (true) {
            val last = ids.get()
            if (ids.compareAndSet(last, last + 1)) {
                return last + 1
            }
        }
    }

    private fun newItem(): ItemData {
        val itemData: ItemData? = null
        return itemData.newItem()
    }

    private fun ItemData?.newItem(): ItemData {
        val id = newId()
        val name = this?.name?.value
        val style = this?.style
        return ItemData(
            id = id,
            name = MutableStateFlow(name ?: id),
            style = style ?: (id % 3),
        )
    }

    @Composable
    override fun BoxScope.Content() {
        LaunchedEffect(null) {
            TAG.logD { "LaunchedEffect" }
            launch {
                updateEvent.collect { (_, action) ->
                    TAG.logD { "collect $action" }
                }
            }
        }
        DisposableEffect(null) {
            TAG.logD { "DisposableEffect" }
            onDispose {
                TAG.logD { "onDispose" }
                reset()
            }
        }
        val state = rememberLazyGridState()
        TAG.logD { "LazyColumn" }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            state = state,
            contentPadding = PaddingValues(8.dp),
        ) {
            itemsIndexed(
                items = list,
                span = { _, it ->
                    val id = it.id
                    var name = it.name.value
                    val style = it.style
                    TAG.logD { "itemsIndexed span $id $name $style $maxLineSpan $maxCurrentLineSpan" }
                    GridItemSpan(when (style) { 0 -> { 1 } 1 -> { 2 } 2 -> { maxCurrentLineSpan } else -> { maxLineSpan } })
                },
//                key = { _, it -> it.id },
//                contentType = { _, it -> it.style },
            ) { i, it ->
                Column(
                    modifier = Modifier.height(200.dp),
                ) {
                    val id = it.id
                    var name by it.name.asState
                    val style = it.style
                    TAG.logD { "itemsIndexed $id $name $style" }
                    Text(
                        text = "$id    $name    $style", color = when (style) { 0 -> { Color.Red } 1 -> { Color.Green } 2 -> { Color.Blue } else -> { Color.Black } },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            list.add(i + 1, it.newItem())
                            updateData(it, "insert")
                        },
                    ) {
                        Text("insert")
                    }
                    Button(
                        onClick = {
                            list -= it
                            updateData(it, "remove")
                        }
                    ) {
                        Text("remove")
                    }
                    Button(
                        onClick = {
                            name += 1
                            updateData(it, "update")
                        }
                    ) {
                        Text("update")
                    }
                }
            }
        }
    }
}