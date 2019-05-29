package org.echo.mobile.ping

import android.graphics.Color
import org.echo.mobile.ping.Helper.getPseudoRandomInt
import org.echo.mobile.ping.Helper.shuffleArray
import kotlin.math.max

/**
 * [IconColors] for generating and holding stroke and inner colors of [Ping] image
 *
 * @author Andrey Chembrovich
 */
class IconColors(nameNumbers: NameNumbers) {
    private val base: MutableMap<Char, Int> = mutableMapOf('r' to 0, 'g' to 0, 'b' to 0)
    private val lines: MutableMap<Char, Int> = mutableMapOf('r' to 0, 'g' to 0, 'b' to 0)
    private val config: ColorConfigTypes = if (nameNumbers.themeFactor == 0) {
        ColorConfigTypes.DARK
    } else {
        ColorConfigTypes.LIGHT
    }

    val rgb: Int
        get() = Color.rgb(
            base['r'].getValueOrZero(),
            base['g'].getValueOrZero(),
            base['b'].getValueOrZero()
        )

    val lineRGB: Int
        get() = Color.rgb(
            lines['r'].getValueOrZero(),
            lines['g'].getValueOrZero(),
            lines['b'].getValueOrZero()
        )

    init {
        generateMainColors(nameNumbers)
    }

    private fun generateMainColors(nameNumbers: NameNumbers) {
        val colors = shuffleArray(arrayOf('r', 'g', 'b'), nameNumbers.shuffleArrayFactor)

        base[colors[0]] = getPseudoRandomInt(
            config.BASE_COLOR_INTERVAL_MIN,
            config.BASE_COLOR_INTERVAL_MAX,
            nameNumbers.colorFactors[0]
        )

        val secondColorOffset = getPseudoRandomInt(
            config.BASE_COLOR_SECOND_OFFSET_MIN,
            config.BASE_COLOR_SECOND_OFFSET_MAX,
            nameNumbers.colorFactors[1]
        )
        base[colors[1]] = max(0, base[colors[0]].getValueOrZero() - secondColorOffset)

        val thirdColorOffset = getPseudoRandomInt(
            0,
            base[colors[0]].getValueOrZero() - config.BASE_COLOR_THIRD_OFFSET_MIN,
            nameNumbers.colorFactors[2]
        )
        base[colors[2]] = Math.max(0, thirdColorOffset)

        lines[colors[0]] = Math.max(0, base[colors[0]].getValueOrZero() + config.SECOND_COLOR_OFFSET)
        lines[colors[1]] = Math.max(0, base[colors[1]].getValueOrZero() + config.SECOND_COLOR_OFFSET)
        lines[colors[2]] = Math.max(0, base[colors[2]].getValueOrZero() + config.SECOND_COLOR_OFFSET)
    }

    private fun Int?.getValueOrZero() = this ?: 0
}