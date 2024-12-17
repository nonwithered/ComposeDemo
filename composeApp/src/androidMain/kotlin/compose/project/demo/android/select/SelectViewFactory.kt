package compose.project.demo.android.select

import android.view.View
import compose.project.demo.R
import compose.project.demo.android.base.list.BaseViewFactory

object SelectViewFactory : BaseViewFactory<SelectItem, SelectViewHolder> {

    override val layoutId: Int
        get() = R.layout.select_item_view

    override fun createViewHolder(itemView: View): SelectViewHolder {
        return SelectViewHolder(itemView)
    }

    override fun checkItemType(item: SelectItem): Boolean {
        return true
    }
}
