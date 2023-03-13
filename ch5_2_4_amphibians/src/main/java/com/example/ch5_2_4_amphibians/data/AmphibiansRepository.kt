package com.example.ch5_2_4_amphibians.data

import com.example.ch5_2_4_amphibians.network.AmphibiansApiService
import com.example.ch5_2_4_amphibians.network.AmphibiansData

interface AmphibiansRepository {
    suspend fun getData() : List<AmphibiansData>
}

class DefaultAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
): AmphibiansRepository {

    override suspend fun getData(): List<AmphibiansData> {
        return amphibiansApiService.getData()
    }
}