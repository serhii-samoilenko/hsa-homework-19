package com.example.util

import java.io.File

class Report(
    private val fileName: String,
) {
    private val targetDir = "reports"
    private var data: String = ""

    @Synchronized
    fun text(text: String) {
        "$text\n".also { data += it + "\n" }.also { println(it) }
    }

    @Synchronized
    fun line(text: String) {
        text.also { data += it + "\n" }.also { println(it) }
    }

    fun h1(text: String) {
        text("# $text")
    }

    fun h2(text: String) {
        text("## $text")
    }

    fun h3(text: String) {
        text("### $text")
    }

    fun h4(text: String) {
        text("#### $text")
    }

    fun code(text: String) {
        text("`$text`")
    }

    fun line() {
        text("---")
    }

    fun htmlTable(legend: String, data: LinkedHashMap<String, LinkedHashMap<String, Double>>) {
        text(htmlTableString(legend, data))
    }

    private fun htmlTableString(legend: String, data: LinkedHashMap<String, LinkedHashMap<String, Double>>): String {
        val sb = StringBuilder()
        sb.append("<table>\n")
        // Get a list of unique inner keys (column headers)
        val columnHeaders = data.values.flatMap { it.keys }.toSet()
        // Generate the first row with header column
        sb.append("  <tr>\n")
        sb.append("    <th><i>$legend</i></th>\n") // Legend at the beginning of the header row
        columnHeaders.forEach { columnHeader ->
            val header = columnHeader.split(" ").joinToString("<br/>") // Minimize width
            sb.append("    <th>$header</th>\n")
        }
        sb.append("  </tr>\n")
        // Generate the rest of the rows
        data.keys.forEach { rowKey ->
            sb.append("  <tr>\n")
            sb.append("    <th>$rowKey</th>\n") // Header cell in the first column
            columnHeaders.forEach { columnHeader ->
                val count = data[rowKey]?.get(columnHeader)?.let { "%.5f".format(it) } ?: ""
                sb.append("    <td>$count</td>\n")
            }
            sb.append("  </tr>\n")
        }
        sb.append("</table>")
        return sb.toString()
    }

    @Synchronized
    fun writeToFile() {
        File(targetDir).mkdirs()
        val file = File(targetDir, fileName)
        file.writeText(data)
        println("Wrote report to ${file.absolutePath}")
    }

    @Synchronized
    fun clear() {
        data = ""
    }
}
