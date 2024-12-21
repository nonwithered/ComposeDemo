package compose.project.demo.common.test

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.RemeasureToBounds
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.ScaleToBounds
import androidx.compose.animation.SharedTransitionScope.SharedContentState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase

@OptIn(ExperimentalSharedTransitionApi::class)
object TestCommon017SharedBounds : TestCase<TestCommon017SharedBounds> {

    val list = listOf<@Composable () -> Unit>(
        { TestResize("RemeasureToBounds skipToLookaheadSize", RemeasureToBounds, { it.skipToLookaheadSize() }) },
        { TestResize("ScaleToBounds skipToLookaheadSize", ScaleToBounds(), { it.skipToLookaheadSize() }) },
        { TestResize("RemeasureToBounds", RemeasureToBounds, { it }) },
        { TestResize("ScaleToBounds", ScaleToBounds(), { it }) },
    )

    private fun <T> animSpec(durationMillis: Int = 2000): FiniteAnimationSpec<T> = tween(
        durationMillis = durationMillis,
        easing = LinearEasing,
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
    private fun TestResize(
        label: String,
        resizeMode: ResizeMode,
        textSkipToLookaheadSize: SharedTransitionScope.(Modifier) -> Modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = label,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                fontSize = 32.sp,
                color = Color.Red,
                textAlign = TextAlign.Center,
            )
            TestCommon016SharedElement.TestSharedContent { animatedVisibilityScope, page, textShared, imageShared ->
                SharedModifier(animatedVisibilityScope, page, textShared, imageShared, resizeMode, textSkipToLookaheadSize)
            }
        }
    }

    @Composable
    private fun SharedTransitionScope.SharedModifier(
        animatedVisibilityScope: AnimatedVisibilityScope,
        page: Int,
        textShared: SharedContentState,
        imageShared: SharedContentState,
        resizeMode: ResizeMode,
        textSkipToLookaheadSize: SharedTransitionScope.(Modifier) -> Modifier,
    ): Pair<Modifier.() -> Modifier, Modifier.() -> Modifier> {
        return { modifier: Modifier ->
            modifier.sharedBounds(
                sharedContentState = textShared,
                animatedVisibilityScope = animatedVisibilityScope,
                placeHolderSize = if (page == 0) SharedTransitionScope.PlaceHolderSize.contentSize else SharedTransitionScope.PlaceHolderSize.animatedSize,
                boundsTransform = { _, _ ->
                    animSpec(if (page == 0) 1000 else 4000)
                },
                resizeMode = resizeMode,
                enter = fadeIn(
                    animationSpec = animSpec(if (page == 0) 1000 else 4000),
                ),
                exit = fadeOut(
                    animationSpec = animSpec(if (page == 0) 1000 else 4000),
                ),
            ).let {
                textSkipToLookaheadSize(it)
            }
        } to
        { modifier: Modifier ->
            modifier.sharedBounds(
                sharedContentState = imageShared,
                animatedVisibilityScope = animatedVisibilityScope,
                placeHolderSize = if (page == 0) SharedTransitionScope.PlaceHolderSize.contentSize else SharedTransitionScope.PlaceHolderSize.animatedSize,
                boundsTransform = { _, _ ->
                    animSpec(if (page == 0) 4000 else 1000)
                },
                resizeMode = resizeMode,
                enter = fadeIn(
                    animationSpec = animSpec(if (page == 0) 4000 else 1000),
                ),
                exit = fadeOut(
                    animationSpec = animSpec(if (page == 0) 4000 else 1000),
                ),
            )
        }
    }
}
