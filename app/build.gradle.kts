import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tvapuestas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tvapuestas"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val localProperties = Properties()
        localProperties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "ODDS_API_KEY", "\"${localProperties.getProperty("ODDS_API_KEY")}\"")
        buildConfigField("String", "ALL_SPORTS_API_KEY", "\"${localProperties.getProperty("ALL_SPORTS_API_KEY")}\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.compose.theme.adapter)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation("com.github.bumptech.glide:glide:4.16.0")
}