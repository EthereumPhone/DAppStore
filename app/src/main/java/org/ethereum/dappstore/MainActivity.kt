package org.ethereum.dappstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDirections
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.ethereum.dappstore.ui.components.AppListUi
import org.ethereum.dappstore.ui.components.SearchBarUi
import org.ethereum.dappstore.ui.components.SettingsUi
import org.ethereum.dappstore.ui.components.UserUi
import org.ethereum.dappstore.ui.theme.DAppStoreTheme
import org.ethereum.dappstore.ui.theme.Selected
import org.ethereum.dappstore.ui.theme.Unselected

class MainActivity : ComponentActivity() {

    sealed class Screen(val route: String, val icon: ImageVector) {
        object Home : Screen("profile", Icons.Rounded.Home)
        object User : Screen("user", Icons.Rounded.Person)
        object Settings : Screen("settings", Icons.Rounded.Menu)
    }

    private val screens = listOf(Screen.Home, Screen.User, Screen.Settings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            DAppStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(topBar = {
                        SearchBarUi()
                    },
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = MaterialTheme.colors.surface,
                                elevation = 0.dp
                            ) {

                                screens.forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = { navController.navigate(screen.route) },
                                        selectedContentColor = Selected,
                                        unselectedContentColor = Unselected,
                                        icon = {
                                            Icon(screen.icon, contentDescription = screen.route)
                                        })
                                }
                            }
                        }) {

                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route
                        ) {
                            composable(Screen.Home.route) { AppListUi() }
                            composable(Screen.User.route) { UserUi() }
                            composable(Screen.Settings.route) { SettingsUi() }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DAppStoreTheme {
        AppListUi()
    }
}