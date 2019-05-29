package org.echo.mobile.ping

import kotlin.math.round

/**
 * Helper that is used for calculation and generation colors and circles positions for [Ping] image
 *
 * @author Andrey Chembrovich
 */
object Helper {

    /**
     * Shuffles [arr] using [shuffleArrayFactor]
     */
    fun shuffleArray(arr: Array<Char>, shuffleArrayFactor: Array<Int>): Array<Char> {
        return arr.mapIndexed { index, _ ->
            arr[shuffleArrayFactor[index]]
        }.toTypedArray()
    }

    /**
     * Generate pseudo random [Double] from [factor] in Integer range from [min] to [max]
     */
    fun getPseudoRandom(min: Int, max: Int, factor: Double): Double {
        return factor / 0xffff * (max - min) + min
    }

    /**
     * Generate pseudo random [Double] from [factor] in Double range from [min] to [max]
     */
    fun getPseudoRandom(min: Double, max: Double, factor: Double): Double {
        return factor / 0xffff * (max - min) + min
    }

    /**
     * Generate pseudo random [Int] from [factor] in Integer range from [min] to [max]
     */
    fun getPseudoRandomInt(min: Int, max: Int, factor: Double): Int {
        return round(getPseudoRandom(min, max, factor)).toInt()
    }
}