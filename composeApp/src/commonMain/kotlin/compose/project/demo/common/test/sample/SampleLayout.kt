package compose.project.demo.common.test.sample

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import compose.project.demo.common.utils.measure

@Composable
fun SampleLayout(
    modifier: Modifier = Modifier,
    content: @Composable SampleLayoutScope.() -> Unit,
) {
    Layout(
        content = { SampleLayoutScopeInstance.content() },
        modifier = modifier,
        measurePolicy = SampleLayoutMeasurePolicy(),
    )
}

@LayoutScopeMarker
interface SampleLayoutScope {

    fun Modifier.sampleModifier(
        params: Any,
    ): Modifier
}

private object SampleLayoutScopeInstance : SampleLayoutScope {

    override fun Modifier.sampleModifier(params: Any): Modifier {
        return this then SampleLayoutModifierElement(params = params)
    }
}

private class SampleLayoutMeasurePolicy : MeasurePolicy {

    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints,
    ): MeasureResult {
        val placeables = measurables.map { measurable ->
            val parentData = measurable.parentData as? SampleLayoutParentData
            measurable.measure( // calculate constraints by parentData
                minWidth = 0,
                maxWidth = Constraints.Infinity,
                minHeight = 0,
                maxHeight = Constraints.Infinity,
            )
        }
        val spaceSize = IntSize( // calculate by placeable and parentData
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        )
        return layout(
            width = spaceSize.width,
            height = spaceSize.height,
        ) {
            placeables.forEach { placeable ->
                val position = IntOffset( // calculate for each child
                    x = 0,
                    y = 0,
                )
                placeable.place(position)
            }
        }
    }
}

private data class SampleLayoutParentData(
    var params: Any,
)

private class SampleLayoutModifierNode(
    var params: Any,
) : Modifier.Node(), ParentDataModifierNode {

    override fun Density.modifyParentData(parentData: Any?): SampleLayoutParentData {
        return if (parentData !is SampleLayoutParentData) {
            SampleLayoutParentData(
                params = params,
            )
        } else {
            parentData.params = params
            parentData
        }
    }
}

private class SampleLayoutModifierElement(
    private val params: Any,
) : ModifierNodeElement<SampleLayoutModifierNode>() {

    override fun create(): SampleLayoutModifierNode {
        return SampleLayoutModifierNode(params = params)
    }

    override fun update(node: SampleLayoutModifierNode) {
        node.params = params
    }

    override fun hashCode(): Int {
        return params.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SampleLayoutModifierElement) {
            return false
        }
        return params == other.params
    }
}
