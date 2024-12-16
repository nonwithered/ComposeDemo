package compose.project.demo.wasm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import compose.project.demo.common.legacy.App
import compose.project.demo.common.test.collect.TestCommonCollector
import compose.project.demo.common.utils.logI
import compose.project.demo.wasm.bean.URLSearchParamsProperties
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val TAG = "main"
    TestCommonCollector.forEach {
        TAG.logI { it.name }
    }
    val search = CaseItemProperties(window.location.search)
    ComposeViewport(document.body!!) {
        val view = TestCommonCollector.firstOrNull(search.case)?.view
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            view?.invoke(this) ?: App()
        }
    }
}

private class CaseItemProperties : URLSearchParamsProperties {

    constructor(search: String) : super(URLSearchParams(search.toJsString()))

    val case: String? by "case".property()
}
