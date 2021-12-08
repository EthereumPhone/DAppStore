package org.ethereum.dappstore.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import org.ethereum.dappstore.data.models.AppInfo

sealed class Screen(open val route: String) {
    sealed class MainNav(override val route: String, val icon: ImageVector) : Screen(route) {
        object Home : MainNav("profile", Icons.Rounded.Home)
        object User : MainNav("user", Icons.Rounded.Person)
        object Settings : MainNav("settings", Icons.Rounded.Menu)
    }


    object AppDetail: Screen("dapp/{id}") {
        fun createRoute(info: AppInfo) = "dapp/${info.id}"
    }
}