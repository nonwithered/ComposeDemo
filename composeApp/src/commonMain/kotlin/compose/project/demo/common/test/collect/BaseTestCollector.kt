package compose.project.demo.common.test.collect

import compose.project.demo.common.test.collect.TestCase.Companion.name
import compose.project.demo.common.test.collect.TestCase.Companion.type

abstract class BaseTestCollector {

    abstract val list: List<CaseItem>

    protected inline val <reified T : TestCase<T>> TestCase<T>.asCaseItem: CaseItem
        get() {
            if (!type.isInstance(this)) {
                throw ClassCastException("from $this to $type")
            }
            return CaseItem(
                name = name,
                view = { Content() },
            )
        }

    fun firstOrNull(name: String?): CaseItem? {
        return list.firstOrNull {
            it.name == name
        }
    }

    fun <T> map(block: (CaseItem) -> T): List<T> {
        return list.map(block)
    }

    fun forEach(block: (CaseItem) -> Unit) {
        list.forEach(block)
    }
}