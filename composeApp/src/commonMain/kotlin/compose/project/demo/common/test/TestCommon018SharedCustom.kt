package compose.project.demo.common.test

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import composedemo.composeapp.generated.resources.set_up_svgrepo_com
import composedemo.composeapp.generated.resources.user_svgrepo_com
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalSharedTransitionApi::class)
object TestCommon018SharedCustom : TestCase<TestCommon018SharedCustom> {

    private fun <T> animSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = 2000,
        easing = LinearEasing,
    )

    @Composable
    override fun BoxScope.Content() {
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
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                        .sharedElementWithCallerManagedVisibility(
                            sharedContentState = rememberSharedContentState(key),
                            boundsTransform = { _, _ ->
                                animSpec()
                            },
                            visible = select,
                        ),
                )
                Image(
                    painter = painterResource(Res.drawable.user_svgrepo_com),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                        .height(200.dp),
                )
                Image(
                    painter = painterResource(Res.drawable.set_up_svgrepo_com),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
                        .sharedElementWithCallerManagedVisibility(
                            sharedContentState = rememberSharedContentState(key),
                            boundsTransform = { _, _ ->
                                animSpec()
                            },
                            visible = !select,
                        ),
                )
            }
        }
    }
}
