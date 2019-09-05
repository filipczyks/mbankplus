import org.w3c.dom.Element
import org.w3c.dom.asList
import transactions.TransactionPresenter
import transactions.Transaction
import transactions.TransactionStore
import transactions.decorators.ColorByAmountTransactionsDecorator
import transactions.decorators.SumByDayTransactionsDecorator
import kotlin.browser.document

class AccountHistoryPage {
    private val domTransactions = document.getElementsByClassName("content-list-row").asList()

    private val transactionStore: TransactionStore = TransactionStore()

    init {
        document.body!!.addEventListener("mouseover", {
            if (hasNewDomTransactionsBeenLoaded()) {
                redecorateTransactions()
            }
        })

        document.body!!.addEventListener("mousemove", {
            if (hasNewDomTransactionsBeenLoaded()) {
                redecorateTransactions()
            }
        })
    }

    private fun redecorateTransactions() {
        val transactionPresenters = buildTransactionPresentersFrom(domTransactions)
        transactionStore.replaceAll(transactionPresenters.map { Transaction.from(it) })

        decorateTransactions(transactionStore.findAll())
    }

    private fun buildTransactionPresentersFrom(domTransactions: List<Element>) =
        domTransactions.mapIndexed { index, element -> TransactionPresenter(element, index) }

    private fun hasNewDomTransactionsBeenLoaded(): Boolean {
        return domTransactions.count() > transactionStore.getTransactionsCount()
    }

    private fun decorateTransactions(transactions: List<Transaction>) {
        ColorByAmountTransactionsDecorator()
            .decorate(transactions)
        SumByDayTransactionsDecorator()
            .decorate(transactions)

    }
}