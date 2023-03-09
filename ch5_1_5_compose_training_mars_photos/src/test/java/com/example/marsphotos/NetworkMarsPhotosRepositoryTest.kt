package com.example.marsphotos

import com.example.marsphotos.fake.FakeDataSource
import com.example.marsphotos.fake.FakeMarsApiService
import com.example.marsphotos.fake.NetworkMarsPhotosRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkMarsPhotosRepositoryTest {
    // getMarsPhotos()가 suspend fun이므로 테스트가 runTest 블록 안에서 실행되어야 함
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() = runTest {
        val repository = NetworkMarsPhotosRepository(
            marsApiService = FakeMarsApiService()
        )
        assertEquals(FakeDataSource.photosList, repository.getMarsPhotos())
    }
}