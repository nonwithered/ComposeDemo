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
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import compose.project.demo.common.utils.BaseModifierNodeElement
import compose.project.demo.common.utils.BaseParentData
import compose.project.demo.common.utils.BaseParentDataModifierNode
import compose.project.demo.common.utils.ParentDataConverter
import compose.project.demo.common.utils.coerceAtLeast
import compose.project.demo.common.utils.horizontalBias
import compose.project.demo.common.utils.intOffset
import compose.project.demo.common.utils.intSize
import compose.project.demo.common.utils.logD
import compose.project.demo.common.utils.logE
import compose.project.demo.common.utils.measure
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
    fun Modifier.placeFractionHorizontal(
        placeFractionHorizontal: Float,
    ): Modifier

    @Stable
    fun Modifier.placeFractionVertical(
        placeFractionVertical: Float,
    ): Modifier
}

private object DiagonalLayoutScopeInstance : DiagonalLayoutScope {

    @Stable
    override fun Modifier.placeFractionHorizontal(
        placeFractionHorizontal: Float,
    ) = this then DiagonalLayoutHorizontalElement(
        placeFractionHorizontal = placeFractionHorizontal,
    )

    @Stable
    override fun Modifier.placeFractionVertical(
        placeFractionVertical: Float,
    ) = this then DiagonalLayoutVerticalElement(
        placeFractionVertical = placeFractionVertical,
    )
}

private class DiagonalLayoutHorizontalElement(
    placeFractionHorizontal: Float,
) : BaseModifierNodeElement<DiagonalLayoutHorizontalNode>() {

    private val placeFractionHorizontal: Float = "placeFractionHorizontal".property(placeFractionHorizontal)

    override val type = DiagonalLayoutHorizontalElement::class

    override fun create(): DiagonalLayoutHorizontalNode {
        "DiagonalLayout".logE(AssertionError()) { "DiagonalLayoutHorizontalElement create" }
      return DiagonalLayoutHorizontalNode(placeFractionHorizontal)
    }

    override fun update(node: DiagonalLayoutHorizontalNode) {
        "DiagonalLayout".logD(AssertionError()) { "DiagonalLayoutHorizontalElement update" }
        node.placeFractionHorizontal = placeFractionHorizontal
    }
}

private class DiagonalLayoutVerticalElement(
    placeFractionVertical: Float,
) : BaseModifierNodeElement<DiagonalLayoutVerticalNode>() {

    private val placeFractionVertical: Float = "placeFractionVertical".property(placeFractionVertical)

    override val type = DiagonalLayoutVerticalElement::class

    override fun create(): DiagonalLayoutVerticalNode {
        "DiagonalLayout".logE(AssertionError()) { "DiagonalLayoutVerticalElement create" }
        return DiagonalLayoutVerticalNode(placeFractionVertical)
    }

    override fun update(node: DiagonalLayoutVerticalNode) {
        "DiagonalLayout".logD(AssertionError()) { "DiagonalLayoutVerticalElement update" }
        node.placeFractionVertical = placeFractionVertical
    }
}

private class DiagonalLayoutHorizontalNode(
    var placeFractionHorizontal: Float,
) : Modifier.Node(), ParentDataModifierNode, BaseParentDataModifierNode<DiagonalLayoutParentData> {

    override val converter = DiagonalLayoutParentData.parentDataConverter

    override fun update(parentData: DiagonalLayoutParentData, density: Density) {
        parentData.placeFractionHorizontal = placeFractionHorizontal
    }

    override fun modifyParentData(parentData: Any?, density: Density): DiagonalLayoutParentData? {
        return super.modifyParentData(parentData, density).also {
            "DiagonalLayout".logD(AssertionError()) { "DiagonalLayoutHorizontalNode modifyParentData" }
        }
    }
}

private class DiagonalLayoutVerticalNode(
    var placeFractionVertical: Float,
) : Modifier.Node(), ParentDataModifierNode, BaseParentDataModifierNode<DiagonalLayoutParentData> {

    override val converter = DiagonalLayoutParentData.parentDataConverter

    override fun update(parentData: DiagonalLayoutParentData, density: Density) {
        parentData.placeFractionVertical = placeFractionVertical
    }

    override fun modifyParentData(parentData: Any?, density: Density): DiagonalLayoutParentData? {
        return super.modifyParentData(parentData, density).also {
            "DiagonalLayout".logD(AssertionError()) { "DiagonalLayoutVerticalNode modifyParentData" }
        }
    }
}

private data class DiagonalLayoutParentData private constructor(
    var placeFractionHorizontal: Float,
    var placeFractionVertical: Float,
) : BaseParentData<DiagonalLayoutParentData> {

    companion object {

        val parentDataConverter = ParentDataConverter(DiagonalLayoutParentData::class) {
            DiagonalLayoutParentData(1f, 1f)
        }
    }
}

private val DiagonalLayoutParentData.placeFraction: Pair<Float, Float>
    get() = placeFractionHorizontal to placeFractionVertical

private data class DiagonalLayoutMeasurePolicy(
    private val alignment: Alignment,
) : MeasurePolicy {

    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints,
    ): MeasureResult {
        "DiagonalLayout".logD(AssertionError()) { "DiagonalLayoutMeasurePolicy measure" }
        var fixedSpaceSize = 0 intSize 0
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(
                maxWidth = constraints.maxWidth - fixedSpaceSize.width,
                maxHeight = constraints.maxHeight - fixedSpaceSize.height,
            )
            fixedSpaceSize += placeable.size * DiagonalLayoutParentData.parentDataConverter.cast(placeable).placeFraction
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
        "DiagonalLayout".logD(AssertionError()) { "DiagonalLayoutMeasurePolicy layout" }
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
            fixedSpaceSize += placeable.size * DiagonalLayoutParentData.parentDataConverter.cast(placeable).placeFraction
        }
    }
}
