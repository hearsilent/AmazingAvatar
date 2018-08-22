# AmazingAvatar
[![GitHub release](https://img.shields.io/github/release/hearsilent/AmazingAvatar.svg?maxAge=2592000)](https://github.com/hearsilent/AmazingAvatar)
[![license](https://img.shields.io/github/license/hearsilent/AmazingAvatar.svg?maxAge=2592000)](https://github.com/hearsilent/AmazingAvatar/blob/master/LICENSE)

An android amazing avatar anim in CollapsingToolbarLayout.

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
<android.support.design.widget.CollapsingToolbarLayout
    android:layout_width="match_parent"
    android:layout_height="224dp"
    app:contentScrim="@android:color/transparent"
    app:layout_scrollFlags="scroll|exitUntilCollapsed"
    app:statusBarScrim="@android:color/transparent"
    app:titleEnabled="false">

    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        app:layout_collapseMode="pin"
        app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:gravity="center_vertical"
        app:layout_collapseMode="parallax"
        app:layout_collapseParallaxMultiplier="1">

        <android.support.v4.widget.Space
            android:id="@+id/space"
            android:layout_width="32dp"
            android:layout_height="32dp"
            android:layout_marginLeft="32dp"/>

        <TextView
            android:id="@+id/toolbar_title"
            style="@style/TextAppearance.Widget.AppCompat.Toolbar.Title"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:gravity="center_vertical"/>
    </LinearLayout>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_collapseMode="parallax"
        app:layout_collapseParallaxMultiplier="1">

        <de.hdodenhof.circleimageview.CircleImageView
            android:id="@+id/imageView_avatar"
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="?attr/actionBarSize"
            android:background="@drawable/avatar_background"
            android:contentDescription="@null"
            android:padding="1dp"/>

        <TextView
            android:id="@+id/textView_title"
            android:layout_width="wrap_content"
            android:layout_height="?attr/actionBarSize"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="130dp"
            android:gravity="center"
            android:maxLines="1"
            android:text="HearSilent"
            android:textColor="#FFF"
            android:textSize="24sp"/>
    </FrameLayout>
</android.support.design.widget.CollapsingToolbarLayout>
```

## Compatibility

Android GINGERBREAD 4.2+

## Credits

Avatars from [maximedegreve/TinyFaces](https://github.com/maximedegreve/TinyFaces).

## Let me know!

I'd be really happy if you sent me links to your projects where you use my component. Just send an email to hear.silent1995@gmail.com And do let me know if you have any questions or suggestion regarding the example. 

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
