package org.echo.mobile.ping

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import kotlin.math.min

/**
 * [ImageView] with colored avatars
 *
 * @author Andrey Chembrovich
 */
class ColoredAvatarView : ImageView {

    private var avatarValue: String? = null

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        setWillNotDraw(false)

        val a = context.obtainStyledAttributes(attrs, R.styleable.ColoredAvatarView)

        avatarValue = a.getString(R.styleable.ColoredAvatarView_avatarValue)

        a.recycle()
    }

    /**
     * Updates [avatarValue] value for avatar.
     * Invalidates view after changes applying.
     */
    fun setAvatarValue(avatarValue: String?) {
        this.avatarValue = avatarValue
        requestLayout()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        setAvatarValue(null)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        avatarValue?.let {
            if (it.isNotBlank()) generateAvatar(it)
        }
    }

    private fun generateAvatar(avatarValue: String) {
        val size = min(width, height).toFloat()
        setImageBitmap(Ping(avatarValue, size).draw())
    }
}
