package io.github.wellingtoncosta.feed.network

import androidx.test.espresso.idling.CountingIdlingResource
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import io.github.wellingtoncosta.feed.infrastructure.network.api.UserApi
import io.github.wellingtoncosta.feed.infrastructure.network.api.fuel.HTTP_200
import io.github.wellingtoncosta.feed.infrastructure.network.entity.UserResponse
import kotlinx.serialization.json.Json

class UserFuelApi(
    private val json: Json,
    private val idlingResource: CountingIdlingResource
) : UserApi {

    override suspend fun getUserById(userId: Long) =
        Fuel.get("/users/$userId")
            .also { idlingResource.increment() }
            .awaitStringResponse()
            .run {
                if(second.statusCode != HTTP_200) null
                else json.parse(UserResponse.serializer(), third)
            }.also { idlingResource.decrement() }

}