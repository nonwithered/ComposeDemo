package compose.project.demo.android.select

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import compose.project.demo.R
import compose.project.demo.android.base.page.BaseActivity

class PageSelectorActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.page_selector_activity

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        initList()
    }

    private fun initList() {
        val listView: RecyclerView = findViewById(R.id.list_view)
        listView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.adapter = SelectViewAdapter(SelectItem::class.java)
    }
}
