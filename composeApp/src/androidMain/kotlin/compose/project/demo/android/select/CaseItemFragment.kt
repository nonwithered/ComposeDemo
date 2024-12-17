package compose.project.demo.android.select

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.project.demo.android.base.page.BaseComposeFragment
import compose.project.demo.common.utils.asState
import compose.project.demo.common.utils.logD

class CaseItemFragment : BaseComposeFragment() {

    private companion object {

        private const val TAG = "CaseItemFragment"
    }

    @Composable
    override fun BoxScope.ComposeContent() {
        var showing by remember {
            mutableStateOf(false)
        }
        TAG.logD { "showing $showing" }
        LifecycleEventEffect(Lifecycle.Event.ON_START) {
            TAG.logD { "ON_START" }
            showing = true
        }
        if (showing) {
            CaseContent()
        }
    }

    @Composable
    private fun BoxScope.CaseContent() {
        val pageData by viewModel<SelectViewModel>(
            viewModelStoreOwner = requireActivity(),
        ).pageData.asState
        TAG.logD { "CaseContent ${pageData.pageName}" }
        val view = CaseItemExtrasData(pageData.extras).caseItem?.view ?: return
        view()
    }
}
