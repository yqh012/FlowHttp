package com.yqh.lib.flowhttp.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yqh.common.utils.AppUtil
import com.yqh.lib.flowhttp.bean.Cache

@Database(entities = [Cache::class], version = 1)
abstract class CacheDataBase : RoomDatabase() {

    companion object {
        val database: CacheDataBase by lazy {
            Room.databaseBuilder(
                AppUtil.application,
                CacheDataBase::class.java,
                "ppjoke_cache"
            )
                //禁用应用在主线程进行查询操作，如果在主线程进行查询会报错
                .allowMainThreadQueries()
                //添加数据库在创建和打开后的回调
//                .addCallback()
                //设置查询的线程池，它会有一个默认的查询线程池
//                .setQueryExecutor()
                //设置数据库工厂
//                .openHelperFactory()
                //设置数据库的日志模式
//                .setJournalMode()
                //设置数据库升级异常之后的回滚
//                .fallbackToDestructiveMigration()
                //设置数据库升级异常之后根据指定版本进行回滚
//                .fallbackToDestructiveMigrationFrom()
                //设置数据库升级时，基于版本处理不同的策略,可以添加多个
//                .addMigrations()
//                .addMigrations(mMigration)
                .build()
        }

//        val mMigration : Migration by lazy {
//            object: Migration(1,2){
//                override fun migrate(database: SupportSQLiteDatabase) {
////                    database.execSQL("alter table teacher rename to student")
////                    ...
//                }
//            }
//        }
    }

    // 必须包含一个具有0个参数且返回带@Dao注释的类的抽象方法
    abstract fun CacheDao(): CacheDao


}