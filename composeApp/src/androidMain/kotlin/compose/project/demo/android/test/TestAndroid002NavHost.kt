package compose.project.demo.android.test


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.project.demo.common.test.TestCommon017SharedBounds
import compose.project.demo.common.test.collect.TestCase

@OptIn(ExperimentalSharedTransitionApi::class)
object TestAndroid002NavHost : TestCase<TestAndroid002NavHost> {

    @Composable
    override fun BoxScope.Content() {
        val navController = rememberNavController()
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                var edit by remember {
                    mutableStateOf("0")
                }

                TextField(
                    value = edit,
                    onValueChange = {
                        edit = it
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textStyle = LocalTextStyle.current.merge(
                        fontSize = 40.sp,
                    )
                )
                Text(
                    text = "navigate",
                    modifier = Modifier.fillMaxWidth().clickable {
                        val index = runCatching {
                            edit.toInt()
                        }.getOrNull()
                        if (index in 0 until TestCommon017SharedBounds.list.size) {
                            navController.navigate(edit)
                        }
                    },
                    fontSize = 40.sp,
                )
            }
            SharedTransitionLayout {
                val sharedTransitionLayout = this@SharedTransitionLayout
                val list = remember(sharedTransitionLayout) {
                    val sharedTransitionScope: @Composable (@Composable SharedTransitionScope.() -> Unit) -> Unit = { block ->
                        sharedTransitionLayout.block()
                    }
                    TestCommon017SharedBounds.list.map {
                        val r: @Composable () -> Unit = { it(sharedTransitionScope) }
                        r
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = "0"
                ) {
                    repeat(list.size) {
                        val content = list[it]
                        composable(it.toString()) {
                            content()
                        }
                    }
                }
            }
        }
    }
}
