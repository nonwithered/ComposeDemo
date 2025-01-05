package compose.project.demo.common.test.sample

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.koinInject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

var idGetter = 0

class A {
    var id: Int = ++idGetter
}
class B(
    var a: A,
) {
    var id: Int = ++idGetter
}

val myModule = module {
    singleOf(::B)
    singleOf(::A)
    scope<TestCase<*>> {
        scopedOf(::B)
        scopedOf(::A)
    }
}

object SampleKoin : TestCase<SampleKoin> {

    init {
        startKoin {
            modules(myModule)
        }
    }

    @Composable
    override fun BoxScope.Content() {
        val m = module {
            singleOf(::B)
            singleOf(::A)
        }
        var b: B

        b = koinInject()
        TAG.logD { "1 ${b.id}" }
        TAG.logD { "2 ${b.a.id}" }

        loadKoinModules(m)
        b = koinInject()
        TAG.logD { "3 ${b.id}" }
        TAG.logD { "4 ${b.a.id}" }
//        unloadKoinModules(m)

        SampleSnapshotFlow()

        var count by remember {
            mutableStateOf(0)
        }

        val isPositive by remember {
            derivedStateOf {
                count > 0
            }
        }
    }

    private val sampleFlow = MutableStateFlow(0)

    @Composable
    private fun SampleSnapshotFlow() {
        val countState = remember {
            mutableStateOf(0)
        }
        LaunchedEffect(countState) {
            snapshotFlow {
                countState.value
            }.collect { value ->
                sampleFlow.value = value
            }
        }
        var count by countState
        // Ignore some code
    }

    private fun println(msg: Any?) {
        TAG.logD { "${msg}" }
    }
}
