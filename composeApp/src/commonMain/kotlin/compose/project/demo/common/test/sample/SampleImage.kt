package compose.project.demo.common.test.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.compose_multiplatform
import composedemo.composeapp.generated.resources.set_up_svgrepo_com
import org.jetbrains.compose.resources.painterResource

object SampleImage : TestCase<SampleImage> {

    @Composable
    override fun BoxScope.Content() {

//        Image(
//            painter = painterResource(Res.drawable.compose_multiplatform),
//            contentDescription = "This is a desc for a11y.",
//            modifier = Modifier.matchParentSize(),
//            contentScale = ContentScale.FillBounds,
//        )

//        AsyncImage(
//            model = "https://www.jetbrains.com/_assets/www/kotlin-multiplatform/parts/sections/head/hero-shape.41226a16aa9674fbb2f397f143af121c.jpg",
//            placeholder = painterResource(Res.drawable.compose_multiplatform),
//            error = painterResource(Res.drawable.set_up_svgrepo_com),
//            contentDescription = "This is a desc for a11y.",
//            modifier = Modifier.matchParentSize(),
//            contentScale = ContentScale.Fit,
//        )
        val painter = rememberAsyncImagePainter(
            model = "https://www.jetbrains.com/_assets/www/kotlin-multiplatform/parts/sections/head/hero-shape.41226a16aa9674fbb2f397f143af121c.jpg",
            placeholder = painterResource(Res.drawable.compose_multiplatform),
            error = painterResource(Res.drawable.set_up_svgrepo_com),
        )

        Image(
            painter = painter,
            contentDescription = "This is a desc for a11y.",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Fit,
        )
    }
}
