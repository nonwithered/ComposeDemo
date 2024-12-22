package compose.project.demo.common.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.project.demo.common.base.view.DiagonalLayout
import compose.project.demo.common.test.collect.TestCase

object TestCommon019CustomLayout : TestCase<TestCommon019CustomLayout> {

    @Composable
    override fun BoxScope.Content() {
        val list = remember {
            listOf(
                "TopStart" to Alignment.TopStart,
                "TopCenter" to Alignment.TopCenter,
                "TopEnd" to Alignment.TopEnd,
                "CenterEnd" to Alignment.CenterEnd,
                "BottomEnd" to Alignment.BottomEnd,
                "BottomCenter" to Alignment.BottomCenter,
                "BottomStart" to Alignment.BottomStart,
                "CenterStart" to Alignment.CenterStart,
                "Center" to Alignment.Center,
            )
        }
        var showState by remember {
            mutableStateOf(0)
        }
        val (lable, alignment) = list[showState]
        Column {
            Text(
                text = lable,
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        showState = (showState + 1) % list.size
                    },
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
            DiagonalLayout(
                modifier = Modifier.background(Color.LightGray),
                alignment = alignment,
            ) {
                Spacer(
                    modifier = Modifier.background(Color.Red)
                        .fillMaxWidth(0.5f)
                        .height(120.dp),
                )
                Spacer(
                    modifier = Modifier.background(Color.Green)
                        .width(120.dp)
                        .fillMaxHeight(0.5f),
                )
                Spacer(
                    modifier = Modifier.background(Color.Blue)
                        .fillMaxSize(),
                )
            }
        }
    }
}
