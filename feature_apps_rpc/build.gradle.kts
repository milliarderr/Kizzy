plugins {
    id ("kizzy.android.library")
    id ("kizzy.android.library.compose")
    id ("kizzy.android.feature")
}

android {
    namespace = "com.my.kizzy.feature_apps_rpc"
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(projects.featureRpcBase)
    implementation (libs.material.icons.extended)
}