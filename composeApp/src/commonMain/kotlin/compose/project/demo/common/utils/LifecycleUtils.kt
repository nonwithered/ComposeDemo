package compose.project.demo.common.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

private fun <T> LifecycleOwner.collect(flow: Flow<T>, collector: FlowCollector<T>): () -> Unit {
    val job = lifecycleScope.launch {
        flow.collect(collector)
    }
    return {
        job.cancel()
    }
}

fun <T> LifecycleOwner.bind(state: Flow<T>, block: (T) -> Unit): () -> Unit {
    return collect(state, block)
}
