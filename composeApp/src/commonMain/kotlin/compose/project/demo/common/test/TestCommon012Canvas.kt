package compose.project.demo.common.test

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD

object TestCommon012Canvas : TestCase<TestCommon012Canvas> {

    private val list = listOf<@Composable () -> Unit>(
        { TestDrawRect() },
        { TestDrawLine() },
        { TestDrawText() },
        { TestDrawBehind() },
        { TestDrawWithContent() },
        { TestDrawWithCacheBehind() },
        { TestDrawWithCacheWithContent() },
    )

    @Composable
    override fun BoxScope.Content() {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = rememberPagerState(pageCount = { list.size }),
        ) { page ->
            list[page]()
        }
    }

    @Composable
    private fun TestDrawRect() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            testDrawRect()
        }
    }

    private fun DrawScope.testDrawRect() {
        val paddingLeft = 50f
        val paddingTop = 100f
        val itemWidth = 400f
        val itemHeight = 200f

        var index = -1

        index++
        drawRect(
            color = Color.Green,
            topLeft = Offset(paddingLeft, paddingTop * (index + 1) + itemHeight * index),
            size = Size(itemWidth, itemHeight),
        )

        index++
        drawRect(
            brush = Brush.linearGradient(listOf(Color.Red, Color.Green)),
            topLeft = Offset(paddingLeft, paddingTop * (index + 1) + itemHeight * index),
            size = Size(itemWidth, itemHeight),
        )

        index++
        drawRect(
            brush = Brush.horizontalGradient(listOf(Color.Red, Color.Green)),
            topLeft = Offset(paddingLeft, paddingTop * (index + 1) + itemHeight * index),
            size = Size(itemWidth, itemHeight),
        )

        index++
        drawRect(
            brush = Brush.verticalGradient(listOf(Color.Red, Color.Green)),
            topLeft = Offset(paddingLeft, paddingTop * (index + 1) + itemHeight * index),
            size = Size(itemWidth, itemHeight),
        )

        index++
        drawRect(
            brush = Brush.radialGradient(listOf(Color.Red, Color.Green)),
            topLeft = Offset(paddingLeft, paddingTop * (index + 1) + itemHeight * index),
            size = Size(itemWidth, itemHeight),
        )

        index++
        drawRect(
            brush = Brush.sweepGradient(listOf(Color.Red, Color.Green)),
            topLeft = Offset(paddingLeft, paddingTop * (index + 1) + itemHeight * index),
            size = Size(itemWidth, itemHeight),
        )
    }

    @Composable
    private fun TestDrawLine() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            testDrawLine()
        }
    }

    private fun DrawScope.testDrawLine() {
        val deltaX = 200f
        val start = 100f to 100f
        val end = 150f to 800f
        val strokeWidth = 50f

        var index = -1

        index++
        drawLine(
            color = Color.Blue,
            start = Offset(start.first + deltaX * index, start.second),
            end = Offset(end.first + deltaX * index, end.second),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Butt,
        )

        index++
        drawLine(
            color = Color.Blue,
            start = Offset(start.first + deltaX * index, start.second),
            end = Offset(end.first + deltaX * index, end.second),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        index++
        drawLine(
            color = Color.Blue,
            start = Offset(start.first + deltaX * index, start.second),
            end = Offset(end.first + deltaX * index, end.second),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Square,
        )
    }

    @Composable
    private fun TestDrawText() {
        val textMeasurer = rememberTextMeasurer()
        val textLayoutResult = textMeasurer.measure(
            text = buildAnnotatedString {
                append("ABC")
                withStyle(style = SpanStyle(
                    color = Color.Blue,
                )) {

                    append("DEFG")
                }
                append(" - 12345")
            },
            style = TextStyle(
                color = Color.Green,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawText(
                textMeasurer = textMeasurer,
                text = "ABCDEFG - 12345",
                topLeft = Offset(100f, 400f),
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(100f, 600f),
            )
        }
    }

    @Composable
    private fun TestDrawBehind() {
        var update by remember {
            mutableStateOf(0)
        }
        TAG.logD { "TestDrawBehind $update" }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { update += 1 },
            ) {
                Text(
                    text = "TestDrawBehind",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontSize = 24.sp,
                )
            }
            TAG.logD { "TestDrawBehind $update outer" }
            Box(
                modifier = Modifier.fillMaxSize().drawBehind {
                    TAG.logD { "TestDrawBehind drawBehind" }
                    testDrawRect()
                }
            ) {
                TAG.logD { "TestDrawBehind $update inner" }
                TestDrawLine()
            }
        }
    }

    @Composable
    private fun TestDrawWithContent() {
        var update by remember {
            mutableStateOf(0)
        }
        TAG.logD { "TestDrawWithContent $update" }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { update += 1 },
            ) {
                Text(
                    text = "TestDrawWithContent",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontSize = 24.sp,
                )
            }
            TAG.logD { "TestDrawWithContent $update outer" }
            Box(
                modifier = Modifier.fillMaxSize().drawWithContent {
                    TAG.logD { "TestDrawWithContent drawWithContent" }
                    drawContent()
                    testDrawLine()
                }
            ) {
                TAG.logD { "TestDrawWithContent $update inner" }
                TestDrawRect()
            }
        }
    }

    @Composable
    private fun TestDrawWithCacheBehind() {
        var update by remember {
            mutableStateOf(0)
        }
        TAG.logD { "TestDrawWithCacheBehind $update" }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { update += 1 },
            ) {
                Text(
                    text = "TestDrawWithCacheBehind",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontSize = 24.sp,
                )
            }
            TAG.logD { "TestDrawWithCacheBehind $update outer" }
            Box(
                modifier = Modifier.fillMaxSize().drawWithCache {
                    TAG.logD { "TestDrawWithCacheBehind drawWithCache" }
                    onDrawBehind {
                        TAG.logD { "TestDrawWithCacheBehind drawWithCache onDrawBehind" }
                        testDrawRect()
                    }
                }
            ) {
                TAG.logD { "TestDrawWithCacheBehind $update inner" }
                TestDrawLine()
            }
        }
    }

    @Composable
    private fun TestDrawWithCacheWithContent() {
        var update by remember {
            mutableStateOf(0)
        }
        TAG.logD { "TestDrawWithCacheWithContent $update" }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { update += 1 },
            ) {
                Text(
                    text = "TestDrawWithCacheWithContent",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontSize = 24.sp,
                )
            }
            TAG.logD { "TestDrawWithCacheWithContent $update outer" }
            Box(
                modifier = Modifier.fillMaxSize().drawWithCache {
                    TAG.logD { "TestDrawWithCacheWithContent drawWithCache" }
                    onDrawWithContent {
                        TAG.logD { "TestDrawWithCacheWithContent drawWithCache onDrawWithContent" }
                        drawContent()
                        testDrawLine()
                    }
                }
            ) {
                TAG.logD { "TestDrawWithCacheWithContent $update inner" }
                TestDrawRect()
            }
        }
    }
}
