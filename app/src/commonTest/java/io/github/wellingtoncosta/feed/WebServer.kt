package io.github.wellingtoncosta.feed

import okhttp3.mockwebserver.MockWebServer

object WebServer {

    lateinit var server: MockWebServer private set

    fun start(body: (String) -> Unit) {
        server = MockWebServer().apply { body(url("/").toString()) }
    }

    fun shutdown() {
        server.shutdown()
    }

}
