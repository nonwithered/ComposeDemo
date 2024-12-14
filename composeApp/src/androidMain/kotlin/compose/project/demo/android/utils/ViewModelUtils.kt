package compose.project.demo.android.utils

import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModels() = lazy {
    requireActivity().viewModels<VM>().value
}
