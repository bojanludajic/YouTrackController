# YouTrack Discord Notifier & Issue Creator
Jetbrains internship task submission.

Kotlin tool that integrates **YouTrack** with **Discord**.
It fetches notifications from YouTrack and posts them to a Discord
channel via a Webhook. Additionally, it allows creating new YouTrack
issues directly from Discord using the `/new-issue [summary] [description]` command.

---

## Overview

- **YouTrack Notifications:** Periodically fetches notifications from
- your YouTrack instance and sends them to a Discord channel via webhook.
- **Create Issues from Discord:** Use the `/new-issue` command in Discord
- to create a new YouTrack issue with a summary and description.
---

## Requirements

- **Kotlin 1.7+**
- **YouTrack** account with API access
- **Discord** server with a bot and Webhook setup

---

## Usage

Get your YouTrack profile token: Profile > Account Security > New Token...

You need to create a Discord server and pick a channel to use.
Go to that channel's Settings > Integrations > Webhooks and create a new Webhook.
Once done, copy the Webhook URL. This simplifies the client-bot interactions.

After that, create a Discord bot, add it to your server and get its token.

Once you have all of those, create a Singleton object Secrets.kt with the
following properties:
    - YOUTRACK_API_TOKEN
    - YOUTRACK_NOTIFICATIONS_URL
    - WEBHOOK_URL
    - YOUTRACK_CREATE_ISSUE_URL
    - DISCORD_BOT_KEY

Run the project.

---

## Dependencies

The project uses the following libraries:

```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
implementation("com.google.code.gson:gson:2.10.1")
implementation("dev.kord:kord-core:0.10.0")
implementation("org.slf4j:slf4j-simple:2.0.9")
