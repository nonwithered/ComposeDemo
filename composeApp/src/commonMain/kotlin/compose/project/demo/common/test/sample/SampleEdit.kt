package compose.project.demo.common.test.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase

object SampleEdit : TestCase<SampleEdit> {

    @Composable
    override fun BoxScope.Content() {
        var editable by remember {
            mutableStateOf("This is a editable string.")
        }
        val textStyle = TextStyle(
            color = Color.Red,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            letterSpacing = 4.sp,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Start,
            lineHeight = 32.sp,
        )
        OutlinedTextField(
            value = editable,
            onValueChange = { editable = it },
            modifier = Modifier.align(Alignment.Center).background(Color.Green),
            textStyle = textStyle,
            label = {
                Text(
                    color = Color.Blue,
                    text = "This is a label.",
                    style = textStyle,
                )
            },
            placeholder = {
                Text(
                    color = Color.Magenta,
                    text = "This is a hint.",
                    style = textStyle,
                )
            },
            maxLines = 2,
            minLines = 1,
        )
    }
}
