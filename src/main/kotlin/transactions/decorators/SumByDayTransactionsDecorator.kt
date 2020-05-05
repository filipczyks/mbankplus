package transactions.decorators

import org.w3c.dom.Element
import transactions.Transaction
import utils.DateUtils
import kotlin.browser.document
import kotlin.js.Date
import kotlin.math.round

class SumByDayTransactionsDecorator : TransactionsDecorator {
    override fun decorate(transactions: List<Transaction>) {
        val groupedByDay = transactions.filter { !it.hasSummary }
            .groupBy { it.createdAt!!.getDate() * it.createdAt!!.getMonth() }
            .values

        val lastDayTransactions = groupedByDay.last()

        groupedByDay.filter { it -> it != lastDayTransactions }
            .map { ts ->
                val firstRow = ts.sortedBy { it.transactionPresenter!!.index }[0]
                    .transactionPresenter!!
                    .element

                val expensesSum = ts.filter { it.amount!! < 0 && !it.isInternal }
                    .sumByDouble { it.amount!! }

                prependSummary(firstRow, ts[0].createdAt!!, expensesSum)

                ts.forEach { it -> it.hasSummary = true }
            }
    }

    private fun prependSummary(element: Element, date: Date, expensesSum: Double) {
        val dateString = "${DateUtils.pad(date.getDate())}.${DateUtils.pad(date.getMonth())}.${date.getFullYear()}"
        val dayOfWeekString = "${date.getDay()}"

        console.log("prepending summary $dateString / $expensesSum")

        val expensesSumString = roundDouble(expensesSum)

        val summary = document.createElement("tr")
        summary.innerHTML =
                "<tr class=\"_3oP6Df9GeSAJbaINFq_mUP\">" +
                    "<td></td>" +
                    "<td>$dateString</td>" +
                    "<td>$dayOfWeekString</td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td class=\"expenses\">" +
                        "<span>$expensesSumString</span>" +
                    "</td>" +
                "</tr>"

        summary.setAttribute("class", "daySummary")

        element.parentNode?.insertBefore(summary, element)
    }

    private fun roundDouble(expensesSum: Double) = round(expensesSum * 100) / 100
}