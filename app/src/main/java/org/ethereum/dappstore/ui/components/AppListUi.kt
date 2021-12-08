package org.ethereum.dappstore.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.joaquimverges.helium.compose.AppBlock
import com.joaquimverges.helium.core.event.EventDispatcher
import org.ethereum.dappstore.data.models.AppInfo
import org.ethereum.dappstore.logic.AppListLogic
import org.ethereum.dappstore.ui.Screen

@Composable
fun AppListUi(nav: NavHostController, logic: AppListLogic = AppListLogic()) {
    AppBlock(logic) { state, events ->
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                null, AppListLogic.State.Loading -> CircularProgressIndicator()
                AppListLogic.State.Error -> Text("Error")
                is AppListLogic.State.Loaded -> ListUi(nav, state.data, events)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListUi(
    nav: NavHostController,
    data: List<AppInfo>,
    events: EventDispatcher<AppListLogic.Event>
) {
    SearchBarUi()
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        cells = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
    ) {
        items(data.size) { index ->
            val appInfo = data[index]
            DAppCardCompact(appInfo, Modifier.clickable {
                events.pushEvent(AppListLogic.Event.AppClicked(appInfo))
                nav.navigate(Screen.AppDetail.createRoute(appInfo))
            })
        }
    }
}

@Composable
fun DAppCardCompact(dappInfo: AppInfo, modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp),
    ) {
        Image(
            painter = rememberImagePainter(
                data = dappInfo.iconUrl,
                builder = {
                    transformations(RoundedCornersTransformation(8.dp.value))
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.height(8.dp))
        Text(
            dappInfo.category,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold
        )
        Text(dappInfo.name, style = MaterialTheme.typography.h6)
    }
}



