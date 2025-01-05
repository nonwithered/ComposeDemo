package compose.project.demo.common.test.sample

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase

object SampleContainer : TestCase<SampleContainer> {

    @Composable
    override fun BoxScope.Content() {
        val itemList = listOf(
            ItemData(1, "a"),
            ItemData(2, "b"),
            ItemData(3, "c"),
        )
        SamplePager(
            modifier = Modifier.matchParentSize(),
            itemList = itemList,
        )
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
}
