package compose.project.demo.common.test

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.intOffset

@OptIn(ExperimentalFoundationApi::class)
object TestCommon020Gestures : TestCase<TestCommon020Gestures> {

    private val list = listOf<@Composable BoxScope.() -> Unit>(
        { TestClickable() },
        { TestToggleable() },
        { TestSelectable() },
        { TestCombinedClickable() },
        { TestTriStateToggleable() },
        { TestDraggable() },
        { TestTransformable() },
    )

    @Composable
    override fun BoxScope.Content() {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = rememberPagerState(pageCount = { list.size }),
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                list[page]()
            }
        }
    }

    @Composable
    private fun BoxScope.TestClickable() {
        var state by remember {
            mutableStateOf(0)
        }
        Text(
            text = "clickable $state",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .background(Color.Blue)
                .clickable {
                    state++
                },
        )
    }

    @Composable
    private fun BoxScope.TestToggleable() {
        var state by remember {
            mutableStateOf(false)
        }
        Text(
            text = "toggleable $state",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .background(Color.Blue)
                .toggleable(state) {
                    state = it
                },
        )
    }

    @Composable
    private fun BoxScope.TestSelectable() {
        var state by remember {
            mutableStateOf(0)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "selectable ${ state == 0 }",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .weight(1f)
                    .background(Color.Blue)
                    .selectable(state == 0) {
                        state = 0
                    },
            )
            Text(
                text = "selectable ${ state == 1 }",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .weight(1f)
                    .background(Color.Blue)
                    .selectable(state == 1) {
                        state = 1
                    },
            )
        }
    }

    @Composable
    private fun BoxScope.TestCombinedClickable() {
        var longClick by remember {
            mutableStateOf(0)
        }
        var doubleClick by remember {
            mutableStateOf(0)
        }
        var click by remember {
            mutableStateOf(0)
        }
        Text(
            text = "click $click\nlongClick $longClick\ndoubleClick $doubleClick",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .background(Color.Blue)
                .combinedClickable(
                    onLongClick = {
                        longClick++
                    },
                    onDoubleClick = {
                        doubleClick++
                    },
                    onClick = {
                        click++
                    },
                ),
        )
    }

    @Composable
    private fun BoxScope.TestTriStateToggleable() {
        var checked by remember { mutableStateOf(ToggleableState.Indeterminate) }
        Text(
            text = "toggleable $checked",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .background(Color.Blue)
                .triStateToggleable(checked) {
                    checked = when (checked) {
                        ToggleableState.On -> ToggleableState.Off
                        ToggleableState.Off -> ToggleableState.Indeterminate
                        ToggleableState.Indeterminate -> ToggleableState.On
                    }
                },
        )
    }

    @Composable
    private fun BoxScope.TestDraggable() {
        var deltaX by remember {
            mutableStateOf(0f)
        }
        var deltaY by remember {
            mutableStateOf(0f)
        }
        Text(
            text = "draggable",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .offset {
                    deltaX intOffset deltaY
                }
                .background(Color.Blue)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        deltaY += delta
                    },
                ).draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        deltaX += delta
                    },
                ),
        )
    }

    @Composable
    private fun BoxScope.TestTransformable() {
        var scale by remember { mutableStateOf(1f) }
        var rotation by remember { mutableStateOf(0f) }
        var offset by remember { mutableStateOf(IntOffset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            offset += offsetChange.round()
            rotation += rotationChange
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.9f)
                .transformable(state)
                .background(Color.LightGray),
        ) {
            Text(
                text = "transformable",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
                    .offset { offset }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        rotationZ = rotation
                    }
                    .background(Color.Blue),
            )
        }
    }
}