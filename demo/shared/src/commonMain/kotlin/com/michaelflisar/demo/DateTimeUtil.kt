package com.michaelflisar.demo

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

object DateTimeUtil {

    fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}