package org.echo.mobile.ping

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import org.echo.mobile.ping.Constants.Companion.BC_PERCENT
import org.echo.mobile.ping.Constants.Companion.FC_PERCENT
import org.echo.mobile.ping.Constants.Companion.LINE_WIDTH_PERCENT
import org.echo.mobile.ping.Constants.Companion.MC_PERCENT
import org.echo.mobile.ping.Constants.Companion.SC_PERCENT
import org.echo.mobile.ping.Helper.getPseudoRandom

/**
 * [Ping] for drawing image called Ping by [String] using canvas
 *
 * @author Andrey Chembrovich
 */
class Ping(accountName: String, size: Float) {
    private val userNumbers: NameNumbers = NameNumbers(accountName)
    private val iconSize = size
    private val color = IconColors(userNumbers)

    private val circleLineWidth = iconSize * LINE_WIDTH_PERCENT
    private val smallCircleRadius = iconSize * SC_PERCENT / 2
    private val smallCircleCenter: PointF

    private val mediumCircleRadius = iconSize * MC_PERCENT / 2
    private var mediumCircleCenter: PointF = PointF(0f, 0f)
    private var mediumCircleAngle: Double = 0.0

    private val bigCircleRadius = iconSize * BC_PERCENT / 2
    private var bigCircleCenter: PointF = PointF(0f, 0f)
    private var bigCircleAngle: Double = 0.0

    private val fadeCircleRadius = iconSize * FC_PERCENT / 2
    private var fadeCircleCenter: PointF = PointF(0f, 0f)
    private var fadeCircleAngle: Double = 0.0

    init {
        smallCircleCenter = initSmallCircleCenter()
        initMediumCircleCenter()
        initBigCircleCenter()
        initFadeCircleCenter()
    }

    private fun initSmallCircleCenter(): PointF {
        return PointF(
            getPseudoRandom(iconSize * 0.1, iconSize * 0.9, userNumbers.angleFactors[0]).toFloat(),
            getPseudoRandom(iconSize * 0.1, iconSize * 0.9, userNumbers.angleFactors[0]).toFloat()
        )
    }

    private fun initMediumCircleCenter() {
        val offset = mediumCircleRadius - smallCircleRadius
        val range = getAngleRange(iconSize, offset * 2, smallCircleCenter)
        mediumCircleAngle = getPseudoRandom(range[0], range[1], userNumbers.angleFactors[1])
        mediumCircleCenter = PointF(
            (Math.cos(mediumCircleAngle) * offset + smallCircleCenter.x).toFloat(),
            (Math.sin(mediumCircleAngle) * offset + smallCircleCenter.y).toFloat()
        )
    }

    private fun initBigCircleCenter() {
        val offset = bigCircleRadius - smallCircleRadius
        val range = getAngleRange(iconSize, offset, smallCircleCenter)
        bigCircleAngle = getPseudoRandom(range[0], range[1], userNumbers.angleFactors[2])

        val angleDiffValue = if (mediumCircleAngle > bigCircleAngle) {
            mediumCircleAngle - bigCircleAngle
        } else {
            bigCircleAngle - mediumCircleAngle
        }

        var angleDiff = Math.abs(angleDiffValue)

        if (angleDiff > Math.PI) {
            angleDiff = Math.PI * 2 - angleDiff
        }

        if (angleDiff < Math.PI / 4) {
            val needAdd = Math.PI / 4 - angleDiff
            if (bigCircleAngle > mediumCircleAngle) {
                bigCircleAngle += needAdd
            } else {
                bigCircleAngle -= needAdd
            }
        }

        bigCircleCenter = PointF(
            (Math.cos(bigCircleAngle) * offset + smallCircleCenter.x).toFloat(),
            (Math.sin(bigCircleAngle) * offset + smallCircleCenter.y).toFloat()
        )
    }

    private fun initFadeCircleCenter() {
        val offset = fadeCircleRadius - smallCircleRadius
        val range = getAngleRange(iconSize, offset * 0.7, smallCircleCenter)
        fadeCircleAngle = getPseudoRandom(range[0], range[1], userNumbers.angleFactors[3])

        fadeCircleCenter = PointF(
            (Math.cos(fadeCircleAngle) * offset + smallCircleCenter.x).toFloat(),
            (Math.sin(fadeCircleAngle) * offset + smallCircleCenter.y).toFloat()
        )
    }

    private fun getAngleRange(squareWidth: Float, r: Double, center: PointF): Array<Double> {
        val result: Array<Double> = arrayOf(0.0, 4 * Math.PI)

        if (squareWidth - center.x < r) {
            val alpha = Math.acos((squareWidth - center.x) / r)
            result[0] = alpha
            result[1] = Math.PI * 2 - alpha
        }

        if (squareWidth - center.y < r) {
            val alpha = Math.acos((squareWidth - center.y) / r)
            result[0] = Math.max(result[0], Math.PI / 2 + alpha)
            result[1] = Math.min(result[1], 5 * Math.PI / 2 - alpha)
        }

        if (center.x < r) {
            val alpha = Math.acos(center.x / r)
            result[0] = Math.max(Math.PI + alpha, result[0])
            result[1] = Math.min(3 * Math.PI - alpha, result[1])
        }

        if (center.y < r) {
            val alpha = Math.acos(center.y / r)
            if (squareWidth - center.x >= r) {
                result[0] = Math.max(Math.PI * 3 / 2 + alpha, result[0])
            }
            if (result[1] < Math.PI * 2) result[1] += Math.PI * 2
            result[1] = Math.min(Math.PI * 7 / 2 - alpha, result[1])
        }
        if (result[0] < result[1] - Math.PI * 2) result[1] -= Math.PI * 2
        return result
    }

    /**
     * Draw Ping image on canvas and return [Bitmap]
     */
    fun draw(): Bitmap {
        val bitmap =
            Bitmap.createBitmap(iconSize.toInt(), iconSize.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        path.addCircle(iconSize / 2, iconSize / 2, iconSize / 2, Path.Direction.CW)

        canvas.clipPath(path)

        val paint = Paint()
        paint.flags = Paint.ANTI_ALIAS_FLAG

        paint.color = color.rgb
        paint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawRect(0f, 0f, iconSize, iconSize, paint)

        paint.color = color.lineRGB
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleLineWidth.toFloat()
        canvas.drawCircle(
            smallCircleCenter.x,
            smallCircleCenter.y,
            smallCircleRadius.toFloat(),
            paint
        )

        canvas.drawCircle(
            mediumCircleCenter.x,
            mediumCircleCenter.y,
            mediumCircleRadius.toFloat(),
            paint
        )

        paint.color = color.lineRGB
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = circleLineWidth.toFloat()
        paint.alpha = (0.7 * 255).toInt()
        canvas.drawCircle(fadeCircleCenter.x, fadeCircleCenter.y, fadeCircleRadius.toFloat(), paint)

        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleLineWidth.toFloat()
        paint.alpha = (0.7 * 255).toInt()
        canvas.drawCircle(bigCircleCenter.x, bigCircleCenter.y, bigCircleRadius.toFloat(), paint)

        return bitmap
    }
}