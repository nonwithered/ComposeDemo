package compose.project.demo.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import compose.project.demo.common.utils.MutableStateProxy

@Composable
fun <T> MutableLiveData<T>.observeAsMutableState(): MutableState<T?> {
    return MutableStateProxy(observeAsState()) {
        value = it
    }
}

val <T> LiveData<T>.asState: State<T?>
    @Composable
    get() = observeAsState()

val <T> MutableLiveData<T>.asState: MutableState<T?>
    @Composable
    get() = observeAsMutableState()

private fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>): () -> Unit {
    liveData.observe(this, observer)
    return {
        liveData.removeObserver(observer)
    }
}

fun <T> LifecycleOwner.bind(state: LiveData<T>, block: (T?) -> Unit): () -> Unit {
    return observe(state, block)
}
