package transactions.decorators;

import transactions.Transaction
import kotlin.math.abs
import kotlin.math.log

class ColorByAmountTransactionsDecorator : TransactionsDecorator {
    override fun decorate(transactions: List<Transaction>) {
        val hugeTransaction = 500

        for (it in transactions.iterator()) {
            if (it.amount!! < 0) {
                val relativeAmount = abs(it.amount!! / hugeTransaction)

                val amountField = it.transactionPresenter?.getAmountField()
                amountField?.setAttribute(
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
        val hue = maxHue - logScale * log(100 * relativeAmount, 2.0)

        return "hsl($hue,100%,90%)"
    }
}