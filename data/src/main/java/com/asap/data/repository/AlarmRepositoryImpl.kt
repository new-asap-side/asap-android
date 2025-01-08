package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.datasource.AlarmRemoteDataSource
import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val remoteDataSource: AlarmRemoteDataSource,
    localDataSource: AppDatabase
) : AlarmRepository{
    private val userDao = localDataSource.userDao()
    private val dao = localDataSource.deactivatedAlarmDao()

    override suspend fun getDeactivatedAlarmList(): List<DeactivatedAlarm> {
        return dao.getDeactivatedAlarmList()
    }

    override suspend fun activate(alarmSummary: AlarmSummary) {
        dao.delete(
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

    override suspend fun release(groupId: Int): Flow<WhetherResponse?> {
        val userId = (userDao.selectAll().firstOrNull()?.userId ?: "-1").toInt()
        return remoteDataSource.release(userId = userId, groupId = groupId)
    }
}