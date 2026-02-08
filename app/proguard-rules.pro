# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Add this option to tell ProGuard to keep and adapt Kotlin metadata
-keep class kotlin.Metadata

# Crashlytics
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

# Rename packages
-optimizationpasses 30
-mergeinterfacesaggressively
-dontpreverify
-optimizations !code/simplification/arithmetic

-repackageclasses hearsilent.amazingavatar
-allowaccessmodification
-keeppackagenames doNotKeepAThing

# Remove logs
-assumenosideeffects class android.util.Log {
    v(...);
    d(...);
    i(...);
    w(...);
    e(...);
    wtf(...);
    println(...);
}