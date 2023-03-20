package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database
 * Entitiy와 DAO를 사용하는 Room Database의 인스턴스 (abstract class 안에 companion object가 있는 형태)
 *
 * @Database 어노테이션에는 entities, version 인자가 필요
 *
 * entities
 * entities 매개변수는 데이터베이스에 포함될 엔티티 클래스를 나타냅니다.
 * 엔티티 클래스는 데이터베이스의 테이블을 나타내며,
 * 각각의 열(column)은 해당 엔티티 클래스의 필드(field)에 대응됩니다.
 * entities 매개변수는 배열 형태로 여러 개의 엔티티 클래스를 전달할 수 있습니다.
 *
 * version
 * version 매개변수는 데이터베이스의 버전을 나타냅니다.
 * 데이터베이스가 업데이트될 때마다 이 값을 증가시켜야 합니다.
 * 버전이 변경될 때마다 Room은 Migration 객체를 사용하여
 * 이전 데이터베이스 스키마와 새로운 데이터베이스 스키마 간의 변경 사항을 처리할 수 있습니다.
 *
 * exportSchema
 * exportSchema 매개변수는 boolean 값으로 설정됩니다.
 * 이 값이 true이면, Room은 데이터베이스 스키마를 export 폴더에 저장하여 앱의 디렉토리 내에서 확인할 수 있습니다.
 * 이렇게 함으로써, 데이터베이스 스키마의 변경 사항을 추적하고, 이전 버전의 데이터베이스와의 호환성을 유지할 수 있습니다.
 * 하지만, exportSchema 값을 true로 설정하면, 빌드 시간이 길어질 수 있습니다. 따라서, 개발 중에만 사용하도록 권장됩니다
 *
 *
 * @Volatile (Volatile = 휘발성)
 * @Volatile 어노테이션을 사용하면 변수가 메인 메모리에 직접 저장되어
 * 다른 스레드에서 변수를 업데이트 하더라도
 * 항상 최신 값을 가져올 수 있음
 *
 * sychronized 블럭
 * synchronized 블럭은 한 번에 하나의 스레드만 해당 블럭에 접근할 수 있도록 하여
 * 동시에 여러 스레드에서 데이터베이스 객체에 접근하는 것을 방지합니다.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }

                /*
                 아래처럼 직접 할당할 수도 있음
                Instance = Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                 */

            }
        }
    }
}