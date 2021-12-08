package org.ethereum.dappstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.ethereum.dappstore.ui.Screen
import org.ethereum.dappstore.ui.components.*
import org.ethereum.dappstore.ui.theme.DAppStoreTheme
import org.ethereum.dappstore.ui.theme.Selected
import org.ethereum.dappstore.ui.theme.Unselected

class MainActivity : ComponentActivity() {

    private val bottomNavScreens =
        listOf(Screen.MainNav.Home, Screen.MainNav.User, Screen.MainNav.Settings)

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
                                bottomNavScreens.forEach { screen ->
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
                            startDestination = Screen.MainNav.Home.route
                        ) {
                            composable(Screen.MainNav.Home.route) { AppListUi(navController) }
                            composable(Screen.MainNav.User.route) { UserUi() }
                            composable(Screen.MainNav.Settings.route) { SettingsUi() }
                            composable(Screen.AppDetail.route) { navBackStackEntry ->
                                navBackStackEntry.arguments?.getString("id")?.let {
                                    AppDetailUi(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
