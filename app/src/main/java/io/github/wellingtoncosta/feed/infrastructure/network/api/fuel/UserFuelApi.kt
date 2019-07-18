package io.github.wellingtoncosta.feed.infrastructure.network.api.fuel

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import io.github.wellingtoncosta.feed.BuildConfig
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.network.entity.UserResponse
import kotlinx.serialization.json.Json

class UserFuelApi(
    private val apiUrl: String = BuildConfig.API_URL,
    private val json: Json
) : UserApi {

    override suspend fun getUserById(userId: Long) =
        Fuel.get("$apiUrl/users/$userId").awaitStringResponse().run {
            if(second.statusCode != HTTP_200) null
            else json.parse(UserResponse.serializer(), third)
        }

}
