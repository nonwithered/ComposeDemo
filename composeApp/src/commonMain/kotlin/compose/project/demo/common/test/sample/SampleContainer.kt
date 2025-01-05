package compose.project.demo.common.test.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import compose.project.demo.common.test.collect.TestCase
import composedemo.composeapp.generated.resources.Res
import composedemo.composeapp.generated.resources.avatar
import composedemo.composeapp.generated.resources.digg
import composedemo.composeapp.generated.resources.setup
import org.jetbrains.compose.resources.painterResource

object SampleContainer : TestCase<SampleContainer> {

    @Composable
    override fun BoxScope.Content() {
//        val itemList = listOf(
//            ItemData(1, "a"),
//            ItemData(2, "b"),
//            ItemData(3, "c"),
//        )
//        SamplePager(
//            modifier = Modifier.matchParentSize(),
//            itemList = itemList,
//        )
        SampleConstraintSet()
    }

    @Composable
    private fun SamplePager(
        modifier: Modifier,
        itemList: List<ItemData>,
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { itemList.size },
        )
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
            key = { index -> itemList[index].id },
        ) { page ->
            val itemData = itemList[page]
            Text(
                text = itemData.name,
                modifier = Modifier,
            )
        }
        // TODO: Design an indicator for the pager.
        println("currentState: ${pagerState.currentPage} ${pagerState.currentPageOffsetFraction}")
    }

    /**
     * This can be any type.
     */
    data class ItemData(
        val id: Int,
        val name: String,
    )

    private val sampleTextStyle = TextStyle(
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

    @Composable
    private fun SampleList(
        modifier: Modifier,
        itemList: List<ItemData>,
    ) {
        val state = rememberLazyListState()
        LazyColumn(
            state = state,
            modifier = modifier,
        ) {
            itemsIndexed(
                items = itemList,
                key = { index, item -> item.id },
                contentType = { index, item -> 0 /* or anyother obj to mark the type to reuse them more efficiently */ },
            ) { index, item ->
                Text(
                    text = item.name,
                    modifier = Modifier,
                )
            }
        }
    }

    @Composable
    private fun SampleConstraint() {
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
                painter = painterResource(Res.drawable.avatar),
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
            )

            Image(
                painter = painterResource(Res.drawable.digg),
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
                painter = painterResource(Res.drawable.setup),
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

    @Composable
    private fun SampleConstraintSet() {
        val avatarId = "avatarId"
        val nicknameId = "nicknameId"
        val descId = "descId"
        val infoId = "infoId"
        val diggId = "diggId"
        val setupId = "setupId"
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
                painter = painterResource(Res.drawable.avatar),
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
            )
            Image(
                painter = painterResource(Res.drawable.digg),
                contentDescription = "digg",
                modifier = Modifier.layoutId(diggId)
                    .padding(end = 16.dp, top = 16.dp)
                    .size(20.dp)
                    .border(2.dp, Color.Blue),
            )
            Image(
                painter = painterResource(Res.drawable.setup),
                contentDescription = "setup",
                modifier = Modifier.layoutId(setupId)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .size(20.dp)
                    .border(2.dp, Color.Blue),
            )
        }
    }
}
