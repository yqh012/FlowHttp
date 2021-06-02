package com.yqh.lib.flowhttp.cache

enum class CacheStrategy(val id: Int) {

    /**
     * 只访问本地缓存，即使本地缓存不存在，也不会请求网络
     */
    CACHE_ONLY(1),

    /**
     * 先访问本地缓存，同时请求网络，网络数据请求成功后在保存到本地
     */
    CACHE_FIRST(2),

    /**
     * 只请求网络，不做任何存储
     */
    NET_ONLY(3),

    /**
     * 先访问网络，成功后再把数据缓存到本地
     */
    NET_CACHE(4)
}