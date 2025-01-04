package compose.project.demo.common.test.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.sample_res_string
import org.jetbrains.compose.resources.stringResource


object SampleSpan : TestCase<SampleSpan> {

    @Composable
    override fun BoxScope.Content() {
        val str: String = stringResource(Res.string.sample_res_string)
        val text = buildAnnotatedString {
            append(str)
            withStyle(
                style = SpanStyle(
                    color = Color.Green,
                    background = Color.Blue,
                ),
            ) {
                append(str)
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "sample_click_tag",
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                color = Color.Yellow,
                            ),
                            pressedStyle = SpanStyle(
                                color = Color.Red,
                            ),
                        ),
                        linkInteractionListener = {
                            // TODO: onClick
                        },
                    )
                ) {
                    append(str)
                }
            }
        }
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center).background(Color.Green),
            style = baseTextStyle,
        )
    }

    private val baseTextStyle = TextStyle(
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
}