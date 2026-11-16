// 조립하는 모듈. 모든 모듈을 아는 곳은 여기 하나뿐입니다
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.cleanlauncher"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cleanlauncher"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures { compose = true }
}

kotlin {
    compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)

    // E2E 테스트: 에뮬레이터에서 앱을 실제로 띄워 확인합니다
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    // 테스트에서 권한을 미리 허락하는 GrantPermissionRule
    androidTestImplementation(libs.androidx.test.rules)
    // Compose 테스트가 안에서 쓰는 Espresso. 최신 안드로이드(16, 17)에서 돌리려면 새 버전이 필요합니다
    androidTestImplementation(libs.androidx.test.espresso.core)
}
