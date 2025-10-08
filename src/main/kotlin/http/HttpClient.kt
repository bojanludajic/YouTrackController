package com.bojanludajic.http

import com.bojanludajic.env.Secrets
import com.google.gson.Gson
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object HttpClient {

    private const val TIMEOUT = 5000
    private const val TOKEN = Secrets.YOUTRACK_API_TOKEN
    private const val DEMO_PROJECT_ID = "0-0"


    fun getNotifications(): String {
        val urlString = Secrets.YOUTRACK_GET_NOTIFICATIONS_URL

        val url = URL(urlString)

        while (true) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", "Bearer $TOKEN")
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

    data class Project(val id: String)
    data class Issue(
        val project: Project,
        val summary: String,
        val description: String
    )

    fun createIssue(summary: String, description: String): String {
        val urlString = Secrets.YOUTRACK_CREATE_ISSUE_URL

        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Authorization", "Bearer $TOKEN")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val issue = Issue(Project(DEMO_PROJECT_ID), summary, description)
        val json = Gson().toJson(issue)

        OutputStreamWriter(connection.outputStream).use { it.write(json) }

        val responseCode = connection.responseCode

        if(responseCode in 200..299) {
            return "New issue created!"
        } else {
            return "There was an error creating your issue!"
        }
    }
}