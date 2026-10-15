package com.example.ui.favorites

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.category.CategoryRecommendationCard
import com.example.ui.components.AppHeader
import com.example.ui.components.TrendingPlaceCard
import com.example.viewmodel.ExploreViewModel

@Composable
fun FavoritesScreen(
    viewModel: ExploreViewModel,
    onPlaceClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.favoritesUiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .testTag("favorites_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // App Header
        item {
            AppHeader(
                title = "Saved Places",
                subtitle = "Your Curated Itinerary",
                onProfileClick = onProfileClick
            )
        }

        // Collection Chips
        item {
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.availableCollections) { col ->
                    val isSelected = col == uiState.activeCollection
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { viewModel.onFavoritesCollectionSelected(col) }
                            .testTag("collection_chip_$col")
                    ) {
                        Text(
                            text = col,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Saved Places Items or Empty State
        if (uiState.savedPlaces.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(60.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No saved places in this collection",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tap the bookmark icon on any sacred shrine, fort, or cafe to build your pilgrimage & sightseeing list.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "${uiState.savedPlaces.size} Saved Destinations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(uiState.savedPlaces) { place ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    CategoryRecommendationCard(
                        place = place,
                        isFavorite = true,
                        onFavoriteToggle = { viewModel.toggleFavorite(place.id) },
                        onExploreClick = { onPlaceClick(place.id) }
                    )
                }
            }
        }

        // Recently Viewed Horizontal Strip
        if (uiState.recentlyViewedPlaces.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Recently Viewed",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.recentlyViewedPlaces) { place ->
                        TrendingPlaceCard(
                            place = place,
                            onClick = { onPlaceClick(place.id) }
                        )
                    }
                }
            }
        }
    }
}
