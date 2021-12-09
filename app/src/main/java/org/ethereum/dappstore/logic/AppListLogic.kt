package org.ethereum.dappstore.logic

import com.joaquimverges.helium.core.LogicBlock
import com.joaquimverges.helium.core.event.BlockEvent
import com.joaquimverges.helium.core.state.BlockState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ethereum.dappstore.data.DataRepository
import org.ethereum.dappstore.data.models.AppInfo

class AppListLogic(
    private val repository: DataRepository = DataRepository
) : LogicBlock<AppListLogic.State, AppListLogic.Event>() {
    sealed class State : BlockState {
        object Loading : State()
        object Error : State()
        data class Loaded(val data: List<AppInfo>) : State()
    }

    sealed class Event : BlockEvent {
        data class AppClicked(val info: AppInfo) : Event()
    }

    init {
        launchInBlock {
            pushState(State.Loading)
            withContext(Dispatchers.IO) {
                repository.fetchApps()?.let {
                    pushState(State.Loaded(it))
                } ?: pushState(State.Error)
            }
        }
    }

    override fun onUiEvent(event: Event) {
        when (event) {
            is Event.AppClicked -> { /* TODO */
            }
        }
    }

}