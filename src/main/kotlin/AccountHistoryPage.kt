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
            if (hasNewDomTransactionsBeenLoaded()) {
                redecorateDomTransactions()
            }
        })

        document.body!!.addEventListener("mousemove", {
            if (hasNewDomTransactionsBeenLoaded()) {
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
        var greatestTransactionAmount = transactionStore.getGreatestTransactionAmount()

        if(greatestTransactionAmount!! > 1000) {
            greatestTransactionAmount = 1000.0
        }

        for (it in transactions.iterator()) {
            if (it.amount!! < 0) {
                val relativeAmount = abs(it.amount!! / greatestTransactionAmount)
                it.domTransaction?.setAttribute(
                    "style",
                    "background: " + getBackgroundColorForRelativeAmount(relativeAmount)
                )
            }
        }
    }

    private fun getBackgroundColorForRelativeAmount(relativeAmount: Double): String {
        val maxHue = 120
        val logLimit = 8
        val logScale = maxHue / logLimit
        val hue = maxHue - logScale * log(100*relativeAmount, 2.0)

        return "hsl($hue,100%,90%)"
    }
}