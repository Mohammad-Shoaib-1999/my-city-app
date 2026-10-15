package com.example.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TempleHindu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.AppHeader
import com.example.ui.components.HeroSpotlightCard
import com.example.ui.components.MarathwadaFoodCard
import com.example.ui.components.RiverGhatMiniBanner
import com.example.ui.components.SearchAndWeatherBar
import com.example.ui.components.TouristHelplineCard
import com.example.ui.components.TrendingPlaceCard
import com.example.ui.components.WeekendEscapeCard
import com.example.viewmodel.ExploreViewModel

@Composable
fun HomeScreen(
    viewModel: ExploreViewModel,
    onPlaceClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.homeUiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // App Header
        item {
            AppHeader(
                title = "Explore",
                subtitle = "Nanded, MH",
                onSearchClick = { /* focus search input */ },
                onProfileClick = onProfileClick
            )
        }

        // Search & Weather Bar
        item {
            Spacer(modifier = Modifier.height(6.dp))
            SearchAndWeatherBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onHomeSearchQueryChanged(it) },
                weatherData = uiState.weatherData
            )
        }

        // Quick Category Horizontal Filter Chips
        item {
            Spacer(modifier = Modifier.height(14.dp))
            val categories = uiState.allCategories
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isActive = cat.id == uiState.activeCategoryFilter
                    val icon = getCategoryIcon(cat.iconName)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isActive) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                viewModel.onHomeCategorySelected(cat.id)
                                onCategoryClick(cat.id)
                            }
                            .testTag("category_chip_${cat.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = cat.name,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                color = if (isActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // If Search Query Active -> Show Instant Search Results
        if (uiState.isSearching) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "Search Results (${uiState.searchResults.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            items(uiState.searchResults) { place ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    WeekendEscapeCard(
                        place = place,
                        onClick = { onPlaceClick(place.id) }
                    )
                }
            }
        } else {
            // Sacred Spotlight Section
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Sacred Spotlight",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = "Live Darshan Open",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    uiState.spotlightPlace?.let { place ->
                        HeroSpotlightCard(
                            place = place,
                            isFavorite = viewModel.isFavorite(place.id),
                            onFavoriteToggle = { viewModel.toggleFavorite(place.id) },
                            onExploreClick = { onPlaceClick(place.id) },
                            onAudioGuideClick = {
                                viewModel.selectPlace(place.id)
                                viewModel.toggleAudioGuide()
                                onPlaceClick(place.id)
                            }
                        )
                    }
                }
            }

            // Trending in Nanded Carousel
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Trending in Nanded",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Top-rated spots by pilgrims and locals",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        TextButton(
                            onClick = { onCategoryClick("historical") },
                            modifier = Modifier.testTag("trending_view_all_btn")
                        ) {
                            Text(
                                text = "View all",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(uiState.trendingPlaces) { place ->
                            TrendingPlaceCard(
                                place = place,
                                onClick = { onPlaceClick(place.id) }
                            )
                        }
                    }
                }
            }

            // River Experience Accent Banner
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    RiverGhatMiniBanner(
                        onClick = { onPlaceClick("nagina-ghat") }
                    )
                }
            }

            // Taste of Marathwada Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Taste of Marathwada",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Iconic flavors, spicy gravies & modern hangs",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val foodList = uiState.marathwadaPlaces.take(2)
                        foodList.forEach { food ->
                            Box(modifier = Modifier.weight(1f)) {
                                MarathwadaFoodCard(
                                    place = food,
                                    onClick = { onPlaceClick(food.id) }
                                )
                            }
                        }
                    }
                }
            }

            // Weekend Escapes Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Weekend Escapes",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Day tours & road trips under 2 hours",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TextButton(onClick = { onCategoryClick("weekend-getaways") }) {
                            Text(
                                text = "Explore",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    uiState.weekendEscapes.take(2).forEach { escape ->
                        WeekendEscapeCard(
                            place = escape,
                            onClick = { onPlaceClick(escape.id) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            // Civic Helpline Banner
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    TouristHelplineCard(
                        onCallClick = { /* launch dialer */ }
                    )
                }
            }
        }
    }
}

fun getCategoryIcon(iconName: String): ImageVector {
    return when (iconName) {
        "castle" -> Icons.Default.Explore
        "temple_hindu" -> Icons.Default.TempleHindu
        "coffee" -> Icons.Default.Coffee
        "restaurant" -> Icons.Default.Restaurant
        "park" -> Icons.Default.Park
        "diamond" -> Icons.Default.Diamond
        "explore" -> Icons.Default.Explore
        else -> Icons.Default.Explore
    }
}
