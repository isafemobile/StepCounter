
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
