
plugins {
    id ("kizzy.android.library")
    id ("kizzy.android.library.compose")
}
android {
    namespace = "com.kizzy.color"
}
dependencies {
    implementation(platform(libs.compose.bom))
    api(libs.compose.ui)
    api(libs.core.ktx)
    api(libs.material3)
}