package compose.project.demo.common.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import compose.project.demo.common.utils.rememberWithObserver
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

object TestCommon013Effect : TestCase<TestCommon013Effect> {

    private val list = listOf<@Composable (Int) -> Unit>(
        { TestLaunchedEffect(it) },
        { TestLaunchedEffect(it) },
    )

    @Composable
    override fun BoxScope.Content() {
        var page by remember {
            mutableStateOf(0)
        }
        val index = page
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { page = (index + 1) % list.size },
            ) {
                Text("current $index")
            }
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                list[index](index)
            }
        }
    }

    @Composable
    private fun TestLaunchedEffect(page: Int) {
        var cacheState by remember {
            mutableStateOf(0)
        }
        val value = cacheState
        TAG.logD { "TestLaunchedEffect $page $value" }
        value.rememberWithObserver("$page $value 1") { it, state ->
            TAG.logD { "$it $state" }
        }
        TestSideEffectRecursion(page, false, value, value)
        SideEffect {
            TAG.logD { "TestLaunchedEffect $page SideEffect $value" }
        }
        LaunchedEffect(value / 3) {
            TAG.logD { "TestLaunchedEffect $page LaunchedEffect $value" }
            launch {
                TAG.logD { "TestLaunchedEffect $page LaunchedEffect launch $value" }
                try {
                    suspendCancellableCoroutine<Nothing> {
                    }
                } finally {
                    TAG.logD { "TestLaunchedEffect $page LaunchedEffect launch finally $value" }
                }
            }
        }
        DisposableEffect(value / 2) {
            TAG.logD { "TestLaunchedEffect $page DisposableEffect $value" }
            onDispose {
                TAG.logD { "TestLaunchedEffect $page DisposableEffect onDispose $value" }
            }
        }
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = { cacheState = value + 1 },
        ) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = value.toString(),
                textAlign = TextAlign.Center,
                color = Color.Red,
                fontSize = 100.sp,
            )
        }
        TestSideEffectRecursion(page, true, value, value)
        value.rememberWithObserver("$page $value 2") { it, state ->
            TAG.logD { "$it $state" }
        }
    }

    @Composable
    private fun TestSideEffectRecursion(page: Int, re: Boolean, initCount: Int, count: Int) {
        if (count <= 0 || count >= initCount * 2) {
            return
        }
        SideEffect {
            TAG.logD { "TestSideEffectRecursion $page $re SideEffect $count" }
        }
        TAG.logD { "TestSideEffectRecursion $page $re $count" }
        TestSideEffectRecursion(page, re, initCount, if (re) count - 1 else count + 1)
    }
}
