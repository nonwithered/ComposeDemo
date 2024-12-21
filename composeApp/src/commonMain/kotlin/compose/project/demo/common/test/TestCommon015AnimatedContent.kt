package compose.project.demo.common.test

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object TestCommon015AnimatedContent : TestCase<TestCommon015AnimatedContent> {

    private val list = listOf<@Composable () -> Unit>(
    ) + TestCommon014AnimatedVisibility.list

    private fun <T> animSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = 2000,
        easing = LinearEasing,
    )

    @Composable
    override fun BoxScope.Content() {
        var state by remember {
            mutableStateOf(0)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "last",
                    modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            state = ((state - 1) % list.size + list.size) % list.size
                        },
                    color = Color.Blue,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "${state % list.size}",
                    modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically),
                    color = Color.Blue,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "next",
                    modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            state = ((state + 1) % list.size + list.size) % list.size
                        },
                    color = Color.Blue,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
            }

            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    if ((initialState + 1) % list.size == targetState % list.size) {
                        slideInHorizontally(
                            animationSpec = animSpec(),
                            initialOffsetX = { fullWidth ->
                                fullWidth
                            },
                        ) togetherWith slideOutHorizontally(
                            animationSpec = animSpec(),
                            targetOffsetX = { fullWidth ->
                                -fullWidth
                            },
                        )
                    } else {
                        slideInHorizontally(
                            animationSpec = animSpec(),
                            initialOffsetX = { fullWidth ->
                                -fullWidth
                            },
                        ) togetherWith slideOutHorizontally(
                            animationSpec = animSpec(),
                            targetOffsetX = { fullWidth ->
                                fullWidth
                            },
                        )
                    } using SizeTransform(
                        sizeAnimationSpec = { initialSize, targetSize ->
                            animSpec()
                        }
                    )
                },
            ) { targetState ->
                val alphaAnimation = remember { Animatable(0f) }
                val borderAnimation = remember { Animatable(0f) }
                LaunchedEffect(targetState) {
                    delay(3000)
                    coroutineScope {
                        launch {
                            alphaAnimation.animateTo(
                                targetValue = 1f,
                                animationSpec = animSpec(),
                            )
                        }
                        launch {
                            borderAnimation.animateTo(
                                targetValue = 1f,
                                animationSpec = animSpec(),
                            )
                        }
                    }
                    borderAnimation.animateTo(
                        targetValue = 0f,
                        animationSpec = animSpec(),
                    )
                    alphaAnimation.animateTo(
                        targetValue = 0f,
                        animationSpec = animSpec(),
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight(if (targetState % 2 == 0) 0.5f else 1f)
                        .border(5.dp + 40.dp * borderAnimation.value, if (targetState % 2 == 0) Color.Green else Color.Magenta)
                        .alpha(0.5f + 0.5f * alphaAnimation.value),
                ) {
                    list[targetState]()
                }
            }
        }
    }
}
