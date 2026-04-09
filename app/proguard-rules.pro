# Project-specific rules; release uses proguard-android-optimize.txt + this file.

# Better stack traces in Play Console / crash reporters after obfuscation
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Kotlin
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.reflect.jvm.internal.**

# Koin (modules + definitions; runtime graph)
-keep class org.koin.** { *; }
-keepclassmembers class * {
    @org.koin.core.annotation.* <methods>;
}

# Coroutines (optional warnings)
-dontwarn kotlinx.coroutines.flow.**
