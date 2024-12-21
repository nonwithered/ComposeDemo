package compose.project.demo.common.test

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.SharedContentState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.project.demo.common.test.collect.TestCase

@OptIn(ExperimentalSharedTransitionApi::class)
object TestCommon017SharedBounds : TestCase<TestCommon017SharedBounds> {

    private fun <T> animSpec(durationMillis: Int = 2000): FiniteAnimationSpec<T> = tween(
        durationMillis = durationMillis,
        easing = LinearEasing,
    )

    @Composable
    override fun BoxScope.Content() {
        TestCommon016SharedElement.TestSharedContent { animatedVisibilityScope, page, textShared, imageShared ->
            SharedModifier(animatedVisibilityScope, page, textShared, imageShared)
        }
    }

    @Composable
    private fun SharedTransitionScope.SharedModifier(
        animatedVisibilityScope: AnimatedVisibilityScope,
        page: Int,
        textShared: SharedContentState,
        imageShared: SharedContentState,
    ): Pair<Modifier.() -> Modifier, Modifier.() -> Modifier> {
        return { modifier: Modifier ->
            modifier.sharedBounds(
                sharedContentState = textShared,
                animatedVisibilityScope = animatedVisibilityScope,
                placeHolderSize = if (page == 0) SharedTransitionScope.PlaceHolderSize.contentSize else SharedTransitionScope.PlaceHolderSize.animatedSize,
                boundsTransform = { _, _ ->
                    animSpec(if (page == 0) 1000 else 4000)
                },
                enter = fadeIn(
                    animationSpec = animSpec(if (page == 0) 1000 else 4000),
                ),
                exit = fadeOut(
                    animationSpec = animSpec(if (page == 0) 1000 else 4000),
                ),
            )
        } to
        { modifier: Modifier ->
            modifier.sharedBounds(
                sharedContentState = imageShared,
                animatedVisibilityScope = animatedVisibilityScope,
                placeHolderSize = if (page == 0) SharedTransitionScope.PlaceHolderSize.contentSize else SharedTransitionScope.PlaceHolderSize.animatedSize,
                boundsTransform = { _, _ ->
                    animSpec(if (page == 0) 4000 else 1000)
                },
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
