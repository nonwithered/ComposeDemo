package compose.project.demo.common.test.collect

import compose.project.demo.common.test.*

object TestCommonCollector : BaseTestCollector() {

    val list = listOf(
        TestCommon001Compose.asCaseItem,
        TestCommon002Text.asCaseItem,
        TestCommon003Spanned.asCaseItem,
        TestCommon004Edit.asCaseItem,
        TestCommon005Image.asCaseItem,
        TestCommon006Pager.asCaseItem,
        TestCommon007Constraint.asCaseItem,
        TestCommon008Constraint.asCaseItem,
        TestCommon009List.asCaseItem,
        TestCommon010Grid.asCaseItem,
        TestCommon011Staggered.asCaseItem,
        TestCommon012Canvas.asCaseItem,
        TestCommon013Effect.asCaseItem,
        TestCommon014AnimatedVisibility.asCaseItem,
        TestCommon015AnimatedContent.asCaseItem,
        TestCommon016SharedElement.asCaseItem,
        TestCommon017SharedBounds.asCaseItem,
        TestCommon018SharedCustom.asCaseItem,
        TestCommon019CustomLayout.asCaseItem,
        TestCommon020Gestures.asCaseItem,
        TestCommon021Loadmore.asCaseItem,
        TestCommon022NormalCustomLayout.asCaseItem,
        TestCommon023NestedScroll.asCaseItem,
    )
}
