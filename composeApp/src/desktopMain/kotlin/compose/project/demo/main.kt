package compose.project.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import compose.project.demo.common.test.collect.TestCommonCollector

fun main() = application {
    val indexState = remember {
        mutableStateOf(-1)
    }
    var index by indexState
    Window(
        onCloseRequest = {
            if (index != -1) {
                index = -1
            } else {
                exitApplication()
            }
        },
        title = "ComposeDemo",
    ) {
        Content(indexState)
    }
}

@Composable
private fun Content(
    indexState: MutableState<Int>,
) {
    val items = TestCommonCollector.list
    val scrollState = rememberScrollState()

    var index by indexState

    if (index in items.indices) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            items[index].run {
                view()
            }
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(state = scrollState),
    ) {
        items.forEachIndexed { i, it ->
            Button(
                onClick = {
                    index = i
                }
            ) {
                Text(text = it.name)
            }
        }
    }
}
