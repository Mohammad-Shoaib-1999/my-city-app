package com.example

import com.example.data.repository.ExploreRepository
import com.example.viewmodel.ExploreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExploreViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ExploreRepository
    private lateinit var viewModel: ExploreViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = ExploreRepository(null)
        viewModel = ExploreViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun homeSearchQuery_updatesState() = runTest(testDispatcher) {
        val collectJob = backgroundScope.launch {
            viewModel.homeUiState.collect {}
        }
        advanceUntilIdle()

        viewModel.onHomeSearchQueryChanged("Nanded Fort")
        advanceUntilIdle()

        assertEquals("Nanded Fort", viewModel.homeUiState.value.searchQuery)
        assertTrue(viewModel.homeUiState.value.isSearching)
        assertTrue(viewModel.homeUiState.value.searchResults.isNotEmpty())
        collectJob.cancel()
    }

    @Test
    fun categorySelection_updatesCategoryState() = runTest(testDispatcher) {
        val collectJob = backgroundScope.launch {
            viewModel.categoryUiState.collect {}
        }
        advanceUntilIdle()

        viewModel.selectCategory("cafes")
        advanceUntilIdle()

        assertEquals("cafes", viewModel.categoryUiState.value.category?.id)
        assertTrue(viewModel.categoryUiState.value.places.isNotEmpty())
        collectJob.cancel()
    }

    @Test
    fun placeDetail_toggleAccordion() = runTest(testDispatcher) {
        val collectJob = backgroundScope.launch {
            viewModel.placeDetailUiState.collect {}
        }
        advanceUntilIdle()

        val initialExpanded = viewModel.placeDetailUiState.value.expandedAccordionIds
        assertTrue(initialExpanded.contains("e1"))

        viewModel.toggleAccordion("e2")
        advanceUntilIdle()

        assertTrue(viewModel.placeDetailUiState.value.expandedAccordionIds.contains("e2"))
        collectJob.cancel()
    }

    @Test
    fun placeDetail_toggleAudioGuide() = runTest(testDispatcher) {
        val collectJob = backgroundScope.launch {
            viewModel.placeDetailUiState.collect {}
        }
        advanceUntilIdle()

        val initialPlaying = viewModel.placeDetailUiState.value.isAudioGuidePlaying
        viewModel.toggleAudioGuide()
        advanceUntilIdle()

        assertEquals(!initialPlaying, viewModel.placeDetailUiState.value.isAudioGuidePlaying)
        collectJob.cancel()
    }

    @Test
    fun profile_toggleDarkMode() {
        viewModel.toggleDarkMode(true)
        assertTrue(viewModel.profileUiState.value.isDarkMode)
    }
}
