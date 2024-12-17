package compose.project.demo.wasm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import compose.project.demo.common.test.collect.TestCommonCollector
import compose.project.demo.common.utils.logD
import compose.project.demo.wasm.bean.URLSearchParamsProperties
import compose.project.demo.wasm.ffi.windowOpen
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

fun main() {
    content()
}

private fun content() {
    val items = TestCommonCollector.list
    items.forEach {
        "Content".logD { it.name }
    }
    window.location.run {
        "location".logD { "href $href" }
        "location".logD { "origin $origin" }
        "location".logD { "protocol $protocol" }
        "location".logD { "host $host" }
        "location".logD { "hostname $hostname" }
        "location".logD { "port $port" }
        "location".logD { "pathname $pathname" }
        "location".logD { "search $search" }
        "location".logD { "hash $hash" }
        "location".logD { "ancestorOrigins $ancestorOrigins" }
    }
    if (route()) {
        return
    }
    viewport {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(state = scrollState),
        ) {
            items.forEach {
                Button(
                    onClick = {
                        val name = it.name
                        val url = "${window.location.origin}?case=$name"
                        windowOpen(url)
                    }
                ) {
                    Text(text = it.name)
                }
            }
        }
    }
}

private fun route(): Boolean {
    val search = CaseItemProperties(window.location.search)
    val view = TestCommonCollector.list.firstOrNull {
        it.name == search.case
    }?.view ?: return false
    viewport {
        view()
    }
    return true
}

@OptIn(ExperimentalComposeUiApi::class)
fun viewport(content: @Composable BoxScope.() -> Unit) {
    ComposeViewport(document.body!!) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            content()
        }
    }
}

private class CaseItemProperties : URLSearchParamsProperties {

    constructor(search: String) : super(URLSearchParams(search.toJsString()))

    val case: String? by "case".property()
}
