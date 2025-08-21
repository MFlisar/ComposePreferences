package com.michaelflisar.composepreferences.demo

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object DateTimeUtil {

    @OptIn(ExperimentalTime::class)
    fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}