package transactions.decorators

import transactions.Transaction

class HideInternalTransactionsDecorator : TransactionsDecorator {
    override fun decorate(transactions: List<Transaction>) {
        transactions.filter { it.isInternal }
            .map {
                it.transactionPresenter
                    ?.element
                    ?.setAttribute("style", "display: none;")
            }
    }
}