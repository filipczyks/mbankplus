package transactions

import org.w3c.dom.Element

class TransactionPresenter(var element: Element, val index: Int) {
    fun getAttribute(name: String): String? {
        return element.getAttribute(name)
    }

    fun getDateField(): Element? {
        return element.querySelector(".date")
    }

    fun getAmountField(): Element? {
        return element.querySelector(".amount")
    }
}
