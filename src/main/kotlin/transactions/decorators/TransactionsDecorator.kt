package transactions.decorators

import transactions.Transaction

interface TransactionsDecorator {
    fun decorate(transactions: List<Transaction>)
}