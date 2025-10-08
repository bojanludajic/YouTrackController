package com.bojanludajic

import com.bojanludajic.bot.CommandHandler
import com.bojanludajic.http.HttpClient
import com.bojanludajic.notifications.NotificationProcessor
import kotlinx.coroutines.*

const val FIVE_SECONDS = 5000L

fun main() = runBlocking {
    val job1 = launch {
        while (isActive) {
            val data = HttpClient.getNotifications()
            NotificationProcessor.processJson(data)
            delay(FIVE_SECONDS)
        }
    }

    val job2 = launch {
        while(isActive) {
            CommandHandler.handleNewIssue()
        }
    }

    joinAll(job1, job2)

}