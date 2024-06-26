package com.resqiar.sendigi

import com.resqiar.sendigi.ui.screens.SchedulingScreen
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resqiar.sendigi.constants.Constants
import com.resqiar.sendigi.ui.theme.AppTheme
import com.resqiar.sendigi.ui.screens.AppLockSchedulerScreen
import com.resqiar.sendigi.ui.screens.AppLockScreen
import com.resqiar.sendigi.ui.screens.AppUsageScreen
import com.resqiar.sendigi.ui.screens.HomeScreen

import com.resqiar.sendigi.ui.screens.Screens
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer() {
    AppTheme {
        val navigationController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val context = LocalContext.current.applicationContext

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = MaterialTheme.shapes.small,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                    ) {
                        Text(text = "")
                    }

                    NavigationDrawerItem(
                        label = { Text(text = "Home") },
                        selected = false,
                        modifier = Modifier.padding(9.dp),

                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navigationController.navigate(Screens.Home.screens) {
                                popUpTo(0)
                            }
                        })
                    NavigationDrawerItem(
                        label = { Text(text = "Apps Usage Stats") },
                        selected = false,
                        modifier = Modifier.padding(9.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "AppsUsage"
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navigationController.navigate(Screens.AppsUsage.screens) {
                                popUpTo(0)
                            }
                        })

                    NavigationDrawerItem(
                        label = { Text(text = "Lock App") },
                        selected = false,
                        modifier = Modifier.padding(9.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "AppLock"
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navigationController.navigate(Screens.AppLock.screens) {
                                popUpTo(0)
                            }
                        })

                    NavigationDrawerItem(
                        label = { Text(text = "App Lock Scheduler") },
                        selected = false,
                        modifier = Modifier.padding(9.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "AppLockScheduler"
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navigationController.navigate(Screens.AppLockScheduler.screens) {
                                popUpTo(0)
                            }
                        })

                    NavigationDrawerItem(
                        label = { Text(text = "Logout") },
                        selected = false,
                        modifier = Modifier.padding(9.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout"
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()

                                // delete token
                                context.getSharedPreferences(Constants.LOG_TOKEN_PREF, Context.MODE_PRIVATE)
                                    .edit()
                                    .remove(Constants.LOG_TOKEN_PREF)
                                    .apply()

                                // Go to Login Activity
                                val intent = Intent(context, LoginActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                            }
                            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
                        })

                }
            }
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(title = { Text(text = "SenDigi") },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu Button")
                            }
                        })
                }
            ) {
                NavHost(
                    navController = navigationController,
                    startDestination = Screens.Home.screens
                ) {
                    composable(Screens.Home.screens) { HomeScreen(navController = navigationController) }
                    composable(Screens.AppsUsage.screens) { AppUsageScreen() }
                    composable(Screens.AppLock.screens) { AppLockScreen() }
                    composable(Screens.AppLockScheduler.screens) { AppLockSchedulerScreen(navController = navigationController) }
                    composable("scheduling/{packageName}/{appName}") { backStackEntry ->
                        val packageName = backStackEntry.arguments?.getString("packageName")
                        val appName = backStackEntry.arguments?.getString("appName")
                        packageName?.let { nonNullPackageName ->
                            appName?.let { nonNullAppName ->
                                SchedulingScreen(packageName = nonNullPackageName, appName = nonNullAppName)
                            }
                        }
                    }
                }
            }
        }
    }
}