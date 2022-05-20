plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  kotlin("plugin.serialization") version LibVersion.kotlin
  id("kotlin-parcelize")
  id("androidx.navigation.safeargs.kotlin")
  id("dagger.hilt.android.plugin")
  id("com.github.ben-manes.versions") version "0.42.0"
}

android {
  compileSdk = 32

  defaultConfig {
    applicationId = "com.himatifunpad.imazine1"
    minSdk = 23
    targetSdk = 32
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
    debug {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility(JavaVersion.VERSION_11)
    targetCompatibility(JavaVersion.VERSION_11)
  }
  kotlinOptions {
    jvmTarget = "11"
    freeCompilerArgs = freeCompilerArgs + listOf(
      "-Xopt-in=kotlin.ExperimentalStdlibApi",
      "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      "-Xopt-in=kotlin.RequiresOptIn"
    )
  }
  buildFeatures {
    viewBinding = true
  }
  packagingOptions {
    resources.excludes.add("/META-INF/AL2.0")
    resources.excludes.add("/META-INF/LGPL2.1")
  }
}

dependencies {
  implementation(kotlin("stdlib", version = LibVersion.kotlin))
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibVersion.coroutines}")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibVersion.coroutines}")

  // androidx core
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("androidx.datastore:datastore-preferences:1.0.0")
  implementation("androidx.fragment:fragment-ktx:1.4.1")
  implementation("androidx.activity:activity-ktx:1.4.0")
  implementation("androidx.preference:preference-ktx:1.2.0")

  //ui
  implementation("com.google.android.material:material:1.6.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.3")
  implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
  implementation("androidx.paging:paging-runtime-ktx:3.1.1")
  implementation("com.afollestad.material-dialogs:core:3.3.0")

  //navigation
  implementation("androidx.navigation:navigation-fragment-ktx:${LibVersion.nav}")
  implementation("androidx.navigation:navigation-ui-ktx:${LibVersion.nav}")

  // lifecycle
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${LibVersion.lifecycle}")
  implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${LibVersion.lifecycle}")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:${LibVersion.lifecycle}")

  // dagger hilt
  implementation("com.google.dagger:hilt-android:${LibVersion.hilt}")
  kapt("com.google.dagger:hilt-compiler:${LibVersion.hilt}")
  kapt("androidx.hilt:hilt-compiler:${LibVersion.androidxHilt}")

  // networking
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
  implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

  // other library
  implementation("io.coil-kt:coil:2.0.0")
  implementation("org.jsoup:jsoup:1.15.1")
  implementation("com.squareup.logcat:logcat:0.1")

  // testing
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
  correctErrorTypes = true
}

// ben-manes versions checking
fun isNonStable(version: String): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
    version.toUpperCase()
      .contains(it)
  }
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  val isStable = stableKeyword || regex.matches(version)
  return isStable.not()
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
  // reject all non stable versions
  rejectVersionIf {
    isNonStable(candidate.version)
  }

  // optional parameters
  checkForGradleUpdate = true
  outputFormatter = "json"
  outputDir = "build/dependencyUpdates"
  reportfileName = "report"
}