import java.text.SimpleDateFormat
import java.util.*

val appName = "Forecast"

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.30"
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(33)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId("com.cmc.forecast")
        minSdkVersion(24)
        targetSdkVersion(33)
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = true

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keystores/debug.jks")
        }

        create("production") {
            storeFile = file("/keystores/release.jks")
            storePassword = ""
            keyAlias = ""
            keyPassword = ""
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("production")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            isTestCoverageEnabled = true
        }

        create("staging") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".staging"
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    applicationVariants.all {
        when (name) {
            "debug" -> {
                resValue("string", "app_name", "[DEV]-${appName}")
                buildConfigField("String", "API_URL", "\"https://api.openweathermap.org/\"")
                buildConfigField("String", "API_KEY", "\"1160f381fa33aa1827ed0f1be7b7db2e\"")
            }

            "staging" -> {
                resValue("string", "app_name", "[STG]-${appName}")
                buildConfigField("String", "API_URL", "\"https://api.openweathermap.org/\"")
                buildConfigField("String", "API_KEY", "\"1160f381fa33aa1827ed0f1be7b7db2e\"")
            }

            "release" -> {
                resValue("string", "app_name", appName)
                buildConfigField("String", "API_URL", "\"https://api.openweathermap.org/\"")
                buildConfigField("String", "API_KEY", "\"1160f381fa33aa1827ed0f1be7b7db2e\"")
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.2")
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2"
    )
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.10.6")
    testImplementation("com.squareup.okhttp3:mockwebserver:3.9.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("io.mockk:mockk-android:1.10.6")
    implementation("com.google.dagger:hilt-android:2.33-beta")
    kapt("com.google.dagger:hilt-compiler:2.33-beta")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("androidx.fragment:fragment-ktx:1.3.0")
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation("com.jaredrummler:material-spinner:1.3.1")
    implementation("net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC2")
}

//Config for output apk
android.applicationVariants.configureEach {
    outputs.all {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val buildNumber = System.getenv("DRONE_BUILD_NUMBER")?.toInt() ?: versionCode
        (this as? com.android.build.gradle.internal.api.BaseVariantOutputImpl)?.outputFileName =
                "appName${name}_${versionName}.${buildNumber}_${formattedDate}.apk"
    }
}
