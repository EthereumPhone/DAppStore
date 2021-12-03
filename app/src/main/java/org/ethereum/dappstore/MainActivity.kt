package org.ethereum.dappstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ethereum.dappstore.ui.components.AppListUi
import org.ethereum.dappstore.ui.theme.DAppStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DAppStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(topBar = {
                        TopAppBar() {
                            Text(
                                "DAppStore",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    },
                        bottomBar = {
                            BottomNavigation() {
                                BottomNavigationItem(
                                    selected = true,
                                    onClick = { /*TODO*/ },
                                    icon = {
                                        Icon(Icons.Rounded.Home, contentDescription = "Home")
                                    })
                                BottomNavigationItem(
                                    selected = false,
                                    onClick = { /*TODO*/ },
                                    icon = {
                                        Icon(Icons.Rounded.Person, contentDescription = "Home")
                                    })
                                BottomNavigationItem(
                                    selected = false,
                                    onClick = { /*TODO*/ },
                                    icon = {
                                        Icon(Icons.Rounded.Menu, contentDescription = "Home")
                                    })
                            }
                        }) {
                        AppListUi()
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