package com.bojanludajic

import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.*
import java.util.zip.GZIPInputStream

object NotificationProcessor {

    private val regexCreatedAt = Regex("""Created at\s+([0-9]{2}\s\w+\s[0-9]{4}\s[0-9]{2}:[0-9]{2})""")
    private val regexComment = Regex("""Comment:\s*(.*?)\s*<""", RegexOption.DOT_MATCHES_ALL)

    fun processJson(response: String) {
        val jsonArray = JsonParser.parseString(response).asJsonArray

        for (item in jsonArray) {
            val obj = item.asJsonObject
            val id = obj["id"].asString
            val contentEncoded = obj["content"].asString

            val contentDecoded = decodeGzipBase64(contentEncoded)

            extractFields(id, contentDecoded)
            println("-----------------------------------------------------")
        }
    }

    private fun decodeGzipBase64(encoded: String): String {
        val decodedBytes = Base64.getDecoder().decode(encoded)
        GZIPInputStream(ByteArrayInputStream(decodedBytes)).use { gis ->
            return BufferedReader(InputStreamReader(gis)).readText()
        }
    }

    private fun extractFields(id: String, content: String) {
        val createdAt = regexCreatedAt.find(content)?.groupValues?.get(1)?.trim()
        val comment = regexComment.find(content)?.groupValues?.get(1)?.trim()

        println("Notification ID: $id")
        println("Created at: ${createdAt ?: "(none)"}")
        println("Comment: ${comment ?: "(none)"}")
    }

}