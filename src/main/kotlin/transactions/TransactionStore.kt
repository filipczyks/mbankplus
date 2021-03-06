package transactions

import kotlin.math.abs
import kotlin.math.absoluteValue

class TransactionStore {
    private var transactions = mutableListOf<Transaction>()

    fun getCount(): Int {
        return transactions.count()
    }

    fun getGreatestTransactionAmount(): Double? {
        return transactions.maxBy { abs(it.amount!!) }!!.amount!!.absoluteValue
    }

    fun findAll(): List<Transaction> {
        return transactions
    }

    fun replaceAll(transactions: List<Transaction>) {
        this.transactions.clear()
        this.transactions.addAll(transactions)
    }

    fun addAll(delta: List<Transaction>) {
        this.transactions.addAll(delta)
    }
}