package com.example

import com.example.data.repository.ExploreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExploreRepositoryTest {

    private lateinit var repository: ExploreRepository

    @Before
    fun setup() {
        repository = ExploreRepository(null)
    }

    @Test
    fun repository_hasPreloadedPlaces() {
        assertTrue(repository.places.isNotEmpty())
        val hazurSahib = repository.getPlaceById("hazur-sahib")
        assertNotNull(hazurSahib)
        assertEquals("Takht Sachkhand Sri Hazur Abchalnagar Sahib", hazurSahib?.name)
        assertTrue(hazurSahib?.isSpotlight == true)
    }

    @Test
    fun repository_categoriesLoaded() {
        assertEquals(8, repository.categories.size)
        val historical = repository.categories.find { it.id == "historical" }
        assertNotNull(historical)
        assertEquals("Historical Places", historical?.name)
    }

    @Test
    fun repository_searchFunctionality() {
        val fortResults = repository.searchPlaces("fort")
        assertTrue(fortResults.isNotEmpty())
        assertTrue(fortResults.any { it.name.contains("Fort", ignoreCase = true) })

        val gurudwaraResults = repository.searchPlaces("Hazur")
        assertEquals(1, gurudwaraResults.size)
        assertEquals("hazur-sahib", gurudwaraResults.first().id)
    }

    @Test
    fun repository_favoriteToggle() {
        val testPlaceId = "kandhar-fort"
        val initiallyFavorite = repository.isFavorite(testPlaceId)
        repository.toggleFavorite(testPlaceId)
        assertEquals(!initiallyFavorite, repository.isFavorite(testPlaceId))

        // Toggle back
        repository.toggleFavorite(testPlaceId)
        assertEquals(initiallyFavorite, repository.isFavorite(testPlaceId))
    }
}
