package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object)
 * 데이터에 접근할 수 있는 메소드(SELECT / INSERT / DELETE / UPDATE)를 정의 해놓은 인터페이스
 *
 * onConflict의 인자는 충돌이 발생하는 경우 Room에 실행할 작업을 알려줍니다
 * OnConflictStrategy.IGNORE은 새 항목을 무시합니다
 *
 * SELECT 메소드는 @Query 어노테이션 뒤에 SQLite 쿼리를 직접 입력하여 생성
 * 쿼리에서 함수의 인자를 선택하려면 :id 처럼 콜론 표기법을 사용
 *
 * Flow<T>를 반환 유형으로 한 경우에는
 * Room이 자동으로 백그라운드 스레드에서 쿼리를 실행하기 때문에
 * suspend fun으로 설정할 필요 없음
 */

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}