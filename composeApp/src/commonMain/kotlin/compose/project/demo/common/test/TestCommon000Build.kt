package compose.project.demo.common.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class QWE {

    fun a(b: (Int) -> Unit) {

    }

    fun b(s: String, i: Int) {

    }

    fun c() {
        val s = "qwe"
        a {
            b(s + this.toString(), it)
        }
    }

    @Composable
    fun q() {

    }
}

@Composable
fun Content() {
    var count by remember {
        mutableStateOf(0)
    }
    Text(
        text = "$count",
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                count++
            },
    )
}

@Composable
fun asdfgh(a: Int) {
    if (a == 0) {
        var s1 by remember {
            mutableStateOf(a)
        }
        if (s1 == 0) {
            var s2 by remember {
                mutableStateOf(s1)
            }
        }
    } else {
        var s1 by remember {
            mutableStateOf(a)
        }
        if (s1 == 0) {
            var s2 by remember {
                mutableStateOf(s1)
            }
        }
    }
}

@Composable
fun Foo1(p1: Int, p2: Float, p3: String) {
    if (check()) {
        val s = remember {
            ""
        }
        Bar(p1, p2, p3)
    } else {
        Bar(p1, p2, p3)
    }
}

@Composable
fun Foo(p0: Int, p1: Float, p2: String) {
    if (check()) {
        Bar(p0, p1, p2)
    } else {
        Bar(p0, p1, p2)
    }
}

@Stable
@Composable
fun Bar(p0: Int, p1: Float, p2: String) : String {
    return if (check()) {
        Bar(p0, p1, p2)
    } else {
        Bar(p0, p1, p2)
    }
}

fun check(): Boolean {
    return false
}

@Composable
fun qwerty(a: Int, b: Double, c: String) {
    zxcv(a, b, c)
}

@Composable
fun zxcv(a: Int, b: Double, c: String) {

}

@Composable
fun qwerty2(a: Int, b: Double, c: String): Int {
    return zxcv2(a, b, c)
}

@Composable
fun zxcv2(a: Int, b: Double, c: String): Int {
    return 0
}

@Stable
@Composable
fun qwerty3(a: Int, b: Double, c: String): Int {
    return zxcv2(a, b, c)
}

@Stable
@Composable
fun zxcv3(a: Int, b: Double, c: String): Int {
    return 0
}

@Stable
@Composable
fun qwerty4(a: Int, b: Double, c: String) {
    zxcv(a, b, c)
}

@Stable
@Composable
fun zxcv4(a: Int, b: Double, c: String) {

}

@Composable
fun qqq(v: Z?): Int {
    v ?: return 0
    return r("")
}

@Composable
fun www(v: Z?): Int {
    v ?: return 0
    return rr("")
}

@Composable
fun qqq(v: A?) {
    v ?: return
    r("")
}

@Composable
fun www(v: A?) {
    v ?: return
    rr("")
}

@Composable
fun q() {

}

@Composable
fun w(): String {
    return ""
}

@Composable
fun e(a: Z) {
    t(a.toString())
}

@Composable
fun r(a: String): Int {
    return 0
}

@Composable
fun t(a: String) {
    return
}

@Stable
@Composable
fun qq() {

}

@Stable
@Composable
fun ww(): String {
    return ""
}

@Stable
@Composable
fun ee(a: Z) {

    e(a as Z)
}

@Stable
@Composable
fun rr(a: String): Int {
    e(a as Z)
    return 0
}

fun aaa(): Long {
    return 0
}

class Z {
    
}

data class A(
    val a: Int,
    var s: String,
    var d: A?,
) {

    @Composable
    fun q() {

    }

    @Composable
    fun w(): String {
        return ""
    }

    @Composable
    fun e(a: Z) {

    }

    @Composable
    fun r(a: String): Int {
        return 0
    }

    @Stable
    @Composable
    fun qq() {

    }

    @Stable
    @Composable
    fun ww(): String {
        return ""
    }

    @Stable
    @Composable
    fun ee(a: Z) {

    }

    @Stable
    @Composable
    fun rr(a: String): Int {
        return 0
    }
}

@Stable
data class B(
    val a: Int,
    var s: String,
    var d: A?,
) {

    @Composable
    fun q() {

    }

    @Composable
    fun w(): String {
        return ""
    }

    @Composable
    fun e(a: Z) {

    }

    @Composable
    fun r(a: String): Int {
        return 0
    }

    @Composable
    fun qq() {

    }

    @Composable
    fun ww(): String {
        return ""
    }

    @Composable
    fun ee(a: Z) {

    }

    @Composable
    fun rr(a: String): Int {
        return 0
    }
}

@Immutable
data class C(
    val a: Int,
    var s: String,
    var d: A?,
) {

    @Composable
    fun q() {

    }

    @Composable
    fun w(): String {
        return ""
    }

    @Composable
    fun e(a: Z) {

    }

    @Composable
    fun r(a: String): Int {
        return 0
    }

    @Composable
    fun qq() {

    }

    @Composable
    fun ww(): String {
        return ""
    }

    @Composable
    fun ee(a: Z) {

    }

    @Composable
    fun rr(a: String): Int {
        return 0
    }
}
