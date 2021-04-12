/**
 * 管理Android X 相关依赖库
 */
object Android {

    const val appcompat         = "androidx.appcompat:appcompat:1.2.0"
    const val coreKtx           = "androidx.core:core-ktx:1.3.2"
    const val material          = "com.google.android.material:material:1.2.1"
    const val constraintlayout  = "androidx.constraintlayout:constraintlayout:2.0.4"


    val lifecycle = Lifecycle
    object Lifecycle {
        private const val lifecycle_version = "2.3.0"
        const val viewModelKtx          = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
        const val liveDataKtx           = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
        const val lifecycleRuntime      = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
        const val viewModelSavestate    = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
        const val process            = "androidx.lifecycle:lifecycle-process:$lifecycle_version"
        const val commonJava8           = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
        const val lifecycleKapt         = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    }

    val navigation = Navigation
    object Navigation {
        private const val navigation_version = "2.3.3"
        const val fragmentKtx   = "androidx.navigation:navigation-fragment-ktx:$navigation_version"
        const val uiKtx         = "androidx.navigation:navigation-ui-ktx:$navigation_version"
    }

    val room = Room
    object Room {
        private const val room_version = "2.2.6"
        const val roomRuntime   = "androidx.room:room-runtime:$room_version"
        const val roomCompiler  = "androidx.room:room-compiler:$room_version"
        const val roomKtx       = "androidx.room:room-ktx:$room_version"
    }

}