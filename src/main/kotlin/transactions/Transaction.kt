package transactions

import org.w3c.dom.Element

class Transaction {
    var amount: Double? = null
    var relativeAmount: Double? = null
    var domTransaction: Element? = null

    companion object {
        fun from(element: Element): Transaction {
            val transaction = Transaction()
            transaction.amount = element
                    .getAttribute("data-amount")!!
                    .replace(",", ".")
                    .toDouble()

            transaction.domTransaction = element

            return transaction
        }

    }
}