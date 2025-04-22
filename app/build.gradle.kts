plugins {
    alias(libs.plugins.android.application)
}
apply(plugin="realm-android")
android {
    namespace = "com.bagew.expensetracker2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bagew.expensetracker2"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0 Base Prototype"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")
}