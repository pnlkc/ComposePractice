package com.example.busschedule.data

import android.content.Context

interface AppContainer {
    val busScheduleRepository: BusScheduleRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val busScheduleRepository: BusScheduleRepository by lazy {
        DefaultBusScheduleRepository(BusScheduleDatabase.getDatabase(context).busScheduleDao())
    }
}