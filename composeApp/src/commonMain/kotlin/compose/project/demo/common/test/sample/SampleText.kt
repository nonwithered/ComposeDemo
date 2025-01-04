package compose.project.demo.common.test.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

object SampleText : TestCase<SampleText> {

    @Composable
    override fun BoxScope.Content() {
        Text(
            text = "This is a string.",
            modifier = Modifier.align(Alignment.Center).background(Color.Green),
            color = Color.Red,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            letterSpacing = 4.sp,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Start,
            lineHeight = 32.sp,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            maxLines = 2,
            minLines = 1,
        )
    }
}
