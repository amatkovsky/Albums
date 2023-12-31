import com.android.build.api.variant.BuildConfigField
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.amatkovsky.albums"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.amatkovsky.albums"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
//            minifyEnabled = false
//            proguardFiles = getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

androidComponents {
    onVariants(selector().withBuildType("debug")) { variant ->
        val props = Properties()
        file("secrets.properties").inputStream().use {
            props.load(it)
        }
        val clientId = props.getProperty("UNSPLASH_CLIENT_ID")
        variant.buildConfigFields.put(
            "UNSPLASH_CLIENT_ID",
            BuildConfigField("String", "\"$clientId\"", null)
        )
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(libs.google.dagger)
    kapt(libs.google.dagger.compiler)

    implementation(libs.google.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.google.hilt.compiler)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.moshi)
    implementation(libs.squareup.moshi.kotlin)
    ksp(libs.squareup.moshi.kotlin.codegen)

    testImplementation(libs.junit)
    testImplementation(libs.turbine)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
