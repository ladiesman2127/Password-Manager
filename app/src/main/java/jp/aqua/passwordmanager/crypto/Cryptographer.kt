package jp.aqua.passwordmanager.crypto

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Сryptographer {
    companion object {
        private val _key = SecretKeySpec(
            "e8c7a20df12a945f6b3c84dc5b3fc1e0".toByteArray(),
            "AES"
        )
        private val _iv = IvParameterSpec("f0e1d2c3b4a59687".toByteArray())
        private const val ALGORITHM = "AES/OFB/PKCS5Padding"

        fun decrypt(
            cipherText: String?,
        ): String {
            if(cipherText == null) return ""
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, _key, _iv)
            val plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText))
            return String(plainText)
        }

        fun encrypt(
            inputText: String,
        ): String {
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, _key, _iv)
            val cipherText = cipher.doFinal(inputText.toByteArray())
            return Base64.getEncoder().encodeToString(cipherText)
        }
    }
}