package transactions.decorators

import org.w3c.dom.Element
import transactions.Transaction
import utils.DateUtils
import kotlin.js.Date

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
                insertSummary(firstRow, it[0].createdAt!!, expensesSum)
            }
    }

    private fun insertSummary(element: Element, date: Date, expensesSum: Double) {
        val dateString = "${DateUtils.pad(date.getDate())}.${DateUtils.pad(date.getMonth())}.${date.getFullYear()}"

        element.innerHTML =
                "<li class=\"content-list-row collapsed sum\" data-category-id=\"14\" data-currency=\"PLN\">\n" +
                "    <header class=\"content-list-row-header\">\n" +
                "        <div class=\"column type\">\n" +
                "        </div>\n" +
                "        <div class=\"column date\" data-order-number=\"14\">$dateString</div>\n" +
                "        <div class=\"column description-additional-info\"></div>\n" +
                "        <div class=\"column description\">\n" +
                "            <span class=\"wrapper\">\n" +
                "                <span class=\"label\">$expensesSum</span>\n" +
                "            </span>\n" +
                "        </div>" +
                "        <div class=\"column category-additional-info\">\n" +
                "        </div>\n" +
                "        <div class=\"column category\">\n" +
                "            <div class=\"multi-select categoryTree\" data-select-id=\"14\" data-irrelevant=\"false\">\n" +
                "                <span class=\"text\">$expensesSum</span>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"column actions\">\n" +
                "            <ul>\n" +
                "                            </ul>\n" +
                "        </div>\n" +
                "        <div class=\"column amount\">\n" +
                "            <strong class=\"negative\">\n" +
                "                $expensesSum\n" +
                "            </strong>PLN\n" +
                "        </div>\n" +
                "    </header>\n" +
                "\n" +
                "</li>"
    }
}