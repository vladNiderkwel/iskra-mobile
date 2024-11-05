package com.niderkwel.domain.models

data class UserAnswer(
    val id: Int = -1,
    val subtask: Subtask,
    val answers: List<Option> = emptyList(),
    val writtenAnswer: String = ""
)

data class UserTask(
    val id: Int = -1,
    val user: User,
    val task: Task,
    val answers: List<UserAnswer> = emptyList(),
    var result: Float = 0f,
    var status: Int = UserTaskStatus.WAITING_TO_CHECK
)

object UserTaskStatus {
    const val WAITING_TO_CHECK = 0
    const val CHECKED = 1
}