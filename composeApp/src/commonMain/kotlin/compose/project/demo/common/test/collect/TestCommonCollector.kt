package compose.project.demo.common.test.collect

import compose.project.demo.common.test.TestCommon001Compose
import compose.project.demo.common.test.TestCommon002Text
import compose.project.demo.common.test.TestCommon003Spanned
import compose.project.demo.common.test.TestCommon004Edit
import compose.project.demo.common.test.TestCommon005Image

object TestCommonCollector : BaseTestCollector() {

    override val list = listOf(
        TestCommon001Compose.asCaseItem,
        TestCommon002Text.asCaseItem,
        TestCommon003Spanned.asCaseItem,
        TestCommon004Edit.asCaseItem,
        TestCommon005Image.asCaseItem,
    )
}
