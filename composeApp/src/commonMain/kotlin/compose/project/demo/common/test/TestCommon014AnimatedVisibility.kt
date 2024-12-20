package compose.project.demo.common.test

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import compose.project.demo.common.utils.rememberAsObserver
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

object TestCommon014AnimatedVisibility : TestCase<TestCommon014AnimatedVisibility> {

    private val list = listOf<@Composable () -> Unit>(
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
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Blue)
                    .clickable {
                        visible = !visible
                    },
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = label,
                    color = Color.Red,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                )
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
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                modifier = Modifier.fillMaxSize(),
                contentDescription = label,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
