package compose.project.demo.common.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import composedemo.composeapp.generated.resources.set_up_svgrepo_com
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

object TestCommon005Image : TestCase<TestCommon005Image> {

    @Composable
    override fun BoxScope.Content() {
        Column {
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "qwer",
                modifier = Modifier.size(100.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds,
                alpha = DefaultAlpha,
                colorFilter = null,
            )
            Image(
                imageVector = vectorResource(Res.drawable.compose_multiplatform),
                contentDescription = "asdf",
                modifier = Modifier.size(100.dp).background(Color.Yellow),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f,
                colorFilter = null,
            )
            Image(
                bitmap = imageResource(Res.drawable.set_up_svgrepo_com),
                contentDescription = "zxcv",
                modifier = Modifier.size(100.dp, 50.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds,
                alpha = 1f,
                colorFilter = ColorFilter.tint(Color.Blue, BlendMode.SrcIn),
            )
        }
    }
}
