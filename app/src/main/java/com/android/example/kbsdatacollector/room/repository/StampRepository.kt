package com.android.example.kbsdatacollector.room.repository

import androidx.annotation.WorkerThread
import com.android.example.kbsdatacollector.room.dao.StampDao
import com.android.example.kbsdatacollector.room.db.Stamp
import kotlinx.coroutines.flow.Flow

class StampRepository(private val stampDao: StampDao) {

    val allProducts: Flow<List<Stamp>> = stampDao.getSortedStamps()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(stamp: Stamp) {
        stampDao.insert(stamp)
    }
}