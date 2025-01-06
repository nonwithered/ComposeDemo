package compose.project.demo.common.test.sample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.sample_res_anim_string
import org.jetbrains.compose.resources.stringResource

object SampleSharedBounds : TestCase<SampleSharedBounds> {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    override fun BoxScope.Content() {
        val list = remember {
            listOf<@Composable (SampleContent) -> Unit>(
                { content -> Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(Color.Green), content = content) },
                { content -> Box(modifier = Modifier.fillMaxSize().background(Color.Yellow), content = content) },
            )
        }
        var state by remember {
            mutableStateOf(0)
        }
        SharedTransitionLayout {
            val animSharedState = rememberSharedContentState(key = "text")
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
                        sizeAnimationSpec = { initialSize, targetSize ->
                            tween(
                                durationMillis = 1000,
                                easing = LinearEasing,
                            )
                        }
                    )
                }
            ) { targetState ->
                list[targetState] {
                    Text(
                        text = stringResource(Res.string.sample_res_anim_string),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5,
                        style = TextStyle(
                            color = if (targetState == 0) Color.Red else Color.Magenta,
                            fontSize = if (targetState == 0) 24.sp else 36.sp,
                            lineHeight = if (targetState == 0) 36.sp else 48.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth(if (targetState == 0) 0.5f else 1f)
                            .align(Alignment.BottomEnd)
                            .sharedBounds(
                                sharedContentState = animSharedState,
                                animatedVisibilityScope = this@AnimatedContent,
                                boundsTransform = { initialBounds, targetBounds ->
                                    tween(
                                        durationMillis = 1000,
                                        easing = LinearEasing,
                                    )
                                },
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 1000,
                                        easing = LinearEasing,
                                    ),
                                ),
                                exit = fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 1000,
                                        easing = LinearEasing,
                                    ),
                                ),
                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds, // SharedTransitionScope.ResizeMode.ScaleToBounds()
                            )
                            .skipToLookaheadSize(),
                    )
                }
            }
        }
    }
}