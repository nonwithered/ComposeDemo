package compose.project.demo.common.utils

import androidx.annotation.ColorLong
import androidx.compose.ui.graphics.Color

val @receiver:ColorLong Long.asColor: Color
    get() = Color(this)
