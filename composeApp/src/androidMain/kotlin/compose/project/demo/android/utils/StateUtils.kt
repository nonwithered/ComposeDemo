package compose.project.demo.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import compose.project.demo.common.utils.toMutableState

@Composable
fun <T> MutableLiveData<T>.observeAsMutableState(): MutableState<T?> {
    return observeAsState().toMutableState {
        value = it
    }
}

val <T> LiveData<T>.asState: State<T?>
    @Composable
    get() = observeAsState()

val <T> MutableLiveData<T>.asState: MutableState<T?>
    @Composable
    get() = observeAsMutableState()

@Composable
fun <T> LiveData<T>.observeAsStateVolatile(): State<T?> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember {
        mutableStateOf(value, neverEqualPolicy())
    }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { state.value = it }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}

@Composable
fun <T> MutableLiveData<T>.observeAsMutableStateVolatile(): MutableState<T?> {
    return observeAsStateVolatile().toMutableState {
        value = it
    }
}

val <T> LiveData<T>.asStateVolatile: State<T?>
    @Composable
    get() = observeAsStateVolatile()

val <T> MutableLiveData<T>.asStateVolatile: MutableState<T?>
    @Composable
    get() = observeAsMutableStateVolatile()
