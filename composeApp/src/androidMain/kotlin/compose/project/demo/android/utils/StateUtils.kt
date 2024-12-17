package compose.project.demo.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
