package compose.project.demo.android.test.collector

import compose.project.demo.android.test.*
import compose.project.demo.common.test.collect.BaseTestCollector
import compose.project.demo.common.test.collect.TestCommonCollector

object TestAndroidCollector : BaseTestCollector() {

    override val list = TestCommonCollector.list + listOf(
        TestAndroid001Stub.asCaseItem
    )
}
