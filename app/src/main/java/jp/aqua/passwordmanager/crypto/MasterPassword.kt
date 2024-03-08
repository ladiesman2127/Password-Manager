package jp.aqua.passwordmanager.crypto

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MasterPassword(private val storageDir: File) {

    suspend fun set(password: String): String = withContext(Dispatchers.IO) {
        val cipheredPassword = Сryptographer.encrypt(password)
        val masterPasswordFile = File(storageDir, "MasterKey.txt")

        val fos = FileOutputStream(masterPasswordFile)
        fos.flush()
        fos.close()

        masterPasswordFile.writeText(cipheredPassword)
        masterPasswordFile.path
    }

    suspend fun get(): String? = withContext(Dispatchers.IO) {
        val masterPasswordFile = File(storageDir, "MasterKey.txt")
        if (!masterPasswordFile.exists()) {
            null
        } else {
            val cipheredPassword = masterPasswordFile.readText()
            Сryptographer.decrypt(cipheredPassword)
        }
    }
}