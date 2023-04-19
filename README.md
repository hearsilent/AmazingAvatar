# AmazingAvatar

[![GitHub release](https://img.shields.io/github/release/hearsilent/AmazingAvatar.svg?maxAge=2592000)](https://github.com/hearsilent/AmazingAvatar)
[![license](https://img.shields.io/github/license/hearsilent/AmazingAvatar.svg?maxAge=2592000)](https://github.com/hearsilent/AmazingAvatar/blob/master/LICENSE)

An android amazing avatar anim in CollapsingToolbarLayout with MotionLayout.

## Screenshot

<img src="https://raw.githubusercontent.com/hearsilent/AmazingAvatar/master/screenshots/screenrecord.gif" height="500">

### Expanded

<img src="https://raw.githubusercontent.com/hearsilent/AmazingAvatar/master/screenshots/device-2018-06-12-232525_framed.png" height="500">

### Collapsed

<img src="https://raw.githubusercontent.com/hearsilent/AmazingAvatar/master/screenshots/device-2018-06-12-232532_framed.png" height="500">

## Usage

*For a working implementation, please have a look at the Sample Project - sample*

<a href='https://play.google.com/store/apps/details?id=hearsilent.amazingavatar&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'  width="155" height="60"/></a>

Just Clone and Build.

## Customization

You can change header view to what you want.

```xml

<com.google.android.material.appbar.CollapsingToolbarLayout android:layout_width="match_parent"
	android:layout_height="match_parent" app:contentScrim="@android:color/transparent"
	app:layout_scrollFlags="scroll|exitUntilCollapsed"
	app:statusBarScrim="@android:color/transparent" app:titleEnabled="false">

	<androidx.appcompat.widget.Toolbar android:id="@+id/toolbar" android:layout_width="match_parent"
		android:layout_height="?android:attr/actionBarSize" app:layout_collapseMode="pin"
		app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>

	<androidx.constraintlayout.motion.widget.MotionLayout android:id="@+id/motion_header"
		android:layout_width="match_parent" android:layout_height="match_parent"
		android:clipChildren="false" android:clipToPadding="false" android:paddingStart="48dp"
		android:paddingEnd="48dp" app:layoutDescription="@xml/scene_header"
		app:layout_collapseMode="parallax" app:layout_collapseParallaxMultiplier="1">

		<Space android:id="@+id/space_toolbar" android:layout_width="0dp"
			android:layout_height="?android:attr/actionBarSize"
			app:layout_constraintEnd_toEndOf="parent" app:layout_constraintStart_toStartOf="parent"
			app:layout_constraintTop_toTopOf="parent"/>

		<TextView android:id="@+id/textView_title_shadow" android:layout_width="wrap_content"
			android:layout_height="wrap_content" android:layout_marginTop="2dp"
			android:ellipsize="end" android:maxLines="1" android:text="HearSilent"
			android:textSize="24sp" android:visibility="invisible"
			app:layout_constraintEnd_toEndOf="parent" app:layout_constraintStart_toStartOf="parent"
			app:layout_constraintTop_toBottomOf="@+id/guideline" tools:ignore="HardcodedText"/>

		<TextView android:id="@+id/textView_title" android:layout_width="wrap_content"
			android:layout_height="wrap_content" android:ellipsize="end"
			android:gravity="center_vertical|start" android:maxLines="1" android:text="HearSilent"
			android:textColor="#FFF" android:textSize="24sp" tools:ignore="HardcodedText"/>

		<androidx.constraintlayout.widget.Guideline android:id="@+id/guideline"
			android:layout_width="wrap_content" android:layout_height="wrap_content"
			android:orientation="horizontal" app:layout_constraintGuide_percent="0.6"/>

		<com.google.android.material.imageview.ShapeableImageView android:id="@+id/imageView_avatar"
			android:layout_width="80dp" android:layout_height="80dp"
			android:layout_marginBottom="2dp" android:background="@drawable/avatar_background"
			android:padding="1dp" app:layout_constraintBottom_toTopOf="@+id/guideline"
			app:layout_constraintEnd_toEndOf="parent" app:layout_constraintStart_toStartOf="parent"
			app:shapeAppearanceOverlay="@style/CircleShape"/>
	</androidx.constraintlayout.motion.widget.MotionLayout>
</com.google.android.material.appbar.CollapsingToolbarLayout>
```

## Compatibility

Android LOLLIPOP 5.0+

## Credits

Avatars from [maximedegreve/TinyFaces](https://github.com/maximedegreve/TinyFaces).

## Let me know!

I'd be really happy if you sent me links to your projects where you use my component. Just send an
email to hear.silent1995+github@gmail.com And do let me know if you have any questions or suggestion
regarding the example.

## License

    MIT License

    Copyright (c) 2017 HearSilent

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
