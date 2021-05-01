package ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning

import androidx.room.*
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SimpleScanningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(simpleScanning: SimpleScanning) : Long

    @Delete
    suspend fun delete(simpleScanning: SimpleScanning)

    @Query("SELECT * FROM simple_scanning WHERE id = :id")
    fun getById(id: Long): SimpleScanning?

    @Query("SELECT * FROM simple_scanning")
    suspend fun getAll(): List<SimpleScanning>

    @Query("SELECT * FROM simple_scanning")
    fun getAllFlow(): Flow<List<SimpleScanning>>

    @Query("SELECT * FROM simple_scanning ORDER BY id DESC LIMIT 1")
    fun getDocWithLastestId(): SimpleScanning?

}