package io.github.wellingtoncosta.feed.mock

import io.github.wellingtoncosta.feed.domain.entity.User
import io.github.wellingtoncosta.feed.infrastructure.network.entity.UserResponse

object UserMock {

    val oneUser: User get() = User(
        id = 1L,
        name = "String",
        username = "String",
        phone = "String",
        email = "String"
    )

    val oneUserResponse: UserResponse get() = oneUser.toResponse()

    private fun User.toResponse() = UserResponse(
        id = this.id,
        name = this.name,
        username = this.username,
        phone = this.phone,
        email = this.email
    )

}