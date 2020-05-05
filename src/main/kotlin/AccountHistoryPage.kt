import org.w3c.dom.*
import transactions.TransactionPresenter
import transactions.Transaction
import transactions.TransactionStore
import transactions.decorators.ColorByAmountTransactionsDecorator
import transactions.decorators.HideInternalTransactionsDecorator
import transactions.decorators.SumByDayTransactionsDecorator
import kotlin.browser.document
import kotlin.browser.window

class AccountHistoryPage {
    private var domTransactions = loadDomTransactions()

    private fun loadDomTransactions(): List<Element> {
        val transactions = document.getElementsByClassName(TransactionPresenter.CLASS_TRANSACTION).asList()

        return transactions
    }

    private val transactionStore: TransactionStore = TransactionStore()

    init {
        window.addEventListener("load", {
            console.log("load")
            window.setInterval({
                redecorateTransactionsIfNewAreLoaded()
            }, 100)
        })
    }

    private fun redecorateTransactionsIfNewAreLoaded() {
        if (hasNewDomTransactionsBeenLoaded()) {
            val deltaCount = domTransactions.count() - transactionStore.getCount()
            redecorateTransactions(domTransactions.takeLast(deltaCount))
        }
    }

    private fun redecorateTransactions(domTransactions: List<Element>) {
        val transactionPresenters = buildTransactionPresentersFrom(domTransactions)
        transactionStore.addAll(transactionPresenters.map { Transaction.from(it) })

        decorateTransactions(transactionStore.findAll())
    }

    private fun buildTransactionPresentersFrom(domTransactions: List<Element>) =
        domTransactions.mapIndexed { index, element -> TransactionPresenter(element, index) }

    private fun hasNewDomTransactionsBeenLoaded(): Boolean {
        return domTransactions.count() > transactionStore.getCount()
    }

    private fun decorateTransactions(transactions: List<Transaction>) {
        ColorByAmountTransactionsDecorator()
            .decorate(transactions)
        SumByDayTransactionsDecorator()
            .decorate(transactions)
        HideInternalTransactionsDecorator()
            .decorate(transactions)
    }

    companion object {
        private const val CLASS_TRANSACTION_LIST: String = "_3dF-vAnsH8IQUMWJLJaGUS"
    }
}