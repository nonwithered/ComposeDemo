package compose.project.demo.common.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

object TestCommon004Edit : TestCase<TestCommon004Edit> {

    @Composable
    override fun BoxScope.Content() {
        var text: String by remember {
            mutableStateOf("qwer")
        }
        TextField(
            value = text,
            onValueChange = {
                text = it
                TAG.logD { "onValueChange $it" }
            },
            label = {
                Row {
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = null,
                        Modifier.size(12.dp),
                    )
                    Text(
                        text = "asdf",
                    )
                }
            },
            placeholder = {
                Row {
                    Text(
                        text = "hint",
                    )
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = null,
                        Modifier.size(12.dp),
                    )
                }
            }
        )
    }
}
