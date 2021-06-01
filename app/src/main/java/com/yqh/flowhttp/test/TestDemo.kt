package com.yqh.flowhttp.test

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.NullPointerException
import java.text.SimpleDateFormat

suspend fun main() {

    val startTime = System.currentTimeMillis()
    log("startCoroutine ... ")
    val job = GlobalScope.launch {
        try {
            supervisorScope {
                val response = listOf(
                    async { workTwo() },
                    async { workOne() },
                    async { workThree() }
                ).awaitAll()

                response.forEach {
                    log("value : $it")
                }
            }
        } catch (e: Exception) {
            log("${e.message}")
        }
    }

    job.join()
    val endTime = System.currentTimeMillis()

    val time = endTime - startTime
    log("endCoroutine ... time: $time")

}

fun log(msg: Any) {
    val df = SimpleDateFormat("HH:mm:ss:SSS");
    val dfStr = df.format(System.currentTimeMillis())
    println("$dfStr :  ${Thread.currentThread().name} -> $msg")
}


suspend fun workOne(): Int {
    delay(3000)
    return 3
}


suspend fun workTwo(): String {
    delay(5000)
    throw NullPointerException("出错了...")
    return 4.toString()
}

suspend fun workThree(): Int {
    delay(1000)
    return 5
}