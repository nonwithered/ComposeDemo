package compose.project.demo.common.test

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.minus
import compose.project.demo.common.utils.offset
import compose.project.demo.common.utils.plus
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import composedemo.composeapp.generated.resources.setup
import composedemo.composeapp.generated.resources.avatar
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalSharedTransitionApi::class)
object TestCommon018SharedCustom : TestCase<TestCommon018SharedCustom> {

    val list = listOf<@Composable () -> Unit>(
        { TestContent({ it }) },
        { TestContent({ it.renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f) }) },
    )

    private fun animSpec(initialBounds: Rect, targetBounds: Rect): FiniteAnimationSpec<Rect> = keyframes {
        delayMillis = 2000
        durationMillis = 10000
        initialBounds at 0 using LinearOutSlowInEasing
        targetBounds - (200f offset 0f) atFraction 0.25f using FastOutLinearInEasing
        initialBounds + (200f offset 0f) at (durationMillis * 0.75f).toInt() using FastOutSlowInEasing
        targetBounds atFraction 1f
    }

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
    private fun TestContent(
        middleModifier: SharedTransitionScope.(Modifier) -> Modifier,
    ) {
        var select by remember {
            mutableStateOf(false)
        }
        val key = remember { Any() }
        SharedTransitionLayout(
            modifier = Modifier.fillMaxSize()
                .clickable {
                    select = !select
                },
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(Color.Red, BlendMode.SrcIn),
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                        .sharedElementWithCallerManagedVisibility(
                            sharedContentState = rememberSharedContentState(key),
                            boundsTransform = { initialBounds, targetBounds ->
                                animSpec(
                                    initialBounds = initialBounds,
                                    targetBounds = targetBounds,
                                )
                            },
                            visible = select,
                        ),
                )
                Image(
                    painter = painterResource(Res.drawable.avatar),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                        .height(200.dp)
                        .let {
                            middleModifier(it)
                        },
                )
                Image(
                    painter = painterResource(Res.drawable.setup),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(Color.Red, BlendMode.SrcIn),
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
                        .sharedElementWithCallerManagedVisibility(
                            sharedContentState = rememberSharedContentState(key),
                            boundsTransform = { initialBounds, targetBounds ->
                                animSpec(
                                    initialBounds = initialBounds,
                                    targetBounds = targetBounds,
                                )
                            },
                            visible = !select,
                        ),
                )
            }
        }
    }
}
