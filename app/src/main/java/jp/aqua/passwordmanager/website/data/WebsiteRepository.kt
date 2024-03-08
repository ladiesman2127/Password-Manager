package jp.aqua.passwordmanager.website.data

import android.content.Context
import jp.aqua.passwordmanager.crypto.MasterPassword
import jp.aqua.passwordmanager.crypto.Сryptographer
import jp.aqua.passwordmanager.website.model.Website
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


class WebsiteRepository(
    context: Context,
    private val websiteDao: WebsiteDao,
    private val storageDir: File,
    private val masterPassword: MasterPassword
) {

    fun getAllWebsites(): Flow<List<Website>> = websiteDao.getAllWebsites()

    suspend fun addWebsite(href: String, password: String): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val url = URL(href)
                val parsedUrl = "${url.protocol}://${url.host}"
                val faviconHref = storeWebsiteFavicon(url, storageDir)
                val encryptedPassword = Сryptographer.encrypt(password)
                insertWebsiteIntoDB(parsedUrl, faviconHref, encryptedPassword)
                Result.success(true)
            } catch (e: IOException) {
                e.printStackTrace()
                Result.failure(e)
            }
        }

    private suspend fun storeWebsiteFavicon(url: URL, storageDir: File): String =
        withContext(Dispatchers.IO) {
            val faviconUrl = URL("${url.protocol}://${url.host}/${Website.FAVICON_SUFFIX}")
            val faviconData = faviconUrl.readBytes()

            val favIconsDir = File(storageDir, "icons/")
            if (!favIconsDir.exists())
                favIconsDir.mkdir()

            val faviconFile = File(favIconsDir, url.host)

            val fos = FileOutputStream(faviconFile)
            fos.flush()
            fos.close()
            faviconFile.writeBytes(faviconData)
            faviconFile.path
        }

    private suspend fun insertWebsiteIntoDB(url: String, favicon: String, password: String) =
        withContext(Dispatchers.IO) {
            val website = Website(url = url, favicon = favicon, password = password)
            websiteDao.insertWebsite(website)
        }

    suspend fun deleteWebsite(website: Website) = websiteDao.deleteWebsite(website)

    suspend fun getMasterPassword() = masterPassword.get()

    suspend fun setMasterPassword(password: String) = masterPassword.set(password)

}
