package transactions.decorators

import org.w3c.dom.Element
import transactions.Transaction
import utils.DateUtils
import kotlin.browser.document
import kotlin.js.Date
import kotlin.math.round

class SumByDayTransactionsDecorator : TransactionsDecorator {
    override fun decorate(transactions: List<Transaction>) {
        transactions.groupBy { it.createdAt!!.getDate() }
            .values
            .map { it ->
                val firstRow = it.sortedBy { it.transactionPresenter!!.index }[0]
                    .transactionPresenter!!
                    .element

                val expensesSum = it.filter { it.amount!! < 0 }
                    .sumByDouble { it.amount!! }

                prependSummary(firstRow, it[0].createdAt!!, expensesSum)
            }
    }

    private fun prependSummary(element: Element, date: Date, expensesSum: Double) {
        val dateString = "${DateUtils.pad(date.getDate())}.${DateUtils.pad(date.getMonth())}.${date.getFullYear()}"

        val expensesSumString = roundDouble(expensesSum)

        val summary = document.createElement("li")
        summary.innerHTML =
                "<li class=\"content-list-row collapsed sum\" data-category-id=\"14\" data-currency=\"PLN\">\n" +
                "    <header class=\"content-list-row-header\">\n" +
                "        <div class=\"column type\">\n" +
                "        </div>\n" +
                "        <div class=\"column date\" data-order-number=\"14\">$dateString</div>\n" +
                "        <div class=\"column description-additional-info\"></div>\n" +
                "        <div class=\"column description\">\n" +
                "            <span class=\"wrapper\">\n" +
                "                <span class=\"label\">$expensesSumString</span>\n" +
                "            </span>\n" +
                "        </div>" +
                "        <div class=\"column category-additional-info\">\n" +
                "        </div>\n" +
                "        <div class=\"column category\">\n" +
                "            <div class=\"multi-select categoryTree\" data-select-id=\"14\" data-irrelevant=\"false\">\n" +
                "                <span class=\"text\">$expensesSumString</span>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"column actions\">\n" +
                "            <ul>\n</ul>\n" +
                "        </div>\n" +
                "        <div class=\"column amount\">\n" +
                "            <strong class=\"negative\">\n" +
                "                $expensesSumString\n" +
                "            </strong>PLN\n" +
                "        </div>\n" +
                "    </header>\n" +
                "\n" +
                "</li>"

        element.parentNode?.insertBefore(summary, element)
    }

    private fun roundDouble(expensesSum: Double) = round(expensesSum * 100) / 100
}