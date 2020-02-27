package org.magmafoundation.reflex.utils


import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


object IOUtils {
    fun downloadFile(file: File, url: String) {
        if (!file.exists()) {
            try {
                println("Downloading file $url")
                BufferedInputStream(URL(url).openStream()).use { `in` ->
                    FileOutputStream(file).use { fileOutputStream ->
                        val dataBuffer = ByteArray(1024)
                        var bytesRead = 0;
                        while (`in`.read(dataBuffer, 0, 1024).also { bytesRead = it } != -1) {
                            fileOutputStream.write(dataBuffer, 0, bytesRead)
                        }
                    }
                }
            } catch (e: IOException) { // handle exception
                e.printStackTrace();
            }
        }
    }
}