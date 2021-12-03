package org.ethereum.dappstore.logic

import android.widget.Toast
import com.joaquimverges.helium.core.LogicBlock
import com.joaquimverges.helium.core.event.BlockEvent
import com.joaquimverges.helium.core.state.BlockState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ethereum.dappstore.data.DataRepository
import org.ethereum.dappstore.data.models.DAppInfo

class AppListLogic(
    private val repository: DataRepository = DataRepository()
) : LogicBlock<AppListLogic.State, AppListLogic.Event>() {
    sealed class State : BlockState {
        object Loading : State()
        object Error : State()
        data class Loaded(val data: List<DAppInfo>) : State()
    }

    sealed class Event : BlockEvent {
        data class DAppClicked(val info: DAppInfo) : Event()
    }

    init {
        launchInBlock {
            pushState(State.Loading)
            withContext(Dispatchers.IO) {
                val data = repository.fetchApps()
                pushState(State.Loaded(data))
            }
        }
    }

    override fun onUiEvent(event: Event) {
        when (event) {
            is Event.DAppClicked -> { /* TODO */ }
        }
    }

}