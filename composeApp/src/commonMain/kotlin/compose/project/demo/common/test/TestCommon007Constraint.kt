package compose.project.demo.common.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.set_up_svgrepo_com
import composedemo.composeapp.generated.resources.thumbs_up_svgrepo_com
import composedemo.composeapp.generated.resources.user_svgrepo_com
import org.jetbrains.compose.resources.painterResource

object TestCommon007Constraint : TestCase<TestCommon007Constraint> {

    @Composable
    override fun BoxScope.Content() {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, Color.Red),
        ) {
            val avatar = createRef()
            val nickname = createRef()
            val desc = createRef()
            val info = createRef()
            val digg = createRef()
            val setup = createRef()

            Image(
                painter = painterResource(Res.drawable.user_svgrepo_com),
                contentDescription = "avatar",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .size(120.dp)
                    .border(2.dp, Color.Blue)
                    .constrainAs(avatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
            )

            Text(
                text = "nickname",
                modifier = Modifier.padding(start = 12.dp, top = 16.dp)
                    .border(2.dp, Color.Blue)
                    .constrainAs(nickname) {
                        start.linkTo(avatar.end)
                        top.linkTo(avatar.top)
                    },
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Text(
                text = "desc",
                modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                    .border(2.dp, Color.Blue)
                    .constrainAs(desc) {
                        start.linkTo(nickname.start)
                        top.linkTo(nickname.bottom)
                    },
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Text(
                text = "info",
                modifier = Modifier.padding(start = 12.dp, bottom = 16.dp)
                    .border(2.dp, Color.Blue)
                    .constrainAs(info) {
                        start.linkTo(nickname.start)
                        bottom.linkTo(avatar.bottom)
                    },
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Image(
                painter = painterResource(Res.drawable.thumbs_up_svgrepo_com),
                contentDescription = "digg",
                modifier = Modifier.padding(end = 16.dp, top = 16.dp)
                    .size(20.dp)
                    .border(2.dp, Color.Blue)
                    .constrainAs(digg) {
                        top.linkTo(avatar.top)
                        end.linkTo(parent.end)
                    },
            )

            Image(
                painter = painterResource(Res.drawable.set_up_svgrepo_com),
                contentDescription = "setup",
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
                    .size(20.dp)
                    .border(2.dp, Color.Blue)
                    .constrainAs(setup) {
                        bottom.linkTo(avatar.bottom)
                        end.linkTo(digg.end)
                    },
            )
        }
    }
}
