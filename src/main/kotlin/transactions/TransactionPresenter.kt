package transactions

import org.w3c.dom.Element

class TransactionPresenter(var element: Element, val index: Int) {
    companion object {
        val CLASS_TRANSACTION = "_2kAiFiNqJBf0hoUh8-KuAT"
        val CLASS_IS_INTERNAL = "_2Ct8R_2IyNsJvMNbyHeLBH"
        val CLASS_DATE = "UX5dED1uU_XhOZZD36O_L"
        val CLASS_AMOUNT = "_3c5cbKfH9GVa6SsOFUEjC2"
    }

    fun getAttribute(name: String): String? {
        return element.getAttribute(name)
    }

    fun getDateField(): Element? {
        return element.querySelector(".$CLASS_DATE")
    }

    fun getAmountField(): Element? {
        return element.querySelector(".$CLASS_AMOUNT")
    }
}
