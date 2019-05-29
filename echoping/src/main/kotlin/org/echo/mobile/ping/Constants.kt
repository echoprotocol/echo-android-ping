package org.echo.mobile.ping

/**
 * Holds constants for [Ping] image generation
 *
 * @author Andrey Chembrovich
 */
class Constants {
    companion object {
        const val LINE_WIDTH_PERCENT = 0.07
        const val SC_PERCENT = 0.4
        const val MC_PERCENT = 0.9
        const val BC_PERCENT = 1.2
        const val FC_PERCENT = 1.1
    }
}

/**
 * Holds color constants for [Ping] image generation for [DARK] and [LIGHT] types
 */
enum class ColorConfigTypes(
    val BASE_COLOR_INTERVAL_MIN: Int,
    val BASE_COLOR_INTERVAL_MAX: Int,
    val BASE_COLOR_SECOND_OFFSET_MIN: Int,
    val BASE_COLOR_SECOND_OFFSET_MAX: Int,
    val BASE_COLOR_THIRD_OFFSET_MIN: Int,
    val SECOND_COLOR_OFFSET: Int
) {
    DARK(
        50,
        120,
        40,
        100,
        100,
        70
    ),
    LIGHT(
        180,
        255,
        60,
        100,
        100,
        -70
    )
}