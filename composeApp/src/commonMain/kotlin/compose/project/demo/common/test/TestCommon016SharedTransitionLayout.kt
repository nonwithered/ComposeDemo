package compose.project.demo.common.test

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.utils.times
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import composedemo.composeapp.generated.resources.set_up_svgrepo_com
import composedemo.composeapp.generated.resources.thumbs_up_svgrepo_com
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalSharedTransitionApi::class)
object TestCommon016SharedTransitionLayout : TestCase<TestCommon016SharedTransitionLayout> {

    private val list = listOf<@Composable BoxScope.(Modifier.() -> Modifier, Modifier.() -> Modifier) -> Unit>(
        { a, b -> OuterPage(a, b) },
        { a, b -> InnerPage(a, b) },
    )

    private fun <T> animSpec(durationMillis: Int = 2000): FiniteAnimationSpec<T> = tween(
        durationMillis = durationMillis,
        easing = LinearEasing,
    )

    @Composable
    override fun BoxScope.Content() {
        var pageState by remember {
            mutableStateOf(0)
        }
        SharedTransitionLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            AnimatedContent(
                targetState = pageState,
                modifier = Modifier.fillMaxSize(),
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
                        sizeAnimationSpec = { _, _ ->
                            animSpec()
                        }
                    )
                },
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            pageState = (pageState + 1) % list.size
                        }
                    ,
                ) {
                    val textShared = rememberSharedContentState(key = "text")
                    val imageShared = rememberSharedContentState(key = "image")
                    list[page](
                        {
                            sharedElement(
                                textShared,
                                animatedVisibilityScope = this@AnimatedContent,
                                placeHolderSize = if (page == 0) SharedTransitionScope.PlaceHolderSize.contentSize else SharedTransitionScope.PlaceHolderSize.animatedSize,
                                boundsTransform = { _, _ ->
                                    animSpec(if (page == 0) 1000 else 4000)
                                },
                            )
                        },
                        {
                            sharedElement(
                                imageShared,
                                animatedVisibilityScope = this@AnimatedContent,
                                placeHolderSize = if (page == 0) SharedTransitionScope.PlaceHolderSize.contentSize else SharedTransitionScope.PlaceHolderSize.animatedSize,
                                boundsTransform = { _, _ ->
                                    animSpec(if (page == 0) 4000 else 1000)
                                },
                            )
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun BoxScope.OuterPage(
        textModifier: Modifier.() -> Modifier,
        imageModifier: Modifier.() -> Modifier,
    ) {
        Column (
            modifier = Modifier.fillMaxSize(0.5f).background(Color.Red).align(Alignment.BottomEnd),
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(Res.drawable.thumbs_up_svgrepo_com),
                contentDescription = "",
            )
            Text(
                text = "outer" * 10,
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Yellow)
                    .align(Alignment.CenterHorizontally)
                    .textModifier(),
                overflow = TextOverflow.Ellipsis,
                color = Color.Blue,
                fontSize = 30.sp,
            )
            Image(
                modifier = Modifier.fillMaxSize()
                    .background(Color.LightGray)
                    .imageModifier(),
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentScale = ContentScale.FillBounds,
                contentDescription = "",
            )
        }
    }

    @Composable
    private fun BoxScope.InnerPage(
        textModifier: Modifier.() -> Modifier,
        imageModifier: Modifier.() -> Modifier,
    ) {

        Column (
            modifier = Modifier.fillMaxSize().background(Color.Green),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Start)
                    .background(Color.LightGray)
                    .imageModifier(),
                painter = painterResource(Res.drawable.set_up_svgrepo_com),
                contentScale = ContentScale.FillBounds,
                contentDescription = "",
            )
            Text(
                text = "inner" * 80,
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Yellow)
                    .textModifier(),
                overflow = TextOverflow.Ellipsis,
                color = Color.Magenta,
                fontSize = 30.sp,
            )
        }
    }
}
