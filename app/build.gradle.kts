import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.asap.aljyo"
    compileSdk = 34

    val localProperties = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }

    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.asap.aljyo"
        minSdk = 26
        targetSdk = 34
        versionCode = 11
        versionName = "1.0.10"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val kakaoKey = localProperties.getProperty("kakao_native_app_key") ?: ""
        // manifest key value 등록
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoKey
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoKey\"")
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = localProperties.getProperty("SIGNED_KEY_ALIAS")
            keyPassword = localProperties.getProperty("SIGNED_KEY_PASSWORD")
            storePassword = localProperties.getProperty("SIGNED_STORE_PASSWORD")
            storeFile = File(localProperties.getProperty("SIGNED_STORE_FILE"))
        }

        create("release") {
            keyAlias = localProperties.getProperty("SIGNED_KEY_ALIAS_RELEASE")
            keyPassword = localProperties.getProperty("SIGNED_KEY_PASSWORD")
            storePassword = localProperties.getProperty("SIGNED_STORE_PASSWORD")
            storeFile = File(localProperties.getProperty("SIGNED_STORE_FILE_RELEASE"))
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // splash screen api
    implementation(libs.androidx.core.splashscreen)

    // hilt
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // navigation with compose
    implementation(libs.navigation.compose)

    // compose placeholder
    implementation(libs.compose.placeholder.material)

    // kakao SDK
    implementation(libs.kakao.login)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.gif)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)

    // moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapter)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // glance-app widget
    implementation(libs.glance.appwidget)
    implementation(libs.glance.material)

    // system-ui-controller
    implementation(libs.system.ui.controller)

    // module
    implementation(project(":domain"))
    implementation(project(":utility"))
    implementation(project(":core"))
    implementation(project(":presentation"))

    // 없애야함
    implementation(project(":data"))
}