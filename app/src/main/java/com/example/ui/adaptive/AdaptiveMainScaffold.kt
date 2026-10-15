package com.example.ui.adaptive

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.category.CategoryScreen
import com.example.ui.details.PlaceDetailScreen
import com.example.ui.favorites.FavoritesScreen
import com.example.ui.home.HomeScreen
import com.example.ui.profile.ProfileScreen
import com.example.viewmodel.ExploreViewModel

sealed class Screen {
    object Home : Screen()
    object Categories : Screen()
    object Saved : Screen()
    object Profile : Screen()
    data class PlaceDetail(val placeId: String, val previousScreen: Screen) : Screen()
}

@Composable
fun AdaptiveMainScaffold(
    viewModel: ExploreViewModel,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val width = maxWidth
        val isExpanded = width >= 840.dp
        val isMedium = width in 600.dp..839.dp

        if (isExpanded) {
            var selectedSideNav by remember { mutableStateOf("categories") }
            ExpandedDashboard(
                viewModel = viewModel,
                selectedNav = selectedSideNav,
                onNavSelect = { selectedSideNav = it }
            )
        } else {
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

            // Handle back navigation
            BackHandler(enabled = currentScreen !is Screen.Home) {
                currentScreen = when (val s = currentScreen) {
                    is Screen.PlaceDetail -> s.previousScreen
                    else -> Screen.Home
                }
            }

            if (isMedium) {
                // Medium layout: NavigationRail + Content
                Row(modifier = Modifier.fillMaxSize()) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .fillMaxHeight()
                            .testTag("medium_nav_rail")
                    ) {
                        val navItems = listOf(
                            Triple(Screen.Home, "Explore", Icons.Default.Explore),
                            Triple(Screen.Categories, "Categories", Icons.Default.GridView),
                            Triple(Screen.Saved, "Saved", Icons.Default.Bookmark),
                            Triple(Screen.Profile, "Profile", Icons.Default.AccountCircle)
                        )

                        navItems.forEach { (screen, label, icon) ->
                            val isSelected = currentScreen::class == screen::class
                            NavigationRailItem(
                                selected = isSelected,
                                onClick = { currentScreen = screen },
                                icon = { Icon(icon, contentDescription = label) },
                                label = { Text(label) },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            )
                        }
                    }

                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        ScreenContent(
                            currentScreen = currentScreen,
                            viewModel = viewModel,
                            onNavigateToScreen = { currentScreen = it }
                        )
                    }
                }
            } else {
                // Compact Layout: Mobile Scaffold with Bottom Navigation Bar
                Scaffold(
                    bottomBar = {
                        val isDetail = currentScreen is Screen.PlaceDetail
                        if (!isDetail) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.95f),
                                tonalElevation = 3.dp,
                                modifier = Modifier.testTag("compact_bottom_nav")
                            ) {
                                val navItems = listOf(
                                    Triple(Screen.Home, "Explore", Icons.Default.Explore),
                                    Triple(Screen.Categories, "Categories", Icons.Default.GridView),
                                    Triple(Screen.Saved, "Saved", Icons.Default.Bookmark),
                                    Triple(Screen.Profile, "Profile", Icons.Default.AccountCircle)
                                )

                                navItems.forEach { (screen, label, icon) ->
                                    val isSelected = currentScreen::class == screen::class
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = { currentScreen = screen },
                                        icon = {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = label,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        ScreenContent(
                            currentScreen = currentScreen,
                            viewModel = viewModel,
                            onNavigateToScreen = { currentScreen = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenContent(
    currentScreen: Screen,
    viewModel: ExploreViewModel,
    onNavigateToScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(targetState = currentScreen, label = "screen_crossfade", modifier = modifier.fillMaxSize()) { screen ->
        when (screen) {
            is Screen.Home -> {
                HomeScreen(
                    viewModel = viewModel,
                    onPlaceClick = { placeId ->
                        onNavigateToScreen(Screen.PlaceDetail(placeId, Screen.Home))
                    },
                    onCategoryClick = { categoryId ->
                        viewModel.selectCategory(categoryId)
                        onNavigateToScreen(Screen.Categories)
                    },
                    onProfileClick = {
                        onNavigateToScreen(Screen.Profile)
                    }
                )
            }
            is Screen.Categories -> {
                CategoryScreen(
                    viewModel = viewModel,
                    onPlaceClick = { placeId ->
                        onNavigateToScreen(Screen.PlaceDetail(placeId, Screen.Categories))
                    },
                    onProfileClick = {
                        onNavigateToScreen(Screen.Profile)
                    }
                )
            }
            is Screen.Saved -> {
                FavoritesScreen(
                    viewModel = viewModel,
                    onPlaceClick = { placeId ->
                        onNavigateToScreen(Screen.PlaceDetail(placeId, Screen.Saved))
                    },
                    onProfileClick = {
                        onNavigateToScreen(Screen.Profile)
                    }
                )
            }
            is Screen.Profile -> {
                ProfileScreen(viewModel = viewModel)
            }
            is Screen.PlaceDetail -> {
                PlaceDetailScreen(
                    placeId = screen.placeId,
                    viewModel = viewModel,
                    onBackClick = {
                        onNavigateToScreen(screen.previousScreen)
                    },
                    onPlaceClick = { newPlaceId ->
                        onNavigateToScreen(Screen.PlaceDetail(newPlaceId, screen.previousScreen))
                    },
                    onProfileClick = {
                        onNavigateToScreen(Screen.Profile)
                    }
                )
            }
        }
    }
}
