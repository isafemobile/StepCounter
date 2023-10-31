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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Rules for MVVM
-keep class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
-keep class android.arch.lifecycle.** { *; }

# Rules for Coroutines
-keep class kotlinx.coroutines.** { *; }

# Rules for Android Sensors
-keep class android.hardware.** { *; }
-keep interface android.hardware.** { *; }

# Rules for Jetpack Compose
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.material.** { *; }
-keep class androidx.compose.animation.** { *; }
-keep @androidx.compose.runtime.Composable class * 
-keepclasseswithmembers class * {
    @androidx.compose.runtime.Composable *;
}
