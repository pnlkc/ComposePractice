import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.InventoryDatabase
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * 데이터베이스 구현을 테스트하는데 권장되는 방법은 JUnit 테스트입니다.
 * JUnit 테스트는 Activity를 따로 생성하지 않아 UI 테스트보다 빠르게 가능합니다.
 *
 * inMemoryDatabaseBuilder()
 * inMemoryDatabaseBuilder()는 메모리(RAM)에 데이터베이스를 저장하기 때문에 매우 빠른 테스가 가능하지만
 * 앱이 종료될 때 모든 데이터가 삭제되기 때문에 테스트용이나 특수한 목적용으로 적합합니다.
 *
 * allowMainThreadQueries()
 * allowMainThreadQueries()는 메인 스레드에서 쿼리를 수행하도록 하는 기능입니다.
 * 메인 스레드에서 작업을 하기 때문에 빠른 실행이 가능하지만 UI와 같은 앱의 성능에 영향을 줄 수 있기 때문에
 * 테스트용으로만 사용하는 것이 바람직합니다.
 *
 * 코루틴 테스트임에도 runTest 블럭이 아니라 runBlocking 블럭에서 테스트를 하는 이유?
 * runTest는 androidTest 소스 디렉토리의 테스트에서는 사용할 수 없습니다.
 * runTest는 test 소스 디렉토리에 있는 단위 테스트에서 주로로사용됩니다.
 */

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase

    private var item1 = Item(1, "Apples", 10.0, 20)
    private var item2 = Item(2, "Bananas", 15.0, 97)

    // @Before 주석은 테스트 실행 전에 실행되어야 하는 코드에 필요합니다.
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase = Room
            .inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.update(Item(1, "Apples", 15.0, 25))
        itemDao.update(Item(2, "Bananas", 5.0, 50))

        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
        assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.delete(item1)
        itemDao.delete(item2)
        val allItems = itemDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = itemDao.getItem(1)
        assertEquals(item.first(), item1)
    }
}