package transactions

import kotlin.js.Date

class Transaction {
    var amount: Double? = null
    var createdAt: Date? = null
    var transactionPresenter: TransactionPresenter? = null

    companion object {
        fun from(transactionPresenter: TransactionPresenter): Transaction {
            val transaction = Transaction()
            transaction.transactionPresenter = transactionPresenter
            transaction.amount = getAmountFrom(transactionPresenter)
            transaction.createdAt = getCreatedAtFrom(transactionPresenter)

            return transaction
        }

        private fun getAmountFrom(transactionPresenter: TransactionPresenter): Double? {
            return transactionPresenter
                .getAttribute("data-amount")!!
                .replace(",", ".")
                .toDouble()
        }

        private fun getCreatedAtFrom(transactionPresenter: TransactionPresenter): Date? {
            val dateText = transactionPresenter.getDateFromTextField()
            val splitDate = dateText.split(".")

            val day = splitDate[0].toInt()
            val month = splitDate[1].toInt()
            val year = splitDate[2].toInt()

            return Date(year, month, day)
        }

    }
}