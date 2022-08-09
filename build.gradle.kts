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
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}