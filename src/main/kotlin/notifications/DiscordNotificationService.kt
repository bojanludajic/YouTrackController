package com.bojanludajic.notifications

import com.bojanludajic.env.Secrets
import java.net.HttpURLConnection
import java.net.URL

object DiscordNotificationService: NotificationService {

    override fun sendMessage(message: String) {
        try {
            val url = URL(Secrets.WEBHOOK_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val payload = """{"content": "${escapeJson(message)}"}"""

            connection.outputStream.use { os ->
                os.write(payload.toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
            if (responseCode !in 200..299) {
                val error = connection.errorStream?.bufferedReader()?.readText()
                println("Discord webhook error ($responseCode): $error")
            }

        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    private fun escapeJson(text: String): String {
        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
    }
}