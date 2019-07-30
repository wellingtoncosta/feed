package io.github.wellingtoncosta.feed.extensions

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

fun dispatches(body: (String) -> MockResponse) = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return body(request.path ?: "")
    }
}

infix fun Int.responses(payload: String?) = MockResponse()
    .setResponseCode(this)
    .addHeader("Content-Type", "application/json;charset=utf-8")
    .also { if(payload != null) it.setBody(payload) }
