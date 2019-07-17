package io.github.wellingtoncosta.feed.app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob

open class CoroutinesViewModel : ViewModel() {

    private val viewModelJob = SupervisorJob()

    protected val viewModeScope = CoroutineScope(Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
