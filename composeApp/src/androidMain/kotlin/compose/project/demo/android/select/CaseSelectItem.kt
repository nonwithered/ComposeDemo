package compose.project.demo.android.select

import androidx.fragment.app.Fragment
import compose.project.demo.common.test.collect.CaseItem

class CaseSelectItem(
    val item: CaseItem,
) : SelectItem {

    override val fragmentClass: Class<out Fragment>
        get() = CaseItemFragment::class.java

    override val pageName: String
        get() = item.name

    override val extras by lazy {
        CaseItemExtrasData().also {
            it.caseItem = item
        }.asBundle()
    }
}