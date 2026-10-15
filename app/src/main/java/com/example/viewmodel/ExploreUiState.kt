package com.example.viewmodel

import com.example.data.model.Category
import com.example.data.model.Place
import com.example.data.model.WeatherData

data class HomeUiState(
    val searchQuery: String = "",
    val activeCategoryFilter: String = "historical",
    val weatherData: WeatherData = WeatherData(),
    val spotlightPlace: Place? = null,
    val trendingPlaces: List<Place> = emptyList(),
    val marathwadaPlaces: List<Place> = emptyList(),
    val weekendEscapes: List<Place> = emptyList(),
    val allCategories: List<Category> = emptyList(),
    val searchResults: List<Place> = emptyList(),
    val isSearching: Boolean = false
)

data class CategoryUiState(
    val category: Category? = null,
    val places: List<Place> = emptyList(),
    val selectedFilter: String = "All Places",
    val sortOption: String = "Highest Rated",
    val searchQuery: String = "",
    val availableFilters: List<String> = emptyList(),
    val totalCount: Int = 0
)

data class PlaceDetailUiState(
    val place: Place? = null,
    val isSaved: Boolean = false,
    val expandedAccordionIds: Set<String> = setOf("e1"), // default first accordion open
    val nearbyPlaces: List<Place> = emptyList(),
    val isAudioGuidePlaying: Boolean = false,
    val audioLanguage: String = "English & Marathi"
)

data class FavoritesUiState(
    val savedPlaces: List<Place> = emptyList(),
    val recentlyViewedPlaces: List<Place> = emptyList(),
    val activeCollection: String = "All Saved",
    val availableCollections: List<String> = listOf("All Saved", "Historical", "Spiritual", "Dining", "Weekend")
)

data class ProfileUiState(
    val userName: String = "Yatri / Visitor",
    val userSubtitle: String = "Hazur Sahib Pilgrim & Explorer",
    val isDarkMode: Boolean = false,
    val highContrast: Boolean = false,
    val audioLanguage: String = "English",
    val aartiNotifications: Boolean = true,
    val festivalAlerts: Boolean = true,
    val offlineMaps: Boolean = true
)
