package jp.aqua.passwordmanager.website.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.aqua.passwordmanager.website.model.Website

@Database(
    entities = [Website::class],
    version = 1,
    exportSchema = false
)
abstract class WebsiteDatabase : RoomDatabase() {
    abstract fun websiteDao(): WebsiteDao

    companion object {
        @Volatile
        private var INSTANCE: WebsiteDatabase? = null

        fun getDatabase(context: Context): WebsiteDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    WebsiteDatabase::class.java,
                    "websites_db"
                )
            }
                .build()
                .also { INSTANCE = it }
        }
    }
}