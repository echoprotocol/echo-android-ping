package org.echo.mobile.ping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Example of using [ColoredAvatarView]
 *
 * @author Andrey Chembrovich
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ColoredAvatarView>(R.id.avatarView).setAvatarValue("testValue")
    }
}
