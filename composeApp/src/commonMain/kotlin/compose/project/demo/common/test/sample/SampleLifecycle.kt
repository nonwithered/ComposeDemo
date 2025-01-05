package compose.project.demo.common.test.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object SampleLifecycle : TestCase<SampleLifecycle> {

    @Composable
    override fun BoxScope.Content() {
    }

    @Composable
    private fun SampleLaunchedEffect(a: String, b: Long, c: Double, d: Any) {
        LaunchedEffect(a, b, c, d) {
            // This is a suspend block.
            coroutineScope {
                // Do something.
            }
            launch {
                // Do something.
            }
        }
    }
    private class RememberObserverMap<K, V>: MutableMap<K, V> by hashMapOf(), RememberObserver {
        override fun onRemembered() { /* TODO */ }
        override fun onForgotten() { /* TODO */ }
        override fun onAbandoned() { /* TODO */ }
    }
}
