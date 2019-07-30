package io.github.wellingtoncosta.feed

fun String.asJson(): String = javaClass.getResource(this)?.readBytes()?.let { String(it) }.toString()
