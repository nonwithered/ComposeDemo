package compose.project.demo.common.test.sample

import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import compose.project.demo.common.test.collect.TestCase

object SampleAnim : TestCase<SampleAnim> {

    @Composable
    override fun BoxScope.Content() {
    }

    class SampleAnimState {
        // define some members or not
    }

    @Composable
    private fun SampleTransition() {
        var animState by remember {
            mutableStateOf(SampleAnimState())
        }
        val transition = updateTransition(animState) // change the state and then the anim will start
        val animatedOffset by transition.animateIntOffset(
            transitionSpec = {
                val initialState = initialState
                val targetState = targetState
                spring() // check state and design the spec
            },
        ) { state: SampleAnimState ->
            IntOffset(0, 0) // calculate the value by state
        }
        Spacer(modifier = Modifier.offset { animatedOffset })
    }
}
