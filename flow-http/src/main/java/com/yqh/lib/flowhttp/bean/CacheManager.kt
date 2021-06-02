package com.yqh.lib.flowhttp.bean

import com.yqh.lib.flowhttp.dao.CacheDataBase
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * 实体 Bean 转byte数组
 */
fun <T> T.toByteArray(): ByteArray {
    return ByteArrayOutputStream().use { byteArrayOutputStream ->
        ObjectOutputStream(byteArrayOutputStream).use { objOutputStream ->
            objOutputStream.writeObject(this)
        }
        byteArrayOutputStream.toByteArray()
    }
}

/**
 * byte数组转 实体
 */
fun <T> ByteArray.toDomain(): T? {
    return ByteArrayInputStream(this).use { byteArrayInputStream ->
        ObjectInputStream(byteArrayInputStream).use {
            it.readObject() as? T
        }
    }
}

/**
 * 删除缓存
 */
fun <T> T.deleteCache(key: String) {
    val cache = Cache()
    cache.key = key
    cache.data = this.toByteArray()
    CacheDataBase.database.CacheDao().delete(cache)
}

/**
 * 添加缓存
 */
fun <T> T.saveCache(key: String) {
    val cache = Cache()
    cache.key = key
    cache.data = this.toByteArray()
    CacheDataBase.database.CacheDao().delete(cache)
}

/**
 * 获取缓存
 */
fun <T> getCache(key: String): T? {
    val cache = CacheDataBase.database.CacheDao().getCache(key)
    return cache.data?.toDomain<T>()
}