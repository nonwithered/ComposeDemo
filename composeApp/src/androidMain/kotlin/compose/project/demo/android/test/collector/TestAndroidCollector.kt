package compose.project.demo.android.test.collector

import compose.project.demo.android.select.CaseSelectItem
import compose.project.demo.android.select.SelectItem
import compose.project.demo.android.test.*
import compose.project.demo.common.test.collect.BaseTestCollector
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCommonCollector

object TestAndroidCollector : BaseTestCollector() {

    val list = TestCommonCollector.list.map { CaseSelectItem(it) } + listOf(
        TestAndroid001NestedScrollInteropSamples.asSelectItem,
        TestAndroid002NavHost.asSelectItem,
        TestAndroid003NestedScroll,
        TestAndroid004Recompose.asSelectItem,
    )

    private inline val <reified T : TestCase<T>> TestCase<T>.asSelectItem: SelectItem
        get() = CaseSelectItem(asCaseItem)
}
