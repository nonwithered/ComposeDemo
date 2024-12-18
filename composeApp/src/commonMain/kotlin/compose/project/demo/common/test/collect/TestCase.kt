package compose.project.demo.common.test.collect

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

interface TestCase<T : TestCase<T>> {

    @Composable
    fun BoxScope.Content()

    companion object {

        inline val <reified T : TestCase<T>> TestCase<T>.type: KClass<T>
            get() = T::class


        inline val <reified T : TestCase<T>> TestCase<T>.name: String
            get() = type.simpleName!!


        inline val <reified T : TestCase<T>> TestCase<T>.TAG: String
            get() = name
    }
}
