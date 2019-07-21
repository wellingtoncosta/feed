package io.github.wellingtoncosta.feed.extensions

fun String.asJson(): String = javaClass.getResource(this)?.readBytes()?.let { String(it) }.toString()
