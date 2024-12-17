package compose.project.demo.android.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

private fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>): () -> Unit {
    liveData.observe(this, observer)
    return {
        liveData.removeObserver(observer)
    }
}

fun <T> LifecycleOwner.bind(state: LiveData<T>, block: (T?) -> Unit): () -> Unit {
    return observe(state, block)
}
