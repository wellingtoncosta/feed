# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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

-keep class io.github.wellingtoncosta.feed.domain.entity.** { *; }
-keep class io.github.wellingtoncosta.feed.infrastructure.api.entity.** { *; }

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class io.github.wellingtoncosta.feed.**$$serializer { *; }
-keepclassmembers class io.github.wellingtoncosta.feed.** {
    *** Companion;
}
-keepclasseswithmembers class io.github.wellingtoncosta.feed.** {
    kotlinx.serialization.KSerializer serializer(...);
}
