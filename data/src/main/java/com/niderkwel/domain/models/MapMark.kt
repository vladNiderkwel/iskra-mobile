package com.niderkwel.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapMark(
    val id: Int,
    val name: String,
    val type: Int,
    val coordinates: List<Float>
) {
    fun typeName(): String {
        return when(type) {
            0 -> "Общий"
            1 -> "Одежда"
            2 -> "Батарейки"
            3 -> "Металлолом"
            4 -> "Стекло"
            5 -> "Вторсырье"
            6 -> "Пластик"
            7 -> "Бытовая техника"
            else -> ""
        }
    }
}
