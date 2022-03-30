# Default values in:
# /android-sdk/tools/proguard/proguard-android.txt

-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable, Annotation, EnclosingMethod, InnerClasses, Exceptions

-keep public class * extends java.lang.Exception
-keep public class * extends java.lang.Throwable

# Keep all Enum values, including not used
-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Android
-dontwarn android.content.**
-dontwarn android.animation.**
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

# Remove Logcat messages
-assumenosideeffects class android.util.Log {
    public static int v(...);
}

# Kotlin
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keep class kotlin.Metadata { *; }
-dontnote kotlin.internal.PlatformImplementationsKt

-keep class kotlin.reflect.jvm.internal.** { *; }
-dontnote kotlin.reflect.jvm.internal.**

# Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class androidx.lifecycle.Lifecycle$State { *; }
-keepclassmembers class androidx.lifecycle.Lifecycle$Event { *; }
-keep class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
-keep class * implements androidx.lifecycle.GeneratedAdapter {
    <init>(...);
}
-keepclassmembers class androidx.lifecycle.** { *; }
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

# AndroidX
-dontnote android.databinding.**
-keep class android.databinding.** { *; }
-dontnote com.google.android.material.**

# Hilt
# Keep class names of Hilt injected ViewModels since their name are used as a multibinding map key.
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
