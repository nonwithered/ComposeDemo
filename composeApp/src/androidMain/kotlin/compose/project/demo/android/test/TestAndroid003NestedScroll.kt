package compose.project.demo.android.test

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import compose.project.demo.R
import compose.project.demo.android.base.page.BaseFragment
import compose.project.demo.android.select.SelectItem
import compose.project.demo.common.utils.MutableStateFlowVolatile
import compose.project.demo.common.utils.logE
import kotlin.math.roundToInt

object TestAndroid003NestedScroll : SelectItem {

    override val fragmentClass = Page::class.java

    private const val COUNT = 20
    private const val INDEX = 10
    private const val HEIGHT = 300

    class Page : BaseFragment() {

        private val scrollEvent = MutableStateFlowVolatile(0f)

        override val layoutId: Int
            get() = R.layout.empty_frame

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val frameLayout: FrameLayout = view as FrameLayout
            val scroll: NestedScrollView = LayoutInflater.from(frameLayout.context).inflate(R.layout.page_nested_scroll, frameLayout, false) as NestedScrollView
            frameLayout.addView(scroll)
            initNestedScrollView(scroll)
            val compose: ComposeView = LayoutInflater.from(scroll.context).inflate(R.layout.test_003_item_compose, scroll, false).also {
                addViewIntoScroll(scroll, it, INDEX)
            }.findViewById(R.id.compose_view)
            compose.updateLayoutParams {
                height = (HEIGHT * resources.displayMetrics.density).roundToInt()
            }
            compose.setContent {
                val state = rememberLazyListState()
                LaunchedEffect(state) {
                    scrollEvent.collect { pixels ->
                        state.scroll {
                            scrollBy(-pixels)
                        }
                    }
                }
                LazyColumn(
                    state = state,
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.LightGray)
                        .nestedScroll(rememberNestedScrollInteropConnection()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(COUNT + 1) { i ->
                        if (i != 5) {
                            Text(
                                text = "$i",
                                modifier = Modifier.padding(10.dp),
                                fontSize = 20.sp,
                                color = Color.Red,
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .height(HEIGHT.dp)
                                    .background(Color.Yellow)
                                    .nestedScroll(connection = object : NestedScrollConnection {

                                        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                                            return Offset.Zero
                                        }

                                        override fun onPostScroll(
                                            consumed: Offset,
                                            available: Offset,
                                            source: NestedScrollSource,
                                        ): Offset {
                                            scrollEvent.tryEmit(available.y)
                                            return available
                                        }
                                    }),
                            ) {
                                AndroidView(
                                    factory = { context ->
                                        val scrollItem: NestedScrollView = LayoutInflater.from(context).inflate(R.layout.page_nested_scroll, null) as NestedScrollView
                                        initNestedScrollView(scrollItem)
                                        ViewCompat.setNestedScrollingEnabled(scrollItem, true)
                                        scrollItem
                                    },
                                    modifier = Modifier.matchParentSize(),
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun initNestedScrollView(scroll: NestedScrollView) {
            repeat(COUNT) { i ->
                val text: TextView = LayoutInflater.from(scroll.context).inflate(R.layout.test_003_item_text, scroll, false) as TextView
                text.text = "$i"
                addViewIntoScroll(scroll, text)
            }
        }

        private fun addViewIntoScroll(scroll: NestedScrollView, view: View, index: Int = -1) {
            scroll.children.mapNotNull { it as? ViewGroup }.first().addView(view, index)
        }
    }
}

class TestAndroid003NestedScrollFrame(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                requestDisallowInterceptTouchEvent(true)
                "TestAndroid003NestedScroll".logE { "requestDisallowInterceptTouchEvent true" }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                requestDisallowInterceptTouchEvent(false)
                "TestAndroid003NestedScroll".logE { "requestDisallowInterceptTouchEvent false" }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
