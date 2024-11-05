package com.niderkwel.iskra_mobile.components.screens.post

import com.niderkwel.domain.models.Post
import com.niderkwel.iskra_mobile.components.UiStatus

data class PostsState(
    val uiStatus: UiStatus = UiStatus.Success,
    val posts: List<Post> = emptyList()
       /* listOf(
            Post(
                id = -1,
                title = "Заголовок Заголовок Заголовок Заголовок Заголовок",
                description = "Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого постаМаленькое описание этого поста",
                publicationDate = LocalDateTime.now(),
                body = "",
                photoUrl = "",
                author = Staff(
                    id = -1,
                    name = "Имя Тестера",
                    email = "d@d.d",
                )
            ),

            Post(
                id = -1,
                title = "Заголовок Заголовок Заголовок Заголовок Заголовок",
                description = "Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого постаМаленькое описание этого поста",
                publicationDate = LocalDateTime.now(),
                body = "",
                photoUrl = "",
                author = Staff(
                    id = -1,
                    name = "Имя Тестера",
                    email = "d@d.d",
                )
            ),
            Post(
                id = -1,
                title = "Заголовок Заголовок Заголовок Заголовок Заголовок",
                description = "Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого постаМаленькое описание этого поста",
                publicationDate = LocalDateTime.now(),
                body = "",
                photoUrl = "",
                author = Staff(
                    id = -1,
                    name = "Имя Тестера",
                    email = "d@d.d",
                )
            )
        )*/
)