package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.MutableStateFlowVolatile
import compose.project.demo.common.utils.asState
import compose.project.demo.common.utils.asStateVolatile
import compose.project.demo.common.utils.logD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.concurrent.Volatile

object TestCommon011Staggered : TestCase<TestCommon011Staggered> {

    @Volatile
    private var ids = 0

    private val list = MutableStateFlowVolatile(mutableListOf<ItemData>())

    init {
        reset()
    }

    private fun reset() {
        ids = 0
        list.value = mutableStateListOf(
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
            newItem(),
            newItem(),
            newItem(),
            newItem(),
        )
    }

    private fun updateData(itemData: ItemData, action: String) {
        val id = itemData.id
        val name = itemData.name.value
        val style = itemData.style.value
    }

    private data class ItemData(
        val id: Int,
        val name: MutableStateFlow<Int>,
        val style: MutableStateFlow<Int>,
    )

    private fun newId(): Int {
        return ++ids
    }

    private fun newItem(): ItemData {
        val itemData: ItemData? = null
        return itemData.newItem()
    }

    private fun ItemData?.newItem(): ItemData {
        val id = newId()
        val name = this?.name?.value
        val style = this?.style?.value
        return ItemData(
            id = id,
            name = MutableStateFlow(name ?: id),
            style = MutableStateFlow(style ?: (id % 4)),
        )
    }

    @Composable
    override fun BoxScope.Content() {
        LaunchedEffect(null) {
            TAG.logD { "LaunchedEffect" }
        }
        DisposableEffect(null) {
            TAG.logD { "DisposableEffect" }
            onDispose {
                TAG.logD { "onDispose" }
                reset()
            }
        }
        val state = rememberLazyStaggeredGridState()
        TAG.logD { "LazyColumn" }
        var items by list.asStateVolatile
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            state = state,
            contentPadding = PaddingValues(8.dp),
        ) {
            itemsIndexed(
                items = items,
                span = { _, it ->
                    if (it.style.value == 3) {
                        StaggeredGridItemSpan.FullLine
                    } else {
                        StaggeredGridItemSpan.SingleLane
                    }
                },
                key = { _, it -> it.id },
//                contentType = { _, it -> it.style },
            ) { i, it ->
                val id = it.id
                var name by it.name.asState
                var style by it.style.asState
                TAG.logD { "itemsIndexed $id $name $style" }
                Column(
                    modifier = Modifier.background(when (style) { 0 -> { Color.Red } 1 -> { Color.Green } 2 -> { Color.Blue } else -> { Color.Black } }),
                ) {
                    Text(
                        text = "$id    $name    $style",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            items = items.apply {
                                add(i + 1, it.newItem())
                            }
                            updateData(it, "insert")
                        },
                    ) {
                        Text("insert")
                    }
                    Button(
                        onClick = {
                            items = items.apply {
                                remove(it)
                            }
                            updateData(it, "remove")
                        }
                    ) {
                        Text("remove")
                    }
                    Button(
                        modifier = Modifier.height(50.dp * (style + 1)),
                        onClick = {
                            name += 1
                            style = (style + 1) % 4
                            items = items
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
