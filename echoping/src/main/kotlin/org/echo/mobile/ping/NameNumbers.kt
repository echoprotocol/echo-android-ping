package org.echo.mobile.ping

import java.security.MessageDigest
import java.util.*

/**
 * [NameNumbers] generate hash value from [String] that is used
 * for generating data for [Ping] image
 *
 * @author Andrey Chembrovich
 */
class NameNumbers(name: String) {
    val hash: String
    val themeFactor: Int
    val shuffleArrayFactor: Array<Int>
    val colorFactors: Array<Double>
    val angleFactors: Array<Double>

    init {
        hash = name.toSHA1()
        themeFactor = if (hash.substring(0, 4).parseInt(16) > 0xffff / 2) 1 else 0

        shuffleArrayFactor = arrayOf(0, 1, 2).mapIndexed { index, _ ->
            arrayOf(index, hash.substring(4 + 2 * index, 6 + 2 * index).parseInt(16))
        }.sortedBy { it[1] }.map { arrayList ->
            arrayList[0]
        }.toTypedArray()

        colorFactors = arrayOf(
            hash.substring(10, 14).parseInt(16).toDouble(),
            hash.substring(14, 18).parseInt(16).toDouble(),
            hash.substring(18, 22).parseInt(16).toDouble()
        )

        angleFactors = arrayOf(
            hash.substring(22, 26).parseInt(16).toDouble(),
            hash.substring(26, 30).parseInt(16).toDouble(),
            hash.substring(30, 34).parseInt(16).toDouble(),
            hash.substring(34, 38).parseInt(16).toDouble()
        )
    }

    private fun String.toSHA1(): String {
        val md = MessageDigest.getInstance("SHA-1")
        val bytes = md.digest(this.toByteArray())

        val buffer = StringBuilder()
        for (b in bytes) {
            buffer.append(String.format(Locale.getDefault(), "%02x", b))
        }
        return buffer.toString()
    }

    private fun String.parseInt(radix: Int = 10): Int {
        return toIntOrNull(radix) ?: 0
    }
}