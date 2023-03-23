package com.example.dessertrelease

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessertrelease.data.UserPreferencesRepository

/**
 * 코드랩 6-3-1의 과정을 따라한 코드입니다.
 */
private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

/**
 * by를 사용하여 초기화 하면 Delegate(대리자)를 사용하여 변수를 초기화합니다.
 * Delegate를 사용하여 초기화 하면 위임 하는 객체의 getter/setter를 가져와 자동으로 생성합니다.
 *
 * preferencesDataStore() 파일을 직접 들어가 확인해보면,
 * PreferenceDataStoreSingletonDelegate의 getValue()가 override 되어 있습니다.
 * Context.dataStore는 PreferenceDataStoreSingletonDelegate의()의 getValue()를 가져와 사용합니다.
 */
//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
//    name = LAYOUT_PREFERENCES_NAME
//)
//
//class DessertReleaseApplication: Application() {
//    lateinit var userPreferencesRepository: UserPreferencesRepository
//
//    override fun onCreate() {
//        super.onCreate()
//        userPreferencesRepository = UserPreferencesRepository(dataStore)
//    }
//}


/**
 * DI를 구현할 때 Application 클래스와 AppContainer 클래스를 분리하여 구현한 코드입니다.
 */

class DessertReleaseApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}


interface AppContainer {
    val userPreferencesRepository: UserPreferencesRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val LAYOUT_PREFERENCES_NAME = "layout_preferences"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = LAYOUT_PREFERENCES_NAME
    )

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }
}