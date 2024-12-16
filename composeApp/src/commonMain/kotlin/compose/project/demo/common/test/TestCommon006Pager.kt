package compose.project.demo.common.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

object TestCommon006Pager : TestCase<TestCommon006Pager> {

    @Composable
    override fun BoxScope.Content() {
        val pagerState = rememberPagerState(
            initialPage = 2,
            initialPageOffsetFraction = 0.4f,
            pageCount = { 10 },
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .background(Color.Gray),
                contentPadding = PaddingValues(80.dp),
                pageSpacing = 40.dp,
                verticalAlignment = Alignment.Bottom,
                reverseLayout = true,
                snapPosition = SnapPosition.Start,
            ) { page ->
                Box(
                    modifier = Modifier.background(Color.Yellow),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "picture",
                        modifier = Modifier,
                        contentScale = ContentScale.Fit,
                    )
                    Text(
                        text = page.toString(),
                        modifier = Modifier.align(Alignment.TopEnd),
                    )
                }
            }
            Text(
                text = pagerState.currentPage.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Text(
                text = pagerState.currentPage.toString(),
                modifier = Modifier,
            )
        }
    }
}
