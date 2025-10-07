package com.bojanludajic

import com.bojanludajic.env.Secrets
import java.net.HttpURLConnection
import java.net.URL

object HttpClient {

    private const val TIMEOUT = 5000

    fun getNotifications(): String {
        val token = Secrets.YOUTRACK_API_TOKEN
        val urlString = Secrets.YOUTRACK_URL

        val url = URL(urlString)

        while (true) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $token")
                connection.setRequestProperty("Accept", "application/json")
                connection.connectTimeout = TIMEOUT
                connection.readTimeout = TIMEOUT

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }

                    return response
                } else {
                    val error = connection.errorStream?.bufferedReader()?.use { it.readText() }
                    println("Error $responseCode: $error")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}