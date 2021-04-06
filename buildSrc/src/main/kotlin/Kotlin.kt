/**
 * 管理 Kotlin 相关依赖
 */
object Kotlin {
    private const val kotlin_version = "1.4.32"
    //标准库
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    //反射库
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"

    //Kotlin插件版本
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

    //协程版本
    private const val coroutines_version = "1.4.2"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    //协程官方框架扩展依赖包
    const val coroutinesJdk = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutines_version"

}