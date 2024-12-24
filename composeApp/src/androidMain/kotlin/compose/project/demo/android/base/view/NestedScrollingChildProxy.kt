package compose.project.demo.android.base.view

import android.view.View
import androidx.core.view.NestedScrollingChildHelper

class NestedScrollingChildProxy(view: View) : BaseNestedScrollingChild {

    override val childHelper = NestedScrollingChildHelper(view)

    fun delegateParent(scrollAxes: Int): NestedScrollingParentProxy.Delegate {
        return ParentDelegate(scrollAxes, this)
    }

    private class ParentDelegate(
        override val scrollAxes: Int,
        proxy: NestedScrollingChildProxy,
    ) : BaseNestedScrollingChild by proxy, NestedScrollingParentProxy.Delegate {

        init {
            isNestedScrollingEnabled = true
        }

        override fun onPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
            if (startNestedScroll(scrollAxes, type)) {
                dispatchNestedPreScroll(dx, dy, consumed, null, type)
            }
        }

        override fun onPostScroll(
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int,
            type: Int,
            consumed: IntArray,
        ) {
            if (startNestedScroll(scrollAxes, type)) {
                dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, null, type, consumed)
            }
        }

        override fun onPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
            return dispatchNestedPreFling(velocityY, velocityY).also {
                stopNestedScroll()
            }
        }

        override fun onPostFling(
            target: View,
            velocityX: Float,
            velocityY: Float,
            consumed: Boolean,
        ): Boolean {
            return dispatchNestedFling(velocityY, velocityY, consumed).also {
                stopNestedScroll()
            }
        }
    }
}
