package compose.project.demo.android.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import compose.project.demo.R
import compose.project.demo.android.base.page.BaseActivity
import compose.project.demo.common.utils.elseTrue
import compose.project.demo.common.utils.logD

class SelectPageActivity : BaseActivity() {

    companion object {

        private const val TAG = "SelectPageActivity"

        private const val TAG_PAGE_FRAGMENT = "TAG_SELECT_PAGE_FRAGMENT"

        fun start(context: Context, pageData: SelectPageData) {
            val intent = Intent(context, SelectPageActivity::class.java).putExtras(pageData.asBundle())
            context.startActivity(intent)
        }
    }

    private val vm by viewModels<SelectViewModel>()

    private val pageData by lazy {
        SelectPageData(intent.extras).also { pageData ->
            vm.pageData.value = pageData
            TAG.logD { "update pageData ${pageData.pageName}" }
        }
    }

    override val layoutId: Int
        get() = R.layout.select_page_activity

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        initFragment()
        initActionBar()
    }

    private fun initFragment() {
        ensureFragmentEmpty()
        val fragmentClass = pageData.fragmentClass
        if (fragmentClass === null) {
            finish()
            return
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentClass, null, TAG_PAGE_FRAGMENT)
            .commit()
    }

    private fun ensureFragmentEmpty() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_PAGE_FRAGMENT) ?: return
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
    }

    private fun initActionBar() {
        supportActionBar?.title = pageData.pageName
        if (!pageData.showActionBar.elseTrue) {
            supportActionBar?.hide()
        }
    }
}
