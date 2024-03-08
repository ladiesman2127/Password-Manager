package jp.aqua.passwordmanager.website.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.aqua.passwordmanager.website.model.Website
import kotlinx.coroutines.flow.Flow


@Dao
interface WebsiteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebsite(website: Website)

    @Delete
    suspend fun deleteWebsite(website: Website)

    @Query("SELECT * FROM " + Website.TABLE_NAME + " WHERE " + Website.ID + " = :websiteId")
    fun getWebsite(websiteId: Int): Flow<Website>

    @Query("SELECT * FROM " + Website.TABLE_NAME)
    fun getAllWebsites(): Flow<List<Website>>

}
