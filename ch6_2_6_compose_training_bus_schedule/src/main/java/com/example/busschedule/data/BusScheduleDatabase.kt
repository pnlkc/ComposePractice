package com.example.busschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BusSchedule::class], version = 1, exportSchema = false)
abstract class BusScheduleDatabase: RoomDatabase() {
    abstract fun busScheduleDao() : BusScheduleDao

    companion object {
        @Volatile
        private var Instance: BusScheduleDatabase? = null

        fun getDatabase(context: Context): BusScheduleDatabase {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, BusScheduleDatabase::class.java, "app_database")
                    // createFromAsset() 메소드는
                    // 앱 내부의 Assets 폴더에서 SQLite 데이터베이스 파일을 불러와서 Room 데이터베이스를 생성합니다
                    .createFromAsset("database/bus_schedule.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}