package compose.project.demo.android.select

import compose.project.demo.android.base.list.BaseViewAdapter
import compose.project.demo.android.test.collector.TestAndroidCollector
import compose.project.demo.android.utils.loadService

class SelectViewAdapter : BaseViewAdapter<SelectItem, SelectViewHolder>() {

    override val items = loadService<SelectItem>() + TestAndroidCollector.map { CaseSelectItem(it) }

    override val factory = listOf(
        SelectViewFactory,
    )
}
