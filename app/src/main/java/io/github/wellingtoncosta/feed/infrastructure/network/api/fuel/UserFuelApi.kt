package io.github.wellingtoncosta.feed.infrastructure.network.api.fuel

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.UserResponse
import kotlinx.serialization.json.Json

class UserFuelApi(
    private val json: Json
) : UserApi {

    override suspend fun getUserById(userId: Long) =
        Fuel.get("/users/$userId").awaitStringResponse().run {
            when(second.statusCode) {
                HTTP_200, HTTP_304 -> json.parse(UserResponse.serializer(), third)
                else -> null
            }
        }

}
