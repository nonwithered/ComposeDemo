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
import androidx.compose.ui.layout.Measured
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
import compose.project.demo.common.utils.minSize
import compose.project.demo.common.utils.plus
import compose.project.demo.common.utils.size
import compose.project.demo.common.utils.times
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
    fun Modifier.placeFraction(
        placeFractionHorizontal: Float = 1f,
        placeFractionVertical: Float = 1f,
    ): Modifier
}

private object DiagonalLayoutScopeInstance : DiagonalLayoutScope {

    @Stable
    override fun Modifier.placeFraction(
        placeFractionHorizontal: Float,
        placeFractionVertical: Float,
    ) = this then DiagonalLayoutElement(
        placeFractionHorizontal = placeFractionHorizontal,
        placeFractionVertical = placeFractionVertical,
    )
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
            fixedSpaceSize += placeable.size * placeable.placeFraction
            placeable
        }
        val spaceSize = fixedSpaceSize.coerceAtLeast(constraints.minSize)
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
            val placeSize = placeable.size
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
            fixedSpaceSize += placeSize * placeable.placeFraction
        }
    }
}

private class DiagonalLayoutElement(
    private val placeFractionHorizontal: Float,
    private val placeFractionVertical: Float,
) : ModifierNodeElement<DiagonalLayoutNode>() {

    override fun create(): DiagonalLayoutNode {
        return DiagonalLayoutNode(
            placeFractionHorizontal = placeFractionHorizontal,
            placeFractionVertical = placeFractionVertical,
        )
    }

    override fun update(node: DiagonalLayoutNode) {
        node.placeFractionHorizontal = placeFractionHorizontal
        node.placeFractionVertical = placeFractionVertical
    }

    override fun InspectorInfo.inspectableProperties() {
        debugInspectorInfo {
            name = "placeFraction"
            properties["placeFractionHorizontal"] = placeFractionHorizontal
            properties["placeFractionVertical"] = placeFractionVertical
        }
    }

    override fun hashCode(): Int {
        Float.hashCode()
        return (placeFractionHorizontal to placeFractionVertical).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? DiagonalLayoutElement ?: return false
        return placeFractionHorizontal == otherModifier.placeFractionHorizontal && placeFractionVertical == otherModifier.placeFractionVertical
    }

}

private class DiagonalLayoutNode(
    var placeFractionHorizontal: Float,
    var placeFractionVertical: Float,
) : ParentDataModifierNode, Modifier.Node() {

    override fun Density.modifyParentData(parentData: Any?): DiagonalLayoutParentData {
        return ((parentData as? DiagonalLayoutParentData) ?: DiagonalLayoutParentData()).also {
            it.placeFractionHorizontal = placeFractionHorizontal
            it.placeFractionVertical = placeFractionVertical
        }
    }
}

private val Measured.diagonalLayoutParentData: DiagonalLayoutParentData?
    get() = parentData as? DiagonalLayoutParentData

private val Measured.placeFraction: Pair<Float, Float>
    get() {
        val placeFractionHorizontal = diagonalLayoutParentData?.placeFractionHorizontal ?: 1f
        val placeFractionVertical = diagonalLayoutParentData?.placeFractionVertical ?: 1f
        return placeFractionHorizontal to placeFractionVertical
    }

private data class DiagonalLayoutParentData(
    var placeFractionHorizontal: Float? = null,
    var placeFractionVertical: Float? = null,
)
