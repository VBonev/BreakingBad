package com.today.breakingbad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.today.breakingbad.overview.CharactersOverviewViewModel
import com.today.network.model.Character
import org.junit.Rule
import org.junit.Test

class JokeDetailRepositoryTest {

    private val initialList = listOf(
        Character(
            name = "Walter White",
            birthday = "12.25.1976",
            nickname = "Heisenberg",
            appearance = listOf(4, 5),
            occupation = listOf("Meth king pin", "teacher"),
            status = "presumed dead",
            img = "no image"
        ),
        Character(
            name = "Jessy Pinkman",
            birthday = "12.25.1976",
            nickname = "Heisenberg",
            appearance = listOf(1, 2),
            occupation = listOf("Walt apprentice"),
            status = "presumed dead",
            img = "no image"
        ),
        Character(
            name = "Saul Goodman",
            birthday = "12.25.1976",
            nickname = "Heisenberg",
            appearance = listOf(1, 5),
            occupation = listOf("Lawyer"),
            status = "presumed dead",
            img = "no image"
        )
    )
    private val viewModel = CharactersOverviewViewModel()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `WHEN filter by season THEN we return two items are returned`() {

        // GIVEN
        viewModel.originalList = initialList
        // WHEN
        viewModel.filterBySeason(1)
        viewModel.charactersList.observeForever {}

        //THEN
        assertThat(viewModel.charactersList.value?.size).isEqualTo(2)
        assertThat(viewModel.charactersList.value?.first()?.appearance?.contains(1))
    }

    @Test
    fun `WHEN we search by name THEN we return only the matching items`() {
        // GIVEN
        viewModel.originalList = initialList
        // WHEN
        val searchName = "Saul"
        viewModel.searchByName(searchName)
        viewModel.charactersList.observeForever {}

        //THEN
        assertThat(viewModel.charactersList.value?.first()?.name?.contains(searchName))
    }

    @Test
    fun `WHEN we search by name and we cant find it THEN we return empty list`() {
        // GIVEN
        viewModel.originalList = initialList
        // WHEN
        viewModel.searchByName("Fring")
        viewModel.charactersList.observeForever {}

        //THEN
        assertThat(viewModel.charactersList.value?.size).isEqualTo(0)
    }
}