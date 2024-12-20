package compose.project.demo.common.test

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase

object TestCommon015AnimatedContent : TestCase<TestCommon015AnimatedContent> {

    private val list = listOf<@Composable () -> Unit>(
    ) + TestCommon014AnimatedVisibility.list

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
                modifier = Modifier.fillMaxSize(),
                targetState = state,
                transitionSpec = {
                    if ((initialState + 1) % list.size == targetState % list.size) {
                        slideInHorizontally(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearEasing,
                            ),
                            initialOffsetX = { fullWidth ->
                                fullWidth
                            },
                        ) togetherWith slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearEasing,
                            ),
                            targetOffsetX = { fullWidth ->
                                -fullWidth
                            },
                        )
                    } else {
                        slideInHorizontally(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearEasing,
                            ),
                            initialOffsetX = { fullWidth ->
                                -fullWidth
                            },
                        ) togetherWith slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearEasing,
                            ),
                            targetOffsetX = { fullWidth ->
                                fullWidth
                            },
                        )
                    }
                },
            ) { targetState ->
                list[targetState]()
            }
        }
    }
}
