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

                val expensesSum = it.filter { it.amount!! < 0 && !it.isInternal }
                    .sumByDouble { it.amount!! }

                prependSummary(firstRow, it[0].createdAt!!, expensesSum)
            }
    }

    private fun prependSummary(element: Element, date: Date, expensesSum: Double) {
        val dateString = "${DateUtils.pad(date.getDate())}.${DateUtils.pad(date.getMonth())}.${date.getFullYear()}"
        val dayOfWeekString = "${date.getDay()}"

        val expensesSumString = roundDouble(expensesSum)

        val summary = document.createElement("tr")
        summary.innerHTML =
                "<tr class=\"_3oP6Df9GeSAJbaINFq_mUP sum\">" +
                    "<td class=\"_15uT9ZzfHxEXJFUyScU42U\">" +
                        "<div class=\"_3MSDLfycBJ4T8peBfFPsAB\" style=\"text-align: center;\">" +
                            "<span aria-labelledby=\"tooltip-83\"></span>" +
                        "</div>" +
                    "</td>" +
                    "<td class=\"_2zsEj9F3790nueN4uWbmh3 _2DLWbTUxedXJ-cPkZvUwDu UX5dED1uU_XhOZZD36O_L\">" +
                        "<span class=\"date\">$dateString</span>" +
                    "</td>" +
                    "<td class=\"_2zsEj9F3790nueN4uWbmh3 _2DLWbTUxedXJ-cPkZvUwDu _2uJi_biBLR3c3Qjv80NTGA\"></td>" +
                    "<td class=\"_2zsEj9F3790nueN4uWbmh3 _2DLWbTUxedXJ-cPkZvUwDu _3v9fRQuC-jvEBUH7gaMNVT\">" +
                        "<div class=\"dayOfWeek\">$dayOfWeekString</div>" +
                    "</td>" +
                    "<td class=\"_2zsEj9F3790nueN4uWbmh3 _2DLWbTUxedXJ-cPkZvUwDu\">" +
                    "<div class=\"_2BmwgwApNCwFZNlXU384dS\"></div>" +
                    "</td>" +
                    "<td class=\"_2zsEj9F3790nueN4uWbmh3 _2DLWbTUxedXJ-cPkZvUwDu\">" +
                        "<div class=\"_3v8JAx5zYMTOD9r_-zh2Zh\">" +
                            "<div class=\"_3s_V00e3u2GBhaObWGJ4UH\">" +
                                "<span></span>" +
                            "</div>" +
                        "</div>" +
                    "</td>" +
                    "<td class=\"expenses\">" +
                        "<span>$expensesSumString</span>" +
                    "</td>" +
                "</tr>"

        summary.setAttribute("class", "sum")

        element.parentNode?.insertBefore(summary, element)
    }

    private fun roundDouble(expensesSum: Double) = round(expensesSum * 100) / 100
}