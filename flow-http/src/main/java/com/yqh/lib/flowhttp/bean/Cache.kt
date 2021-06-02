package com.yqh.lib.flowhttp.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "http_cache")
class Cache : Serializable {
    @PrimaryKey
    var key: String = ""

    var data: ByteArray? = null
}