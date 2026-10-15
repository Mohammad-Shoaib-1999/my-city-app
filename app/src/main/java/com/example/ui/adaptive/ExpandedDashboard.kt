package com.example.ui.adaptive

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TempleHindu
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Place
import com.example.viewmodel.ExploreViewModel

@Composable
fun ExpandedDashboard(
    viewModel: ExploreViewModel,
    selectedNav: String,
    onNavSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryState by viewModel.categoryUiState.collectAsState()
    val placeDetailState by viewModel.placeDetailUiState.collectAsState()
    val selectedPlace = placeDetailState.place ?: viewModel.repository.places.first()

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .testTag("expanded_dashboard")
    ) {
        // 1. LEFT SIDEBAR (Width 260dp)
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            modifier = Modifier
                .width(260.dp)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Logo & Godavari Live
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TempleHindu,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Explore Nanded",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Godavari Region Live",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Quick Search Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Search sacred sites...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Default.Mic, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Nav destinations
                    val navItems = listOf(
                        Triple("explore", "Explore", Icons.Default.Explore),
                        Triple("categories", "Categories", Icons.Default.Category),
                        Triple("saved", "Saved Places", Icons.Default.Bookmark),
                        Triple("profile", "Profile", Icons.Default.AccountCircle)
                    )

                    navItems.forEach { (route, label, icon) ->
                        val isSelected = selectedNav == route
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onNavSelect(route) }
                                .testTag("side_nav_$route")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Bottom Status & Pilgrim card
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("CITY STATUS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Icon(Icons.Default.WbSunny, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Nanded Ghats", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("31°C • Flowing", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Yatri / Visitor", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text("Hazur Sahib Pilgrim", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.Default.MoreVert, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // 2. MIDDLE PANE: Category & Recommendation List Pane (380dp)
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.5f),
            modifier = Modifier
                .width(380.dp)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {
                // Category Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("HERITAGE CIRCUIT", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondaryContainer) {
                        Text("${categoryState.totalCount} Sites", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(categoryState.category?.name ?: "Historical Places", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ऐतिहासिक स्थळे", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Filter search
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Filter by name, dynasty...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Filter Chips
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(categoryState.availableFilters) { filter ->
                        val isFilterActive = filter == categoryState.selectedFilter
                        Surface(
                            shape = CircleShape,
                            color = if (isFilterActive) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { viewModel.onCategoryFilterSelected(filter) }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                if (isFilterActive) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(filter, style = MaterialTheme.typography.labelSmall, fontWeight = if (isFilterActive) FontWeight.Bold else FontWeight.Medium, color = if (isFilterActive) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Places List with Selection Sync!
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categoryState.places) { place ->
                        val isCurrentSelected = place.id == selectedPlace.id
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCurrentSelected) MaterialTheme.colorScheme.surfaceContainerLowest else MaterialTheme.colorScheme.surfaceContainer
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentSelected) 3.dp else 0.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectPlace(place.id) }
                                .testTag("expanded_place_card_${place.id}")
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (isCurrentSelected) {
                                    Box(
                                        modifier = Modifier
                                            .width(5.dp)
                                            .fillMaxHeight()
                                            .background(MaterialTheme.colorScheme.primary)
                                            .align(Alignment.CenterStart)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    ) {
                                        AsyncImage(
                                            model = place.heroImageUrl,
                                            contentDescription = place.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Surface(
                                            shape = CircleShape,
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(4.dp)
                                        ) {
                                            Text("★ ${place.rating}", style = MaterialTheme.typography.labelSmall, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Surface(
                                                shape = CircleShape,
                                                color = MaterialTheme.colorScheme.secondaryContainer
                                            ) {
                                                Text(place.badges.firstOrNull() ?: place.subCategory, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                            }
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(place.distance, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(place.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text(place.address.split(",").first(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(place.timings.split("•").first(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = if (isCurrentSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* map */ },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Icon(Icons.Default.TravelExplore, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View all on Interactive Map", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 3. RIGHT PANE: Immersive Detail Pane (Expanded remaining width)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Top Bar
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Maharashtra Heritage Gateway", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondaryContainer) {
                            Text("Expanded View", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        FilledTonalButton(onClick = {}, shape = CircleShape) {
                            Icon(Icons.Default.TravelExplore, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Interactive Map", style = MaterialTheme.typography.labelMedium)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            // Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(bottom = 60.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Hero Panorama Banner
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = selectedPlace.heroImageUrl,
                                contentDescription = selectedPlace.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Black.copy(alpha = 0.4f),
                                                Color.Black.copy(alpha = 0.2f),
                                                Color.Black.copy(alpha = 0.85f)
                                            )
                                        )
                                    )
                            )
                            // Top Badges & Actions
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.9f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) {
                                            Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("ASI GRADE 1 HERITAGE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f)) {
                                        Text("One of 5 Takhts of Sikhism", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.9f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) {
                                            Icon(Icons.Default.ViewInAr, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("360° Virtual Darshan", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                    Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.9f)).clickable { viewModel.toggleFavorite(selectedPlace.id) }, contentAlignment = Alignment.Center) {
                                        Icon(if (placeDetailState.isSaved) Icons.Default.Bookmark else Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }

                            // Bottom Title & Status
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(selectedPlace.nativeName, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiaryContainer)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(selectedPlace.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("★ ${selectedPlace.rating} (${selectedPlace.reviewCount} Pilgrims Reviewed)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiaryContainer)
                                        Text("•", color = Color.White.copy(alpha = 0.7f))
                                        Text("Always Open (24 Hours)", style = MaterialTheme.typography.labelMedium, color = Color.White)
                                    }
                                }

                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
                                ) {
                                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text("GURU KA LANGAR", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Text("Continuous Seva Active", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Primary Action Bar
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {},
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Get Directions (${selectedPlace.distance})", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }

                        FilledTonalButton(
                            onClick = { viewModel.toggleAudioGuide() },
                            shape = CircleShape,
                            colors = ButtonDefaults.filledTonalButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier.height(44.dp)
                        ) {
                            Icon(Icons.Default.Headphones, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Audio Guide (42 Mins • English & Marathi)", style = MaterialTheme.typography.labelMedium)
                        }

                        FilledTonalButton(onClick = {}, shape = CircleShape, modifier = Modifier.height(44.dp)) {
                            Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Timetable & Daily Aarti", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }

                // 4 Bento Metrics Cards
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val metrics = listOf(
                            Triple("Railway Link", "Station Road", "1.2 km from Nanded Rly"),
                            Triple("Daily Schedule", "6:30 PM Rehras", "Evening Aarti & Shabad"),
                            Triple("Community Meal", "Langar 24x7", "Free meals for 25,000+"),
                            Triple("Sanctum Protocol", "Head Covered", "Rumals provided free")
                        )
                        metrics.forEach { (label, value, sub) ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(sub, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }

                // Historical Significance & Narrative Grid
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Left Narrative (60%)
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.weight(1.4f)
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Historical Significance & Spiritual Seat", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Text("Est. 1708 C.E.", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(selectedPlace.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, lineHeight = 22.sp)

                                Spacer(modifier = Modifier.height(14.dp))

                                // Gallery strip inside card
                                Text("Sanctum & Precinct Gallery", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(8.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    items(selectedPlace.gallery) { g ->
                                        Column(modifier = Modifier.width(130.dp)) {
                                            Box(modifier = Modifier.size(130.dp, 90.dp).clip(RoundedCornerShape(12.dp))) {
                                                AsyncImage(model = g.imageUrl, contentDescription = g.title, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(g.title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, maxLines = 1)
                                        }
                                    }
                                }
                            }
                        }

                        // Right Info Column (40%)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Visitor Etiquette Accordion Box
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Visitor Etiquette & Codes", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    selectedPlace.etiquetteList.forEach { et ->
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                                                .padding(10.dp)
                                        ) {
                                            Text(et.title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(et.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                            }

                            // Geographic Access widget
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("Geographic Access", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Coordinates: ${selectedPlace.coordinates}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.PinDrop, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text("Open Maps", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Nearby Heritage on the Godavari Circuit
                item {
                    Text("Nearby Heritage on the Godavari Circuit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val nearbyList = placeDetailState.nearbyPlaces
                        nearbyList.forEach { nb ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.selectPlace(nb.id) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(modifier = Modifier.size(60.dp).clip(RoundedCornerShape(10.dp))) {
                                        AsyncImage(model = nb.heroImageUrl, contentDescription = nb.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(nb.distance, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                        Text(nb.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1)
                                        Text(nb.subCategory, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                    }
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
