package com.example.countriesapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.countriesapp.ui.components.RowItem
import org.junit.Rule
import org.junit.Test

class RowItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun titleIsRendered() {
        composeTestRule.setContent {
            RowItem(
                title = "Title",
                onItemClicked = {}
            )
        }

        composeTestRule.onNodeWithText("Title").assertExists()
    }

    @Test
    fun itemCanBeClicked() {
        val isItemClicked = mutableStateOf(false)

        composeTestRule.setContent {
            RowItem(
                title = "Title",
                onItemClicked = { isItemClicked.value = true }
            )
        }

        composeTestRule.onNodeWithText("Title").performClick()

        assert(isItemClicked.value)
    }

}