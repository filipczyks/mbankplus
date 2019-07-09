import org.w3c.dom.asList
import transactions.Transaction
import transactions.TransactionStore
import kotlin.browser.document
import kotlin.math.abs
import kotlin.math.log

class AccountHistoryPage {
    private val domTransactions = document.getElementsByClassName("content-list-row").asList()
    private val transactionStore: TransactionStore = TransactionStore()

    init {
        document.body!!.addEventListener("mouseover", {
            if(hasNewDomTransactionsBeenLoaded()) {
                redecorateDomTransactions()
            }
        })

        document.body!!.addEventListener("mousemove", {
            if(hasNewDomTransactionsBeenLoaded()) {
                redecorateDomTransactions()
            }
        })
    }

    private fun redecorateDomTransactions() {
        transactionStore.replaceAll(domTransactions.map { Transaction.from(it) })
        decorateTransactions(transactionStore.findAll())
    }

    private fun hasNewDomTransactionsBeenLoaded(): Boolean {
        return domTransactions.count() > transactionStore.getTransactionsCount()
    }

    private fun decorateTransactions(transactions: List<Transaction>) {
        val greatestTransactionAmount = transactionStore.getGreatestTransactionAmount()

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
}