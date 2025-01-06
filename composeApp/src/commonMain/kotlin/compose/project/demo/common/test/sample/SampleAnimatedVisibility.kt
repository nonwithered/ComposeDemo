package compose.project.demo.common.test.sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase

object SampleAnimatedVisibility : TestCase<SampleAnimatedVisibility> {

    @Composable
    override fun BoxScope.Content() {
        var visible by remember {
            mutableStateOf(true)
        }
        Column(
            modifier = Modifier.clickable {
                visible = !visible
            }
        ) {
            AnimatedVisibility(
                modifier = Modifier,
                visible = visible,
                enter = fadeIn() + expandIn(),
                exit = shrinkOut() + fadeOut(),
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth().background(Color.Red))
            }
            Spacer(modifier = Modifier.fillMaxSize().background(Color.Blue))
        }
    }
}
