package compose.project.demo.common.test

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.elseTrue
import compose.project.demo.common.utils.elseZero
import compose.project.demo.common.utils.intOffset
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

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
        { TestAnchoredDraggable() },
        { TestDetectGestures() },
        { TestAwaitPointerEvent() },
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

    private enum class DragAnchors {
        Start,
        Center,
        End,
    }

    @Composable
    private fun BoxScope.TestAnchoredDraggable() {
        val density = LocalDensity.current
        val defaultActionSize = 200.dp
        val endActionSizePx = with(density) { defaultActionSize.toPx() }
        val startActionSizePx = with(density) { defaultActionSize.toPx() }
        val state = remember {
            AnchoredDraggableState(
                initialValue = DragAnchors.Center,
                anchors = DraggableAnchors {
                    DragAnchors.Start at -startActionSizePx
                    DragAnchors.Center at 0f
                    DragAnchors.End at endActionSizePx
                },
                positionalThreshold = { distance: Float -> distance * 0.5f },
                velocityThreshold = { with(density) { 100.dp.toPx() } },
                snapAnimationSpec = spring(),
                decayAnimationSpec = exponentialDecay(),
            )
        }
        Text(
            text = "anchoredDraggable",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
                .offset {
                    0 intOffset state.requireOffset()
                }
                .background(Color.Blue)
                .anchoredDraggable(state, Orientation.Vertical),
        )
    }

    @Composable
    private fun BoxScope.TestDetectGestures() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(Color.LightGray),
        ) {
            var detectTapGesturesState by remember {
                mutableStateOf("")
            }
            Text(
                text = "detectTapGestures $detectTapGesturesState",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .background(Color.Red)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = { offset ->
                                detectTapGesturesState = "\nonDoubleTap\n$offset"
                            },
                            onLongPress = { offset ->
                                detectTapGesturesState = "\nonLongPress\n$offset"
                            },
                            onPress = { offset ->
                                detectTapGesturesState = "\nonPress\n$offset"
                            },
                            onTap = { offset ->
                                detectTapGesturesState = "\nonTap\n$offset"
                            },
                        )
                    },
            )
            var detectDragGesturesState by remember {
                mutableStateOf("")
            }
            Text(
                text = "detectDragGestures $detectDragGesturesState",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                detectDragGesturesState = "\nonDragStart\n$offset"
                            },
                            onDragEnd = {
                                detectDragGesturesState = "\nonDragEnd"
                            },
                            onDragCancel = {
                                detectDragGesturesState = "onDragCancel"
                            },
                            onDrag = { change, dragAmount ->
                                detectDragGesturesState = "\nonDrag\n$dragAmount"
                            },
                        )
                    },
            )
            var detectTransformGesturesState by remember {
                mutableStateOf("")
            }
            Text(
                text = "detectTransformGestures $detectTransformGesturesState",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .background(Color.Blue)
                    .pointerInput(Unit) {
                        detectTransformGestures(
                            onGesture = { centroid, pan, zoom, rotation ->
                                detectTransformGesturesState = "\nonGesture\n$centroid\n$pan\n$zoom\n$rotation"
                            },
                        )
                    },
            )
        }
    }

    @Composable
    private fun BoxScope.TestAwaitPointerEvent() {
        val density = LocalDensity.current
        val areaWidth = 50
        fun PointerInputChange?.isDown(): Boolean {
            if (this === null) {
                return false
            }
            return !previousPressed && pressed
        }
        var parentState by remember {
            mutableStateOf("")
        }
        var parentCount by remember {
            mutableStateOf(0)
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.9f)
                .pointerInput(Unit) {
                    val currentContext = currentCoroutineContext()
                    awaitPointerEventScope {
                        while (currentContext.isActive) {
                            val eventInitial = awaitPointerEvent(PointerEventPass.Initial).changes.firstOrNull()
                            val eventInitialConsumed = eventInitial?.isConsumed
                            density.run {
                                if (eventInitial?.position?.x.elseZero in (areaWidth * 3).dp.toPx()..(areaWidth * 4).dp.toPx()) {
                                    eventInitial?.consume()
                                }
                            }
                            val eventMain = awaitPointerEvent(PointerEventPass.Main).changes.firstOrNull()
                            val eventMainConsumed = eventMain?.isConsumed
                            density.run {
                                if (eventMain?.position?.x.elseZero in (areaWidth * 4).dp.toPx()..(areaWidth * 5).dp.toPx()) {
                                    eventMain?.consume()
                                }
                            }
                            val eventFinal = awaitPointerEvent(PointerEventPass.Final).changes.firstOrNull()
                            val eventFinalConsumed = eventFinal?.isConsumed
                            density.run {
                                if (eventFinal?.position?.x.elseZero in (areaWidth * 5).dp.toPx()..(areaWidth * 6).dp.toPx()) {
                                    eventFinal?.consume()
                                }
                            }
                            if (eventInitial.isDown() && eventMain.isDown() && eventFinal.isDown()) {
                                parentCount++
                                parentState = "\n" + eventInitialConsumed + "\n" + eventMainConsumed + "\n" + eventFinalConsumed
                            }
                        }
                    }
                },
        ) {
            Text(
                text = "parent $parentCount $parentState",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .background(Color.Red),
            )
            var awaitEachGestureState by remember {
                mutableStateOf("")
            }
            var awaitEachGestureCount by remember {
                mutableStateOf(0)
            }
            Text(
                text = "Gesture $awaitEachGestureCount $awaitEachGestureState",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            val eventInitial = awaitPointerEvent(PointerEventPass.Initial).changes.firstOrNull()
                            val eventInitialConsumed = eventInitial?.isConsumed
                            density.run {
                                if (eventInitial?.position?.x.elseZero in (areaWidth * 0).dp.toPx()..(areaWidth * 1).dp.toPx()) {
                                    eventInitial?.consume()
                                }
                            }
                            val eventMain = awaitPointerEvent(PointerEventPass.Main).changes.firstOrNull()
                            val eventMainConsumed = eventMain?.isConsumed
                            density.run {
                                if (eventMain?.position?.x.elseZero in (areaWidth * 1).dp.toPx()..(areaWidth * 2).dp.toPx()) {
                                    eventMain?.consume()
                                }
                            }
                            val eventFinal = awaitPointerEvent(PointerEventPass.Final).changes.firstOrNull()
                            val eventFinalConsumed = eventFinal?.isConsumed
                            density.run {
                                if (eventFinal?.position?.x.elseZero in (areaWidth * 2).dp.toPx()..(areaWidth * 3).dp.toPx()) {
                                    eventFinal?.consume()
                                }
                            }
                            if (eventInitial.isDown() && eventMain.isDown() && eventFinal.isDown()) {
                                awaitEachGestureCount++
                                awaitEachGestureState = "\n" + eventInitialConsumed + "\n" + eventMainConsumed + "\n" + eventFinalConsumed
                            }
                        }
                    },
            )
            var awaitPointerEventScopeState by remember {
                mutableStateOf("")
            }
            var awaitPointerEventScopeCount by remember {
                mutableStateOf(0)
            }
            Text(
                text = "Scope $awaitPointerEventScopeCount $awaitPointerEventScopeState",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .background(Color.Blue)
                    .pointerInput(Unit) {
                        val currentContext = currentCoroutineContext()
                        awaitPointerEventScope {
                            while (currentContext.isActive) {
                                val eventInitial = awaitPointerEvent(PointerEventPass.Initial).changes.firstOrNull()
                                val eventInitialConsumed = eventInitial?.isConsumed
                                density.run {
                                    if (eventInitial?.position?.x.elseZero in (areaWidth * 0).dp.toPx()..(areaWidth * 1).dp.toPx()) {
                                        eventInitial?.consume()
                                    }
                                }
                                val eventMain = awaitPointerEvent(PointerEventPass.Main).changes.firstOrNull()
                                val eventMainConsumed = eventMain?.isConsumed
                                density.run {
                                    if (eventMain?.position?.x.elseZero in (areaWidth * 1).dp.toPx()..(areaWidth * 2).dp.toPx()) {
                                        eventMain?.consume()
                                    }
                                }
                                val eventFinal = awaitPointerEvent(PointerEventPass.Final).changes.firstOrNull()
                                val eventFinalConsumed = eventFinal?.isConsumed
                                density.run {
                                    if (eventFinal?.position?.x.elseZero in (areaWidth * 2).dp.toPx()..(areaWidth * 3).dp.toPx()) {
                                        eventFinal?.consume()
                                    }
                                }
                                if (eventInitial.isDown() && eventMain.isDown() && eventFinal.isDown()) {
                                    awaitPointerEventScopeCount++
                                    awaitPointerEventScopeState = "\n" + eventInitialConsumed + "\n" + eventMainConsumed + "\n" + eventFinalConsumed
                                }
                            }
                        }
                    },
            )
            Row(
                modifier = Modifier.fillMaxWidth().weight(0.5f)
            ) {
                Spacer(modifier = Modifier.fillMaxHeight().width(areaWidth.dp).background(Color.LightGray))
                Spacer(modifier = Modifier.fillMaxHeight().width(areaWidth.dp).background(Color.DarkGray))
                Spacer(modifier = Modifier.fillMaxHeight().width(areaWidth.dp).background(Color.LightGray))
                Spacer(modifier = Modifier.fillMaxHeight().width(areaWidth.dp).background(Color.DarkGray))
                Spacer(modifier = Modifier.fillMaxHeight().width(areaWidth.dp).background(Color.LightGray))
                Spacer(modifier = Modifier.fillMaxHeight().width(areaWidth.dp).background(Color.DarkGray))
            }
        }
    }
}