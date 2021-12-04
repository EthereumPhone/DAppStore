package org.ethereum.dappstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.joaquimverges.helium.compose.AppBlock
import com.joaquimverges.helium.core.event.EventDispatcher
import org.ethereum.dappstore.data.models.DAppInfo
import org.ethereum.dappstore.logic.AppListLogic

@Composable
fun AppListUi(logic: AppListLogic = AppListLogic()) {
    AppBlock(logic) { state, events ->
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                null, AppListLogic.State.Loading -> CircularProgressIndicator()
                AppListLogic.State.Error -> Text("Error")
                is AppListLogic.State.Loaded -> ListUi(state.data, events)
            }
        }
    }
}

@Composable
fun ListUi(data: List<DAppInfo>, events: EventDispatcher<AppListLogic.Event>) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(data.size) { index ->
            val dappInfo = data[index]
            DAppCard(dappInfo, Modifier.clickable {
                events.pushEvent(AppListLogic.Event.DAppClicked(dappInfo))
            })
        }
    }
}

@Composable
fun DAppCard(dappInfo: DAppInfo, modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = dappInfo.iconUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column() {
                Text(dappInfo.name, style = MaterialTheme.typography.h5)
                Spacer(Modifier.height(6.dp))
                Text(dappInfo.description)
            }
        }
    }
}


