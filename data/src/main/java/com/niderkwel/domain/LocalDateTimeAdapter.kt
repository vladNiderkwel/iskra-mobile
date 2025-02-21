package com.niderkwel.domain

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateAsStringAdapter : JsonAdapter<LocalDateTime>() {
    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        writer.value(value?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }

    @FromJson
    override fun fromJson(p0: JsonReader): LocalDateTime? =
        LocalDateTime.parse(p0.nextString())
}