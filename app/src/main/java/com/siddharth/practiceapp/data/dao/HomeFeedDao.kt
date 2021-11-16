package com.siddharth.practiceapp.data.dao

import androidx.room.*
import com.siddharth.practiceapp.data.entities.HomeData
import com.siddharth.practiceapp.data.entities.HomeFeed
import kotlinx.coroutines.delay

@Dao
interface HomeFeedDao {
    @Query("SELECT * FROM HomeFeed_table")
    suspend fun getAllHomeFeedList(): MutableList<HomeFeed>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHomeFeed(homeFeed: HomeFeed)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHomeFeedList(homeFeedList: List<HomeFeed>)

    @Query("DELETE FROM HomeFeed_table")
    suspend fun deleteAllHomeFeed()

    @Transaction
    suspend fun deleteAndInsertTransaction(homeFeedList: List<HomeFeed>) {
        deleteAllHomeFeed()
        delay(1000)
        insertHomeFeedList(homeFeedList)
    }
}