package compose.project.demo.common.test.collect

import compose.project.demo.common.test.collect.TestCase.Companion.name
import compose.project.demo.common.test.collect.TestCase.Companion.type
import compose.project.demo.common.utils.logD

abstract class BaseTestCollector {

    abstract val list: List<CaseItem>

    protected inline val <reified T : TestCase<T>> TestCase<T>.asCaseItem: CaseItem
        get() {
            if (!type.isInstance(this)) {
                throw ClassCastException("from $this to $type")
            }
            return CaseItem(
                name = name,
                view = {
                    "asCaseItem".logD { "Content $name" }
                    Content()
                },
            )
        }
}
