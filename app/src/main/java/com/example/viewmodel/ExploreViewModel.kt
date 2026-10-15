package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Category
import com.example.data.model.Place
import com.example.data.repository.ExploreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ExploreViewModel(
    val repository: ExploreRepository = ExploreRepository()
) : ViewModel() {

    // Home UI State
    private val _homeSearchQuery = MutableStateFlow("")
    private val _homeCategoryFilter = MutableStateFlow("historical")

    val homeUiState: StateFlow<HomeUiState> = combine(
        _homeSearchQuery,
        _homeCategoryFilter,
        repository.favorites
    ) { query, categoryFilter, _ ->
        val spotlight = repository.places.find { it.isSpotlight } ?: repository.places.first()
        val trending = repository.places.filter { it.isTrending }
        val marathwada = repository.places.filter { it.isTasteOfMarathwada }
        val weekends = repository.places.filter { it.isWeekendEscape }
        val categories = repository.categories

        val isSearching = query.isNotBlank()
        val searchResults = if (isSearching) repository.searchPlaces(query) else emptyList()

        HomeUiState(
            searchQuery = query,
            activeCategoryFilter = categoryFilter,
            weatherData = repository.weatherData,
            spotlightPlace = spotlight,
            trendingPlaces = trending,
            marathwadaPlaces = marathwada,
            weekendEscapes = weekends,
            allCategories = categories,
            searchResults = searchResults,
            isSearching = isSearching
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = HomeUiState()
    )

    fun onHomeSearchQueryChanged(newQuery: String) {
        _homeSearchQuery.value = newQuery
    }

    fun onHomeCategorySelected(categoryId: String) {
        _homeCategoryFilter.value = categoryId
    }

    // Category Screen State
    private val _currentCategoryId = MutableStateFlow("historical")
    private val _categoryFilterTag = MutableStateFlow("All Places")
    private val _categorySortOption = MutableStateFlow("Highest Rated")
    private val _categorySearch = MutableStateFlow("")

    val categoryUiState: StateFlow<CategoryUiState> = combine(
        _currentCategoryId,
        _categoryFilterTag,
        _categorySortOption,
        _categorySearch,
        repository.favorites
    ) { categoryId, filterTag, sort, search, _ ->
        val category = repository.categories.find { it.id == categoryId }
            ?: repository.categories.first()
        
        var list = repository.places.filter {
            if (categoryId == "historical") {
                it.categoryId == "historical" || it.categoryId == "spiritual"
            } else {
                it.categoryId == categoryId
            }
        }

        if (search.isNotBlank()) {
            val q = search.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.nativeName.lowercase().contains(q) ||
                it.description.lowercase().contains(q) ||
                it.subCategory.lowercase().contains(q)
            }
        }

        if (filterTag != "All Places") {
            list = list.filter { it.filterTag.equals(filterTag, ignoreCase = true) || it.subCategory.contains(filterTag, ignoreCase = true) }
        }

        list = when (sort) {
            "Highest Rated" -> list.sortedByDescending { it.rating }
            "Closest First" -> list.sortedBy { it.distanceKm }
            "Most Popular" -> list.sortedByDescending { it.reviewCount }
            else -> list
        }

        CategoryUiState(
            category = category,
            places = list,
            selectedFilter = filterTag,
            sortOption = sort,
            searchQuery = search,
            availableFilters = category.filterTags,
            totalCount = list.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = CategoryUiState()
    )

    fun selectCategory(categoryId: String) {
        _currentCategoryId.value = categoryId
        _categoryFilterTag.value = "All Places"
        _categorySearch.value = ""
    }

    fun onCategoryFilterSelected(filter: String) {
        _categoryFilterTag.value = filter
    }

    fun onCategorySortSelected(sort: String) {
        _categorySortOption.value = sort
    }

    fun onCategorySearchQueryChanged(query: String) {
        _categorySearch.value = query
    }

    // Place Detail State
    private val _selectedPlaceId = MutableStateFlow("hazur-sahib")
    private val _expandedAccordionIds = MutableStateFlow<Set<String>>(setOf("e1"))
    private val _isAudioGuidePlaying = MutableStateFlow(false)

    val placeDetailUiState: StateFlow<PlaceDetailUiState> = combine(
        _selectedPlaceId,
        _expandedAccordionIds,
        _isAudioGuidePlaying,
        repository.favorites
    ) { placeId, accordions, isPlaying, favs ->
        val place = repository.getPlaceById(placeId) ?: repository.places.first()
        val nearby = place.nearbyPlaceIds.mapNotNull { repository.getPlaceById(it) }

        PlaceDetailUiState(
            place = place,
            isSaved = favs.contains(place.id),
            expandedAccordionIds = accordions,
            nearbyPlaces = nearby,
            isAudioGuidePlaying = isPlaying
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PlaceDetailUiState()
    )

    fun selectPlace(placeId: String) {
        _selectedPlaceId.value = placeId
        repository.markViewed(placeId)
    }

    fun toggleAccordion(accordionId: String) {
        val current = _expandedAccordionIds.value.toMutableSet()
        if (current.contains(accordionId)) {
            current.remove(accordionId)
        } else {
            current.add(accordionId)
        }
        _expandedAccordionIds.value = current
    }

    fun toggleAudioGuide() {
        _isAudioGuidePlaying.value = !_isAudioGuidePlaying.value
    }

    fun toggleFavorite(placeId: String) {
        repository.toggleFavorite(placeId)
    }

    fun isFavorite(placeId: String): Boolean {
        return repository.isFavorite(placeId)
    }

    // Favorites State
    private val _favoritesCollectionFilter = MutableStateFlow("All Saved")

    val favoritesUiState: StateFlow<FavoritesUiState> = combine(
        repository.favorites,
        repository.recentlyViewed,
        _favoritesCollectionFilter
    ) { favIds, recentIds, collection ->
        val saved = favIds.mapNotNull { repository.getPlaceById(it) }
        val recent = recentIds.mapNotNull { repository.getPlaceById(it) }

        val filteredSaved = when (collection) {
            "Historical" -> saved.filter { it.categoryId == "historical" }
            "Spiritual" -> saved.filter { it.categoryId == "spiritual" }
            "Dining" -> saved.filter { it.categoryId == "restaurants" || it.categoryId == "cafes" }
            "Weekend" -> saved.filter { it.isWeekendEscape }
            else -> saved
        }

        FavoritesUiState(
            savedPlaces = filteredSaved,
            recentlyViewedPlaces = recent,
            activeCollection = collection
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = FavoritesUiState()
    )

    fun onFavoritesCollectionSelected(collection: String) {
        _favoritesCollectionFilter.value = collection
    }

    // Profile State
    private val _profileState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileState.asStateFlow()

    fun toggleDarkMode(enabled: Boolean) {
        _profileState.value = _profileState.value.copy(isDarkMode = enabled)
    }

    fun toggleHighContrast(enabled: Boolean) {
        _profileState.value = _profileState.value.copy(highContrast = enabled)
    }

    fun toggleAartiNotifications(enabled: Boolean) {
        _profileState.value = _profileState.value.copy(aartiNotifications = enabled)
    }

    fun setAudioLanguage(language: String) {
        _profileState.value = _profileState.value.copy(audioLanguage = language)
    }
}
