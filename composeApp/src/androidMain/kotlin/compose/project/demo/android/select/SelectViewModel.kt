package compose.project.demo.android.select

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SelectViewModel : ViewModel() {

    val pageData = MutableStateFlow(SelectPageData())

    private companion object {

        private const val TAG = "SelectViewModel"
    }
}
