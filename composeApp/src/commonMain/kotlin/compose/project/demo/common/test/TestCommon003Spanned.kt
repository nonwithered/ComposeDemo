package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD

object TestCommon003Spanned : TestCase<TestCommon003Spanned> {

    @Composable
    override fun BoxScope.Content() {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = buildAnnotatedString {
                    withLink(link = LinkAnnotation.Clickable(
                        tag = "click_tag",
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                color = Color.Blue,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                textDecoration = TextDecoration.None,
                            ),
                            pressedStyle = SpanStyle(
                                color = Color.Gray,
                            ),
                        ),
                        linkInteractionListener = { TAG.logD { "onClick $it" } },
                    )) {
                        append("000000000000")
                    }
                    append("123")
                    withStyle(style = SpanStyle(
                        color = Color.Green,
                        background = Color.Blue,
                    )) {
                        append("321")
                    }
                    append("456")
                    withStyle(style = SpanStyle(
                        color = Color.Green,
                        background = Color.Blue,
                        shadow = Shadow(
                            color = Color.Red,
                            offset = Offset(6f, 12f),
                        )
                    )) {
                        append("654")
                    }
                    append("789")
                    withStyle(style = SpanStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Red, Color.Blue),
                        )
                    )) {
                        append("987")
                    }
                },
                modifier = Modifier.background(Color.Green).basicMarquee(velocity = 100.dp),
                color = Color.Red,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                letterSpacing = 16.sp,
                textDecoration = TextDecoration.Underline + TextDecoration.LineThrough,
                textAlign = TextAlign.End,
                lineHeight = 64.sp,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = 2,
                minLines = 1,
                onTextLayout = { TAG.logD { "onTextLayout $it" } },
                style = TextStyle.Default,
            )
        }
    }
}
