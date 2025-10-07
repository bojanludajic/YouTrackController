package com.bojanludajic.bot

import com.bojanludajic.env.Secrets
import com.bojanludajic.http.HttpClient
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.respondPublic
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.string
import kotlinx.coroutines.runBlocking

object CommandHandler {

    fun handleNewIssue() = runBlocking {
        val botToken = Secrets.DISCORD_BOT_KEY
        val kord = Kord(botToken)

        kord.createGlobalChatInputCommand("create-issue", "Create new YouTrack issue.") {
            string("summary", "Issue name.") {
                required = true
            }
            string("description", "Issue description.") {
                required = true
            }
        }

        kord.on<ChatInputCommandInteractionCreateEvent> {
            if(interaction.command.rootName == "create-issue") {
                val summary = interaction.command.strings["summary"]!!
                val description = interaction.command.strings["description"]!!

                val response = HttpClient.createIssue(summary, description)

                interaction.respondPublic {
                    content = response
                }
            }
        }

        kord.login()
    }
}