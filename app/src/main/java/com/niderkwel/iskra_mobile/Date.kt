package com.niderkwel.iskra_mobile

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun dateString(date: LocalDateTime): String =
    date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))