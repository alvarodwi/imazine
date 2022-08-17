buildscript {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.2.2")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${LibVersion.kotlin}")
    classpath("com.google.dagger:hilt-android-gradle-plugin:${LibVersion.hilt}")
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${LibVersion.nav}")
    // firebase
    classpath( "com.google.gms:google-services:4.3.13")
    classpath( "com.google.firebase:firebase-crashlytics-gradle:2.9.1")
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}