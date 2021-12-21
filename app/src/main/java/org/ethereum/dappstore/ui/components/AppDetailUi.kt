package org.ethereum.dappstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.joaquimverges.helium.compose.AppBlock
import com.joaquimverges.helium.core.event.EventDispatcher
import org.ethereum.dappstore.data.models.AppInfo
import org.ethereum.dappstore.logic.AppDetailLogic

@Composable
fun AppDetailUi(
    appId: String,
    navController: NavController
) {
    val logic: AppDetailLogic = remember { AppDetailLogic(appId) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBlock(logic) { state, events ->
            when (state) {
                AppDetailLogic.State.Error -> Text("Error loading app info")
                null, AppDetailLogic.State.Loading -> CircularProgressIndicator()
                is AppDetailLogic.State.Loaded -> AppDetailLayout(navController, state.data, events)
            }
        }
    }
}


@Composable
fun AppDetailLayout(
    navController: NavController,
    data: AppInfo,
    events: EventDispatcher<AppDetailLogic.Event>
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.background) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }
    }, floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier.padding(bottom = 60.dp),
            shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
            backgroundColor = MaterialTheme.colors.primary,
            onClick = { events.pushEvent(AppDetailLogic.Event.InstallButtonClicked(context, data)) }
        ) {
            Row(
                Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Install")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Install",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.button
                )
            }

        }
    }) {

        DAppCardLarge(data)
    }
}

@Composable
fun DAppCardLarge(dappInfo: AppInfo) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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
                Text(
                    dappInfo.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(dappInfo.description)
            }
        }
    }
}
