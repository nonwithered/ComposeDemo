package compose.project.demo.android.select

import android.os.Bundle
import compose.project.demo.android.base.bean.BundleProperties
import compose.project.demo.common.test.collect.CaseCollector
import compose.project.demo.common.test.collect.CaseItem

class CaseItemExtrasData(
    bundle: Bundle? = null,
): BundleProperties(bundle ?: Bundle()) {

    private var name: String? by "case".property()

    var caseItem: CaseItem?
        get() = CaseCollector.firstOrNull(name)
        set(value) {
            name = value?.name
        }
}
