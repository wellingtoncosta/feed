package io.github.wellingtoncosta.feed.app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

open class CoroutinesViewModel : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModeScope = CoroutineScope(Main + viewModelJob)

    fun launch(block: suspend CoroutineScope.() -> Unit): Job =
        CoroutinesIdlingResource.idlingResource.increment().let {
            viewModeScope.launch(block = block).also {
                it.invokeOnCompletion { CoroutinesIdlingResource.idlingResource.decrement() }
            }
        }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
