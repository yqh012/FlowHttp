/**
 * 管理第三方库依赖
 */
object ThirdParty {

    const val multidex = "com.android.support:multidex:1.0.3"

    const val utilCodex = "com.blankj:utilcodex:1.30.5"

    val okhttp = OkHttp

    object OkHttp {
        private const val okhttp_version = "4.9.1"
        const val okhttp = "com.squareup.okhttp3:okhttp:$okhttp_version"
        const val okhttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$okhttp_version"
    }

    val retrofit = Retrofit

    object Retrofit {
        private const val retrofit_version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
        const val scalars = "com.squareup.retrofit2:converter-scalars:$retrofit_version"
        const val gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
        const val moshi = "com.squareup.retrofit2:converter-moshi:$retrofit_version"
    }

    val glide = Glide

    object Glide {
        private const val glide = "com.github.bumptech.glide:glide:4.11.0"
        const val transformations = "jp.wasabeef:glide-transformations:4.1.0"
    }

    val smart = Smart

    object Smart {
        private const val smart_version = "2.0.3"
        const val smart = "com.scwang.smartrefresh:SmartRefreshLayout:$smart_version"
        const val head = "com.scwang.smartrefresh:SmartRefreshHeader:$smart_version"
    }

    val arout = Arout

    object Arout {
        private const val arouter_version = "1.5.1"
        const val arouter = "com.alibaba:arouter-api:$arouter_version"
        const val arouterCompiler = "com.alibaba:arouter-compiler:$arouter_version"
    }

    val mmkv = Mmkv

    object Mmkv {
        private const val mmkv_version = "1.2.7"
        const val mmkv = "com.tencent:mmkv-static:$mmkv_version"
    }

    val autoSize = AutoSize
    object AutoSize {
        private const val autosize_version = "1.2.1"
        const val layoutAutosize = "me.jessyan:autosize:$autosize_version"
    }

//    implementation("io.coil-kt:coil:1.1.1")

    val coil = Coil
    object Coil {
        private const val coil_version = "1.1.1"
        const val coil = "io.coil-kt:coil:${coil_version}"
    }

}