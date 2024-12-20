package compose.project.demo.common.test

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import compose.project.demo.common.utils.rememberAsObserver
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

object TestCommon014AnimatedVisibility : TestCase<TestCommon014AnimatedVisibility> {

    val list = listOf<@Composable () -> Unit>(
        {
            TestAnimatedVisibility(
                "default",
                fadeIn() + expandVertically(),
                fadeOut() + shrinkVertically(),
            )
        },
        {
            TestAnimatedVisibility(
                "fade",
                fadeIn(),
                fadeOut(),
            )
        },
        {
            TestAnimatedVisibility(
                "scale",
                scaleIn(),
                scaleOut(),
            )
        },
        {
            TestAnimatedVisibility(
                "slide",
                slideIn { fullSize -> IntOffset(fullSize.width / 2, fullSize.height / 2) },
                slideOut { fullSize -> IntOffset(fullSize.width / 2, fullSize.height / 2) },
            )
        },
        {
            TestAnimatedVisibility(
                "size",
                expandIn(),
                shrinkOut(),
            )
        },
        {
            TestAnimatedVisibility(
                "size vertically",
                expandVertically(),
                shrinkVertically(),
            )
        },
        {
            TestAnimatedVisibility(
                "size horizontally",
                expandHorizontally(),
                shrinkHorizontally(),
            )
        },
        {
            TestAnimatedVisibility(
                "slide vertically",
                slideInVertically(),
                slideOutVertically(),
            )
        },
        {
            TestAnimatedVisibility(
                "slide horizontally",
                slideInHorizontally(),
                slideOutHorizontally(),
            )
        },
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
    private fun TestAnimatedVisibility(
        label: String,
        enter: EnterTransition,
        exit: ExitTransition,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var visible by remember {
                mutableStateOf(true)
            }
            AnimatedVisibility(
                visible = visible,
                enter = enter,
                exit = exit,
                label = label,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                        .background(Color.Red),
                ) {
                }
                "".rememberAsObserver { state ->
                    TAG.logD { "$label $visible $state" }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Blue)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 2000,
                            easing = LinearEasing,
                        ),
                    )
                    .height(if (visible) 100.dp else 200.dp)
                    .clickable {
                        visible = !visible
                    },
            ) {
                val animatedScale by animateFloatAsState(
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = LinearEasing,
                    ),
                    targetValue = if (visible) 1f else 2f,
                    label = "text_scale",
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer {
                            scaleX = animatedScale
                        },
                    text = label,
                    color = Color.Red,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated),
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                val animatedAlpha by animateFloatAsState(
                    targetValue = if (!visible) 1.0f else 0f,
                    label = "alpha",
                    animationSpec = tween(
                        durationMillis = 2000,
                        easing = LinearEasing,
                    ),
                )

                var transitionState by remember { mutableStateOf(false) }

                run {
                    val transition = updateTransition(transitionState)
                    val borderWidth by transition.animateDp(
                        transitionSpec = {
                            tween(
                                durationMillis = 2000,
                                easing = LinearEasing,
                            )
                        },
                    ) { state ->
                        if (state) {
                            0.dp
                        } else {
                            10.dp
                        }
                    }
                    val scaleValue by transition.animateFloat(
                        transitionSpec = {
                            tween(
                                durationMillis = 2000,
                                easing = LinearEasing,
                            )
                        },
                    ) { state ->
                        if (state) {
                            1f
                        } else {
                            0.5f
                        }
                    }
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.5f)
                            .border(borderWidth, Color.Red)
                            .clickable {
                                transitionState = !transitionState
                            }
                            .graphicsLayer {
                                alpha = animatedAlpha
                                scaleX = scaleValue
                                scaleY = scaleValue
                            },
                        contentDescription = label,
                        contentScale = ContentScale.FillBounds,
                    )
                }

                run {
                    val scaleAnimation = remember { Animatable(0.5f) }
                    val borderAnimation = remember { Animatable(10f) }
                    LaunchedEffect(transitionState) {
                        launch {
                            scaleAnimation.animateTo(
                                targetValue = if (transitionState) {
                                    1f
                                } else {
                                    0.5f
                                },
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = LinearEasing,
                                ),
                            )
                        }
                        launch {
                            borderAnimation.animateTo(
                                targetValue = if (transitionState) {
                                    0f
                                } else {
                                    10f
                                },
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = LinearEasing,
                                ),
                            )
                        }
                    }
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        modifier = Modifier.fillMaxHeight().fillMaxWidth()
                            .border(borderAnimation.value.dp, Color.Red)
                            .clickable {
                                transitionState = !transitionState
                            }
                            .graphicsLayer {
                                alpha = animatedAlpha
                                scaleX = scaleAnimation.value
                                scaleY = scaleAnimation.value
                            },
                        contentDescription = label,
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
        }
    }
}
