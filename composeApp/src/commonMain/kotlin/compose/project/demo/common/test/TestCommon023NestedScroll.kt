package compose.project.demo.common.test

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.dp
import compose.project.demo.common.utils.logE
import kotlinx.coroutines.delay

object TestCommon023NestedScroll : TestCase<TestCommon023NestedScroll> {

    private class NestedScrollConnectionImpl(
        private val tag: String,
        private val needTrace: Boolean,
    ) : NestedScrollConnection {

        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (needTrace) {
                TAG.logE(AssertionError()) { "onPreScroll $tag" }
            } else {
                TAG.logE { "onPreScroll $tag" }
            }
            return super.onPreScroll(available, source)
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (needTrace) {
                TAG.logE(AssertionError()) { "onPostScroll $tag" }
            } else {
                TAG.logE { "onPostScroll $tag" }
            }
            return super.onPostScroll(consumed, available, source)
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            if (needTrace) {
                TAG.logE(AssertionError()) { "onPreFling $tag" }
            } else {
                TAG.logE { "onPreFling $tag" }
            }
            return super.onPreFling(available)
        }

        override suspend fun onPostFling(
            consumed: Velocity,
            available: Velocity
        ): Velocity {
            if (needTrace) {
                TAG.logE(AssertionError()) { "onPostFling $tag" }
            } else {
                TAG.logE { "onPostFling $tag" }
            }
            return super.onPostFling(consumed, available)
        }
    }

    @Composable
    override fun BoxScope.Content() {
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(NestedScrollConnectionImpl("1", false))
                .nestedScroll(NestedScrollConnectionImpl("2", false))
            ,
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxWidth()
                    .nestedScroll(NestedScrollConnectionImpl("3", false))
                    .nestedScroll(NestedScrollConnectionImpl("4", false)),
            ) {
                items(50) {
                    Text(
                        text = "$it",
                        modifier = Modifier.padding(2.dp),
                        fontSize = 20.sp,
                        color = Color.Red,
                    )
                }
            }
        }
    }
}
