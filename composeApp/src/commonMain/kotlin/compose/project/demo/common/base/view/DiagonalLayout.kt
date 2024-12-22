package compose.project.demo.common.base.view

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density

@Composable
fun DiagonalLayout(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    content: @Composable DiagonalLayoutScope.() -> Unit,
) {
    Layout(
        content = { DiagonalLayoutScopeInstance.content() },
        modifier = modifier,
        measurePolicy = DiagonalLayoutMeasurePolicy(alignment),
    )
}

@LayoutScopeMarker
@Immutable
interface DiagonalLayoutScope {

    @Stable
    fun Modifier.align(alignment: Alignment): Modifier
}

private object DiagonalLayoutScopeInstance : DiagonalLayoutScope {

    @Stable
    override fun Modifier.align(alignment: Alignment) = this then DiagonalLayoutAlignElement(alignment)
}

private data class DiagonalLayoutMeasurePolicy(
    private val alignment: Alignment,
) : MeasurePolicy {

    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        TODO("Not yet implemented")
    }
}

private class DiagonalLayoutAlignElement(
    val alignment: Alignment,
) : ModifierNodeElement<DiagonalLayoutAlignNode>() {

    override fun create(): DiagonalLayoutAlignNode {
        return DiagonalLayoutAlignNode(alignment)
    }

    override fun update(node: DiagonalLayoutAlignNode) {
        node.alignment = alignment
    }

    override fun InspectorInfo.inspectableProperties() {
        debugInspectorInfo {
            name = "alignment"
            value = alignment
        }
    }

    override fun hashCode(): Int {
        return alignment.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? DiagonalLayoutAlignElement ?: return false
        return alignment == otherModifier.alignment
    }

}

private class DiagonalLayoutAlignNode(
    var alignment: Alignment,
) : ParentDataModifierNode, Modifier.Node() {

    override fun Density.modifyParentData(parentData: Any?): Any? {
        return null
    }
}
