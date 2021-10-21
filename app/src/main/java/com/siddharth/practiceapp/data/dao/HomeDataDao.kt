package com.siddharth.practiceapp.data.dao

import androidx.room.*
import com.siddharth.practiceapp.data.entities.HomeData

@Dao
interface HomeDataDao {
    @Query("SELECT * FROM homeData_table")
    suspend fun getAllHomeDataList(): List<HomeData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHomeData(homeData: HomeData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHomeDataList(homeDataList: List<HomeData>)

    @Query("DELETE FROM homeData_table")
    suspend fun deleteAllHomeData()

    @Transaction
    suspend fun deleteAndInsertTransaction(homeDataList: List<HomeData>){
        deleteAllHomeData()
        insertHomeDataList(homeDataList)
    }
}