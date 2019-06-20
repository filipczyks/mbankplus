import org.w3c.dom.Element
import org.w3c.dom.asList
import kotlin.browser.document
import kotlin.math.abs
import kotlin.math.log

class AccountHistoryPage {
    private val domTransactions = document.getElementsByClassName("content-list-row").asList()

    init {
        document.body!!.addEventListener("click", {
            console.log("click on body")
            decorateTransactions()
        })
    }

    private fun decorateTransactions() {
        val transactions = buildTransactions(domTransactions)
        val greatestTransactionAmount = findGreatestTransactionByAmount(transactions).amount

        for (it in transactions.iterator()) {
            it.relativeAmount = abs(it.amount!! / greatestTransactionAmount!!)
            it.domTransaction?.setAttribute("style", "background: " + getBackgroundColorForDomTransaction(it))
        }
    }

    private fun getBackgroundColorForDomTransaction(it: Transaction): String {
        val relativeAmount = 255 - log(it.relativeAmount!! * 200, 2.0) * 25

        return if (it.amount!! > 0) {
            "rgb($relativeAmount,255,$relativeAmount)"
        } else {
            "rgb(255,$relativeAmount,$relativeAmount)"
        }
    }

    private fun buildTransactions(domTransactions: List<Element>): List<Transaction> {
        return domTransactions.map { Transaction.from(it) }
    }

    private fun findGreatestTransactionByAmount(transactions: List<Transaction>): Transaction {
        return transactions.maxBy { abs(it.amount!!) }!!
    }
}