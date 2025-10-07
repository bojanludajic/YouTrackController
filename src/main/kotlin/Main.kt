package com.bojanludajic

import com.bojanludajic.http.HttpClient
import com.bojanludajic.notifications.NotificationProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    while(true) runBlocking {
        val data = HttpClient.getNotifications()
        NotificationProcessor.processJson(data)
        delay(5000)
    }
}