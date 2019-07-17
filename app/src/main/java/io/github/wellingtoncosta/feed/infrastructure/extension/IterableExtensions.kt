package io.github.wellingtoncosta.feed.infrastructure.extension

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

suspend fun <T, R> Iterable<T>.asyncMap(body: suspend (T) -> R) = runBlocking {
    map { async { body(it) } }.map { it.await() }
}
