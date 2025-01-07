package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.service.AlarmService
import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val remoteDataSource: AlarmService,
    private val localDataSource: AppDatabase
) : AlarmRepository{
    override suspend fun getDeactivatedAlarmList(): List<DeactivatedAlarm> {
        val dao = localDataSource.deactivatedAlarmDao()
        return dao.getDeactivatedAlarmList()
    }

    override suspend fun activate() {
        TODO("Not yet implemented")
    }

    override suspend fun deactivate() {
        TODO("Not yet implemented")
    }

    override suspend fun release() {
        TODO("Not yet implemented")
    }
}