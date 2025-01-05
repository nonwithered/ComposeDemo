package compose.project.demo.common.test.sample

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import compose.project.demo.common.test.collect.TestCase
import compose.project.demo.common.test.collect.TestCase.Companion.TAG
import compose.project.demo.common.utils.logD
import org.koin.compose.koinInject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
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

    }
}
