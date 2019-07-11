package transactions.decorators

import transactions.Transaction

class SplitByDaysDecorator : TransactionsDecorator {
    override fun decorate(transactions: List<Transaction>) {
        transactions.groupBy { it.createdAt!!.getDate() }
            .values
            .map { it ->
                it.sortedBy { it.transactionPresenter!!.index }[0]
                    .transactionPresenter!!
                    .setAttribute("style", "border-top: solid 2px #666")
            }
    }
}