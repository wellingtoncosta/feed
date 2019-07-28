package io.github.wellingtoncosta.feed

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class FeedTestAppRunner  : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, FeedTestApp::class.java.name, context)
    }

}
