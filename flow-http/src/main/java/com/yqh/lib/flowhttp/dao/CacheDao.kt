package com.yqh.lib.flowhttp.dao

import androidx.room.*
import com.yqh.lib.flowhttp.bean.Cache

@Dao
interface CacheDao {

    //如果出现插入出现冲突(例如相同的数据)的处理方式,目前采用替换的方式
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cache: Cache): Long

    /**
     * 如果是一对多,这里可以写List<Cache>
     * 注意，冒号后面必须紧跟参数名，中间不能有空格。大于小于号和冒号中间是有空格的。
     * select *from cache where【表中列名】 =:【参数名】------>等于
     * where 【表中列名】 < :【参数名】 小于
     * where 【表中列名】 between :【参数名1】 and :【参数2】------->这个区间
     * where 【表中列名】like :参数名----->模糊查询
     * where 【表中列名】 in (:【参数名集合】)---->查询符合集合内指定字段值的记录
     */
    @Query("select *from http_cache where `key`=:key")
    fun getCache(key: String): Cache

    //只可以传递对象，删除时会根据Cache中的主键，来对比删除
    @Delete
    fun delete(cache: Cache)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache): Int
}