package transactions

import kotlin.js.Date

class Transaction {
    var amount: Double? = null
    var createdAt: Date? = null
    var isInternal: Boolean = false
    var transactionPresenter: TransactionPresenter? = null

    companion object {
        fun from(transactionPresenter: TransactionPresenter): Transaction {
            val transaction = Transaction()
            transaction.transactionPresenter = transactionPresenter
            transaction.amount = getAmountFrom(transactionPresenter)
            transaction.createdAt = getCreatedAtFrom(transactionPresenter)
            transaction.isInternal = getIsInternalFrom(transactionPresenter)

            return transaction
        }

        private fun getAmountFrom(transactionPresenter: TransactionPresenter): Double? {
            return transactionPresenter
                .getAttribute("data-amount")
                ?.replace(",", ".")
                ?.toDouble()
        }

        private fun getCreatedAtFrom(transactionPresenter: TransactionPresenter): Date? {
            val dateText = transactionPresenter.getDateField()?.textContent!!
            val splitDate = dateText.split(".")

            val day = splitDate[0].toInt()
            val month = splitDate[1].toInt()
            val year = splitDate[2].toInt()

            return Date(year, month, day)
        }

        private fun getIsInternalFrom(transactionPresenter: TransactionPresenter): Boolean {
            val classes = transactionPresenter.getAttribute("class")
                ?.split(" ")!!

            return classes.find { it == "disabled" } != null
        }
    }
}