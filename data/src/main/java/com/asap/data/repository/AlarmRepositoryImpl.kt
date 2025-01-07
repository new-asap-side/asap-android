package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.service.AlarmService
import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val remoteDataSource: AlarmService,
    localDataSource: AppDatabase
) : AlarmRepository{
    private val dao = localDataSource.deactivatedAlarmDao()

    override suspend fun getDeactivatedAlarmList(): List<DeactivatedAlarm> {
        return dao.getDeactivatedAlarmList()
    }

    override suspend fun activate(alarmSummary: AlarmSummary) {
        dao.insert(
            DeactivatedAlarm(
                groupId = alarmSummary.groupId,
                groupTitle = alarmSummary.group.title,
                alarmTime = alarmSummary.group.alarmTime,
                alarmDates = alarmSummary.group.alarmDays.toString()
            )
        )
    }

    override suspend fun deactivate(alarmSummary: AlarmSummary) {
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