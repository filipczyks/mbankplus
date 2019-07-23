package transactions

import org.w3c.dom.Element

class TransactionPresenter(var element: Element, val index: Int) {
    fun getAttribute(name: String): String? {
        return element.getAttribute(name)
    }

    fun setAttribute(key: String, value: String) {
        element.setAttribute(key, value)
    }

    fun getDateFromTextField(): String {
        return element.querySelector(".date")!!.textContent!!
    }

    fun getAmountField(): Element {
        return element.querySelector(".amount")!!
    }
}
