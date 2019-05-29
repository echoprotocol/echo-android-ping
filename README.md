# About
Ping is the avatar generator for Echo Blockchain accounts by username.

Each Echo Blockchain account has its own unique avatar. Ping will help you get it.

# Usage

```kotlin
val imageView = viewGroup.findViewById<ColoredAvatarView>(R.id.avatarView)
imageView.setAvatarValue("testValue")
```

where `pixelplex` - an Echo account name, and `100` - an avatar size.

And as result, you will receive the image with your unique avatar

As example:

![PixelPlex avatar](pixelplex-avatar.png)

# Contributing and License
Distributed under the MIT license. See LICENSE for more information.
