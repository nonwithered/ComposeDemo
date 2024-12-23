package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import compose.project.demo.common.base.view.DiagonalLayout
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.intOffset
import compose.project.demo.common.utils.intSize
import compose.project.demo.common.utils.offset
import compose.project.demo.common.utils.rect
import compose.project.demo.common.utils.size

object TestCommon019CustomLayout : TestCase<TestCommon019CustomLayout> {

    private val list = listOf<@Composable BoxScope.() -> Unit>(
        { TestDiagonalLayout() },
        { TestLayoutModifier() },
        { TestOnPlaced() },
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
    private fun BoxScope.TestDiagonalLayout() {
        val list = remember {
            listOf(
                "TopStart" to Alignment.TopStart,
                "TopCenter" to Alignment.TopCenter,
                "TopEnd" to Alignment.TopEnd,
                "CenterEnd" to Alignment.CenterEnd,
                "BottomEnd" to Alignment.BottomEnd,
                "BottomCenter" to Alignment.BottomCenter,
                "BottomStart" to Alignment.BottomStart,
                "CenterStart" to Alignment.CenterStart,
                "Center" to Alignment.Center,
            )
        }
        var showState by remember {
            mutableStateOf(0)
        }
        val (lable, alignment) = list[showState]
        Column {
            Text(
                text = lable,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        showState = (showState + 1) % list.size
                    },
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
            DiagonalLayout(
                modifier = Modifier.background(Color.LightGray),
                alignment = alignment,
            ) {
                Spacer(
                    modifier = Modifier.background(Color.Red.copy(alpha = 0.5f))
                        .fillMaxWidth(0.5f)
                        .height(120.dp)
                        .placeFractionHorizontal(0.5f)
                        .placeFractionVertical(2f),
                )
                Spacer(
                    modifier = Modifier.background(Color.Green.copy(alpha = 0.5f))
                        .width(120.dp)
                        .fillMaxHeight(0.5f)
                        .placeFractionVertical(0.5f),
                )
                Spacer(
                    modifier = Modifier.background(Color.Blue.copy(alpha = 0.5f))
                        .fillMaxSize(),
                )
            }
        }
    }

    @Composable
    private fun BoxScope.TestLayoutModifier() {
        val list = remember {
            listOf(
                "TopStart" to Alignment.TopStart,
                "TopCenter" to Alignment.TopCenter,
                "TopEnd" to Alignment.TopEnd,
                "CenterEnd" to Alignment.CenterEnd,
                "BottomEnd" to Alignment.BottomEnd,
                "BottomCenter" to Alignment.BottomCenter,
                "BottomStart" to Alignment.BottomStart,
                "CenterStart" to Alignment.CenterStart,
                "Center" to Alignment.Center,
            )
        }
        var showState by remember {
            mutableStateOf(0)
        }
        val (lable, alignment) = list[showState]
        Column {
            Text(
                text = lable,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        showState = (showState + 1) % list.size
                    },
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
            Box(
                modifier = Modifier.fillMaxSize(0.5f).background(Color.LightGray).layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(100, 200)
                    }
                },
            ) {
                Spacer(
                    modifier = Modifier.background(Color.Red.copy(alpha = 0.5f))
                        .fillMaxWidth(0.5f)
                        .height(120.dp)
                        .align(alignment),
                )
                Spacer(
                    modifier = Modifier.background(Color.Green.copy(alpha = 0.5f))
                        .width(120.dp)
                        .fillMaxHeight(0.5f)
                        .align(alignment),
                )
            }
        }
    }

    @Composable
    private fun BoxScope.TestOnPlaced() {
        val list = remember {
            listOf(
                "TopStart" to Alignment.TopStart,
                "TopCenter" to Alignment.TopCenter,
                "TopEnd" to Alignment.TopEnd,
                "CenterEnd" to Alignment.CenterEnd,
                "BottomEnd" to Alignment.BottomEnd,
                "BottomCenter" to Alignment.BottomCenter,
                "BottomStart" to Alignment.BottomStart,
                "CenterStart" to Alignment.CenterStart,
                "Center" to Alignment.Center,
            )
        }
        var showState by remember {
            mutableStateOf(0)
        }
        val (lable, alignment) = list[showState]
        Column {
            Text(
                text = lable,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        showState = (showState + 1) % list.size
                    },
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
            Box(
                modifier = Modifier.fillMaxSize(0.5f).background(Color.LightGray),
            ) {
                var positionInParent by remember {
                    mutableStateOf(0 offset 0)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(120.dp)
                        .align(alignment)
                        .onPlaced {
                            positionInParent = it.positionInParent()
                        }
                        .offset {
                            positionInParent.round() * -1f
                        }
                        .background(Color.Red.copy(alpha = 0.5f)),
                )
                var parentBounds by remember {
                    mutableStateOf(0 intOffset 0)
                }
                Spacer(
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight(0.5f)
                        .align(alignment)
                        .onPlaced {
                            parentBounds = it.parentCoordinates?.parentCoordinates?.parentCoordinates?.size!!.run { width intOffset height }
                        }
                        .offset {
                            parentBounds
                        }
                        .background(Color.Green.copy(alpha = 0.5f)),
                )
            }
        }
    }
}
