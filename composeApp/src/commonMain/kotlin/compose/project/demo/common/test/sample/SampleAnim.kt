package compose.project.demo.common.test.sample

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import compose.project.demo.common.test.collect.TestCase
import androidx.compose.ui.unit.DpSize

object SampleAnim : TestCase<SampleAnim> {

    @Composable
    override fun BoxScope.Content() {
    }

    class SampleAnimState {
        // define some members or not
    }

    private val DpSizeToVector: TwoWayConverter<DpSize, AnimationVector2D> =
        TwoWayConverter(
            convertToVector = { AnimationVector2D(it.width.value, it.height.value) },
            convertFromVector = { DpSize(it.v1.dp, it.v2.dp) }
        )

    private val DpSize.Companion.VectorConverter: TwoWayConverter<DpSize, AnimationVector2D>
        get() = DpSizeToVector

    @Composable
    private fun SampleTransition(stateParams: SampleAnimState) {
        val animState by rememberUpdatedState(stateParams)
        val transition = updateTransition(animState) // change the state and then the anim will start
        val animatedSize by transition.animateValue(
            typeConverter = DpSize.VectorConverter,
            transitionSpec = {
                val initialState = initialState
                val targetState = targetState
                spring() // check state and design the spec
            },
        ) { state: SampleAnimState ->
            DpSize(0.dp, 0.dp) // calculate the value by state
        }
        Spacer(modifier = Modifier.size(animatedSize))
    }

    @Composable
    private fun SampleAnimatable(targetSize: DpSize) {
        val animatable = remember {
            Animatable(
                initialValue = targetSize,
                typeConverter = DpSize.VectorConverter,
            )
        }
        LaunchedEffect(animatable, targetSize) {
            animatable.animateTo(
                targetValue = targetSize,
                animationSpec = infiniteRepeatable(animation = tween())
            )
        }
        val animatedSize by animatable::value
        Spacer(modifier = Modifier.size(animatedSize))
    }
}
