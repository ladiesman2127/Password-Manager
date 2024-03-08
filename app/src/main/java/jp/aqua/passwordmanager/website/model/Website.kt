package jp.aqua.passwordmanager.website.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(Website.TABLE_NAME)
data class Website(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int? = null,
    @ColumnInfo(name = URL)
    val url: String,
    @ColumnInfo(name = FAVICON)
    val favicon: String,
    @ColumnInfo(name = PASSWORD)
    val password: String,
) {
    companion object {
        const val TABLE_NAME = "websites"
        const val ID = "id"
        const val URL = "url"
        const val FAVICON = "favicon"
        const val PASSWORD = "password"
        const val FAVICON_SUFFIX = "favicon.ico"
    }
}