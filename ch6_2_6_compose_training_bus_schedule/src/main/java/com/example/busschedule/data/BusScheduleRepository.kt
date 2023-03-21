package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

interface BusScheduleRepository {

    fun getAllSchedule(): Flow<List<BusSchedule>>

    fun getSchedule(stopName: String): Flow<List<BusSchedule>>
}

class DefaultBusScheduleRepository(
    private val busScheduleDao: BusScheduleDao
) : BusScheduleRepository {
    override fun getAllSchedule(): Flow<List<BusSchedule>> = busScheduleDao.getAllSchedule()

    override fun getSchedule(stopName: String): Flow<List<BusSchedule>> =
        busScheduleDao.getSchedule(stopName)
}