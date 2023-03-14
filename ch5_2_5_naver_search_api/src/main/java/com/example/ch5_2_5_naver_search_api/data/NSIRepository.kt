package com.example.ch5_2_5_naver_search_api.data

import com.example.ch5_2_5_naver_search_api.network.NSIApiService
import com.example.ch5_2_5_naver_search_api.network.NSIData

interface NSIRepository {
    suspend fun getSearchImage(
        clientId: String,
        clientSecret: String,
        query: String,
        display: Int,
        start: Int,
        sort: String,
        filter: String,
    ): NSIData
}

class DefaultNSIRepository(
    private val nsiApiService: NSIApiService,
) : NSIRepository {
    override suspend fun getSearchImage(
        clientId: String,
        clientSecret: String,
        query: String,
        display: Int,
        start: Int,
        sort: String,
        filter: String,
    ): NSIData {
        return nsiApiService.getSearchImage(
            clientId = clientId,
            clientSecret = clientSecret,
            query = query,
            display = display,
            start = start,
            sort = sort,
            filter = filter
        )
    }
}