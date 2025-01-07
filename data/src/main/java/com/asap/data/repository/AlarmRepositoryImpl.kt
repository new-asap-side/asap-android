package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.service.AlarmService
import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.entity.remote.AlarmSummary
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

    override suspend fun deactivate(alarmSummary: AlarmSummary) {
        val dao = localDataSource.deactivatedAlarmDao()
        dao.insert(
            DeactivatedAlarm(
                groupId = alarmSummary.groupId,
                groupTitle = alarmSummary.group.title,
                alarmTime = alarmSummary.group.alarmTime,
                alarmDates = alarmSummary.group.alarmDays.toString()
            )
        )
    }

    override suspend fun release() {
        TODO("Not yet implemented")
    }
}