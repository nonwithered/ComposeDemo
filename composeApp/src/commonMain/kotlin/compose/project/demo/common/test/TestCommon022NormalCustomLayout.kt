package compose.project.demo.common.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onPlaced
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logE

object TestCommon022NormalCustomLayout : TestCase<TestCommon022NormalCustomLayout> {

    @Composable
    override fun BoxScope.Content() {
        Box(
            modifier = Modifier.fillMaxSize()
                .layout { measurable, constraints ->
                    TAG.logE(AssertionError()) { "LayoutElement measurable" }
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        TAG.logE(AssertionError()) { "LayoutElement placeable" }
                        placeable.placeRelative(0, 0)
                    }
                }.onPlaced {
                    TAG.logE(AssertionError()) { "OnPlacedElement onPlaced" }
                }.drawBehind {
                    TAG.logE(AssertionError()) { "DrawBehindElement drawBehind" }
                }.drawWithContent {
                    TAG.logE(AssertionError()) { "DrawWithContentElement drawWithContent" }
                },
        ) {

        }
        TestCommon019CustomLayout.run {
            TestDiagonalLayout()
        }
    }
}
