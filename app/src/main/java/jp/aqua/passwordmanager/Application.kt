package jp.aqua.passwordmanager

import android.app.Application
import jp.aqua.passwordmanager.crypto.MasterPassword
import jp.aqua.passwordmanager.website.data.WebsiteDatabase
import jp.aqua.passwordmanager.website.data.WebsiteRepository
import java.io.File

class Application : Application() {
    lateinit var websiteRepository: WebsiteRepository
        private set

    private lateinit var masterPassword: MasterPassword

    private lateinit var storageDir: File

    override fun onCreate() {
        super.onCreate()

        storageDir = File(filesDir?.path + "/")

        masterPassword = MasterPassword(storageDir)

        websiteRepository = WebsiteDatabase.getDatabase(this).websiteDao()
            .let { dao -> WebsiteRepository(applicationContext, dao, storageDir, masterPassword) }
    }
}