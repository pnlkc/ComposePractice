package com.example.dessertrelease.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object {
        val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout")
        const val TAG = "UserPreferencesRepo"
    }

    /**
     * dataStore.data는 DataStore의 모든 키-값 쌍이 포함되어 있는 Preferences 객체의 Flow를 반환합니다.
     * 즉, Flow<Preferences>를 반환하는데, 이것을 Flow<원하는 타입>으로 map 변환 해주어야 합니다.
     *
     * preferences[IS_LINEAR_LAYOUT] 값이 존재하지 않을 수도 있기 때문에 초기값도 지정해줘야 합니다.
     *
     * catch 블럭을 통해 DataStore에 접근할 때 발생할 수 있는 IOExceptions를 처리해주는 것이 좋습니다.
     * emit(emptyPreferences())를 통해 빈 Preferences 객체를 방출해 map 블럭이 계속 실행되도록 허용합니다.
     */
    val isLinearLayout: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_LINEAR_LAYOUT] ?: true
        }

    /**
     * 이 메소드가 호출되기 전에는 DataStore에 값이 존재하지 않습니다.
     * edit()을 통해 키-값 쌍을 설정해야 값이 정의되고 초기화 됩니다.
     */
    suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT] = isLinearLayout
        }
    }
}