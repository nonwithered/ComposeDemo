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
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import compose.project.demo.common.utils.coerceAtLeast
import compose.project.demo.common.utils.horizontalBias
import compose.project.demo.common.utils.intOffset
import compose.project.demo.common.utils.intSize
import compose.project.demo.common.utils.plus
import compose.project.demo.common.utils.verticalBias

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
        constraints: Constraints,
    ): MeasureResult {
        var fixedSpaceSize = 0 intSize 0
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(
                Constraints(
                    maxWidth = constraints.maxWidth - fixedSpaceSize.width,
                    maxHeight = constraints.maxHeight - fixedSpaceSize.height,
                )
            )
            fixedSpaceSize += (placeable.width intSize placeable.height)
            placeable
        }
        val spaceSize = fixedSpaceSize.coerceAtLeast(constraints.minWidth intSize constraints.minHeight)
        return layout(
            width = spaceSize.width,
            height = spaceSize.height,
        ) {
            layoutPlaceable(layoutDirection, spaceSize, placeables)
        }
    }

    private fun Placeable.PlacementScope.layoutPlaceable(
        layoutDirection: LayoutDirection,
        spaceSize: IntSize,
        placeables: List<Placeable>,
    ) {
        var fixedSpaceSize = 0 intSize 0
        placeables.forEach { placeable ->
            val placeSize = placeable.width intSize placeable.height
            val placePosition = alignment.align(
                size = placeSize,
                space = spaceSize,
                layoutDirection,
            )
            val positionX = placePosition.x - fixedSpaceSize.width * alignment.horizontalBias * when (layoutDirection) {
                LayoutDirection.Ltr -> {
                    1f
                }
                LayoutDirection.Rtl -> {
                    -1f
                }
            }
            val positionY = placePosition.y - fixedSpaceSize.height * alignment.verticalBias
            placeable.place(positionX intOffset positionY)
            fixedSpaceSize += placeSize
        }
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
