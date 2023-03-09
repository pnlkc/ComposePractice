package com.example.marsphotos.fake

import com.example.marsphotos.data.MarsPhotoRepository
import com.example.marsphotos.network.MarsApiService
import com.example.marsphotos.network.MarsPhoto

class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotoRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}