package compose.project.demo.common.test.sample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import compose.project.demo.common.test.collect.TestCase

object SampleAnimatedContent : TestCase<SampleAnimatedContent> {

    @Composable
    override fun BoxScope.Content() {
        val list = remember {
            listOf<@Composable () -> Unit>(
                { Spacer(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(Color.Red)) },
                { Spacer(modifier = Modifier.fillMaxSize().background(Color.Blue)) },
            )
        }
        var state by remember {
            mutableStateOf(0)
        }
        AnimatedContent(
            targetState = state,
            modifier = Modifier.clickable { state = (state + 1) % list.size },
            transitionSpec = {
                val initialState = initialState
                val targetState = targetState
                // check the state and decide which kind of anim to use
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearEasing,
                    ),
                    initialOffsetX = { fullWidth -> fullWidth },
                ) togetherWith slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearEasing,
                    ),
                    targetOffsetX = { fullWidth -> -fullWidth },
                ) using SizeTransform(
                    sizeAnimationSpec = { initialSize, targetSize -> tween(
                        durationMillis = 1000,
                        easing = LinearEasing,
                    ) }
                )
            }
        ) { targetState ->
            list[targetState]()
        }
    }
}
