package compose.project.demo.common.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.set_up_svgrepo_com
import composedemo.composeapp.generated.resources.thumbs_up_svgrepo_com
import composedemo.composeapp.generated.resources.user_svgrepo_com
import org.jetbrains.compose.resources.painterResource

object TestCommon008Constraint : TestCase<TestCommon008Constraint> {

    @Composable
    override fun BoxScope.Content() {
        val avatarId = "avatar"
        val nicknameId = "nicknameId"
        val descId = "descId"
        val infoId = "infoId"
        val diggId = "diggId"
        val setupId = "setup"
        val constraintSet = remember {
            ConstraintSet {
                val avatar = createRefFor(avatarId)
                val nickname = createRefFor(nicknameId)
                val desc = createRefFor(descId)
                val info = createRefFor(infoId)
                val digg = createRefFor(diggId)
                val setup = createRefFor(setupId)
                constrain(avatar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                constrain(nickname) {
                    start.linkTo(avatar.end)
                    top.linkTo(avatar.top)
                }
                constrain(desc) {
                    start.linkTo(nickname.start)
                    top.linkTo(nickname.bottom)
                }
                constrain(info) {
                    start.linkTo(nickname.start)
                    bottom.linkTo(avatar.bottom)
                }
                constrain(digg) {
                    top.linkTo(avatar.top)
                    end.linkTo(parent.end)
                }

                constrain(setup) {
                    bottom.linkTo(avatar.bottom)
                    end.linkTo(digg.end)
                }
            }
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, Color.Red),
            constraintSet = constraintSet,
        ) {
            Image(
                painter = painterResource(Res.drawable.user_svgrepo_com),
                contentDescription = "avatar",
                modifier = Modifier.layoutId(avatarId)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .size(120.dp)
                    .border(2.dp, Color.Blue),
            )

            Text(
                text = "nickname",
                modifier = Modifier.layoutId(nicknameId)
                    .padding(start = 12.dp, top = 16.dp)
                    .border(2.dp, Color.Blue),
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Text(
                text = "desc",
                modifier = Modifier.layoutId(descId)
                    .padding(start = 12.dp, top = 8.dp)
                    .border(2.dp, Color.Blue),
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Text(
                text = "info",
                modifier = Modifier.layoutId(infoId)
                    .padding(start = 12.dp, bottom = 16.dp)
                    .border(2.dp, Color.Blue),
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Image(
                painter = painterResource(Res.drawable.thumbs_up_svgrepo_com),
                contentDescription = "digg",
                modifier = Modifier.layoutId(diggId)
                    .padding(end = 16.dp, top = 16.dp)
                    .size(20.dp)
                    .border(2.dp, Color.Blue),
            )

            Image(
                painter = painterResource(Res.drawable.set_up_svgrepo_com),
                contentDescription = "setup",
                modifier = Modifier.layoutId(setupId)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .size(20.dp)
                    .border(2.dp, Color.Blue),
            )
        }
    }
}
