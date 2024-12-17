package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import kotlinx.datetime.Clock

object TestCommon001Compose : TestCase<TestCommon001Compose> {

    @Composable
    override fun BoxScope.Content() {
        var editText by remember {
            mutableStateOf("")
        }
        var showText by remember {
            mutableStateOf(Clock.System.now().toString())
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(Color.Blue),
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterVertically).background(Color.White),
            ) {
                Text(
                    text = showText,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp).background(Color.Green),
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = {
                            showText = editText
                            TAG.logD { "onClick $editText" }
                        },
                        modifier = Modifier.width(120.dp)
                            .align(Alignment.CenterVertically),
                    ) {
                        Text(
                            text = "commit",
                            fontSize = 16.sp,
                        )
                    }
                    TextField(
                        value = editText,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 32.sp,
                        ),
                        onValueChange = {
                            editText = it
                        },
                    )
                }
            }
        }
    }
}
