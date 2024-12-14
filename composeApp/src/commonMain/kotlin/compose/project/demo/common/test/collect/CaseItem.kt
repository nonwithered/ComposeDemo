package compose.project.demo.common.test.collect

import androidx.compose.runtime.Composable

data class CaseItem(
    val name: String,
    val view: @Composable () -> Unit
)
