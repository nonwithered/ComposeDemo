package compose.project.demo.android.base.page

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import compose.project.demo.R

abstract class BaseComposeFragment : BaseFragment() {

    final override val layoutId: Int
        get() = R.layout.compose_fragment

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                Box(
                    modifier = Modifier.fillMaxWidth().padding(innerPadding),
                ) {
                    ComposeContent()
                }
            }
        }
    }

    @Composable
    protected abstract fun BoxScope.ComposeContent()
}
