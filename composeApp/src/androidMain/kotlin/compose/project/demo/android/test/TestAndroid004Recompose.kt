package compose.project.demo.android.test

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object TestAndroid004Recompose : TestCase<TestAndroid004Recompose> {

    @Composable
    override fun BoxScope.Content() {
        var state by remember {
            mutableStateOf(0, neverEqualPolicy())
        }
        var first by remember {
            mutableStateOf(0)
        }
        TAG.logD(AssertionError()) { "Content $state" }
//        LaunchedEffect(Unit) {
//            delay(2000)
//            withContext(Dispatchers.IO) {
//                state = 1
//            }
//        }
        state = 1
        if (first == 0 && state == 1) {
            first = 1
            state = 2
        } else {
            state = 1
        }
    }
}
