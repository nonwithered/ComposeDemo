package compose.project.demo.common.test.collect

import androidx.compose.runtime.Composable
import compose.project.demo.common.test.*

object CaseCollector {

    private val list: List<CaseItem> = listOf(
        "Case001TestCompose" case { Case001TestCompose() },
        "Case002TestText" case { Case002TestText() },
        "Case003TestSpanned" case { Case003TestSpanned() },
        "Case004TestEdit" case { Case004TestEdit() },
    )

    private infix fun String.case(view: @Composable () -> Unit): CaseItem {
        val name = this
        return CaseItem(
            name = name,
            view = view,
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
