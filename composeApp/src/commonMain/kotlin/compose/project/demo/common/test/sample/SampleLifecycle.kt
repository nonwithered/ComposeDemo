package compose.project.demo.common.test.sample

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logE
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.sample_res_string
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

object SampleLifecycle : TestCase<SampleLifecycle> {

//    val LocalString = compositionLocalOf<String> { error("stub") }
    val LocalString = staticCompositionLocalOf<String> { error("stub") }

    @Composable
    override fun BoxScope.Content() {
        TAG.logE { "CCCCC" }
        var style by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(Unit) {
            delay(2000L)
            style = true
        }
        CompositionLocalProvider(
            LocalString provides (if (style) "Q" else "W"),
        ) {
            A()
        }
    }

    @Composable
    private fun BoxScope.A() {
        TAG.logE { "AAAAA" }
        B()
    }

    @Composable
    private fun BoxScope.B() {
        TAG.logE { "BBBBB" }
        Text(
            text = LocalString.current,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
