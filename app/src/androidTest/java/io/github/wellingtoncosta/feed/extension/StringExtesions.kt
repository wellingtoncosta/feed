package io.github.wellingtoncosta.feed.extension

import androidx.test.platform.app.InstrumentationRegistry

fun String.asJson() = InstrumentationRegistry.getInstrumentation().context.assets
    .open(this)
    .readBytes().let { String(it) }.toString()