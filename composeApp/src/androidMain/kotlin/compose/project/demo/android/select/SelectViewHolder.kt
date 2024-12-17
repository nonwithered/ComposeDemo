package compose.project.demo.android.select

import android.view.View
import android.widget.TextView
import compose.project.demo.R
import compose.project.demo.android.base.list.BaseViewHolder

class SelectViewHolder(itemView: View) : BaseViewHolder<SelectItem>(itemView) {

    private val itemName: TextView = itemView.findViewById(R.id.item_name)

    override fun onBind(item: SelectItem, position: Int) {
        super.onBind(item, position)
        itemName.text = item.pageName
        itemView.setOnClickListener {
            val pageData = SelectPageData()
            pageData.pageName = item.pageName
            pageData.fragmentClass = item.fragmentClass
            pageData.showActionBar = item.showActionBar
            pageData.extras = item.extras
            SelectPageActivity.start(context, pageData)
        }
    }

    override fun onUnBind() {
        super.onUnBind()
    }
}
