package compose.project.demo.android.select

import compose.project.demo.android.base.list.BaseViewAdapter
import compose.project.demo.android.utils.loadService
import compose.project.demo.common.test.collect.CaseCollector

class SelectViewAdapter<T : SelectItem>(
    type: Class<T>,
) : BaseViewAdapter<SelectItem, SelectViewHolder>() {

    override val items = loadService(type) + CaseCollector.map { CaseSelectItem(it) }

    override val factory = listOf(
        SelectViewFactory,
    )
}
