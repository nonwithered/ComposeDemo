package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.case_002_test_text_res
import org.jetbrains.compose.resources.stringResource

object TestCommon002Text : TestCase<TestCommon002Text> {

    @Composable
    override fun BoxScope.Content() {
        Text(
            text = stringResource(Res.string.case_002_test_text_res),
            modifier = Modifier.background(Color.Green),
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
