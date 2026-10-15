package com.example.data.model

data class Category(
    val id: String,
    val name: String,
    val nativeName: String,
    val iconName: String,
    val description: String,
    val count: Int,
    val heroImageUrl: String,
    val filterTags: List<String>
)

data class PlaceGallery(
    val id: String,
    val title: String,
    val imageUrl: String,
    val caption: String = ""
)

data class EtiquetteItem(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String
)

data class Review(
    val id: String,
    val userName: String,
    val userRole: String,
    val rating: Double,
    val date: String,
    val comment: String
)

data class WeatherData(
    val location: String = "Nanded, Maharashtra",
    val temperature: String = "28°C",
    val condition: String = "Sunny",
    val godavariFlow: String = "Calm",
    val airQuality: String = "Good (AQI 42)",
    val sunsetTime: String = "6:14 PM"
)

data class Place(
    val id: String,
    val name: String,
    val nativeName: String = "",
    val tagline: String = "",
    val categoryId: String,
    val subCategory: String = "",
    val rating: Double = 4.8,
    val reviewCount: Int = 1200,
    val distance: String = "1.5 km",
    val distanceKm: Double = 1.5,
    val heroImageUrl: String,
    val gallery: List<PlaceGallery> = emptyList(),
    val address: String = "Nanded, Maharashtra",
    val timings: String = "Open 24 Hours",
    val scheduleDetails: String = "",
    val entryFee: String = "Free Entry",
    val description: String = "",
    val historyText: String = "",
    val highlights: List<String> = emptyList(),
    val badges: List<String> = emptyList(),
    val dressCode: String = "",
    val langarInfo: String = "",
    val etiquetteList: List<EtiquetteItem> = emptyList(),
    val nearbyPlaceIds: List<String> = emptyList(),
    val isSpotlight: Boolean = false,
    val isTrending: Boolean = false,
    val isWeekendEscape: Boolean = false,
    val isTasteOfMarathwada: Boolean = false,
    val priceLevel: String = "Free",
    val audioGuideDuration: String? = null,
    val coordinates: String = "19.1517° N, 77.3182° E",
    val filterTag: String = "all"
)

data class Trivia(
    val id: String,
    val title: String,
    val content: String,
    val era: String = "Satavahana & Rashtrakuta"
)
