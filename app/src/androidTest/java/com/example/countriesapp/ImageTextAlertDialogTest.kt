package com.example.countriesapp

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.countriesapp.ui.components.ImageTextAlertDialog
import org.junit.Rule
import org.junit.Test

class ImageTextAlertDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun titleTextIsRendered() {
        composeTestRule.setContent {
            ImageTextAlertDialog(
                modifier = Modifier,
                imageUrl = null,
                title = "Title",
                text = null,
                onDismissDialog = {}
            )
        }

        composeTestRule.onNodeWithText("Title").assertExists()
    }

    @Test
    fun imageIsRendered() {
        composeTestRule.setContent {
            ImageTextAlertDialog(
                modifier = Modifier,
                imageUrl = "https://picsum.photos/200/200",
                title = "Title",
                text = null,
                onDismissDialog = {},
                imageContentDescription = "Some Image"
            )
        }

        composeTestRule.onNodeWithText("Title").assertExists()
        composeTestRule.onNodeWithContentDescription("Some Image").assertExists()
    }

    @Test
    fun textIsRendered() {
        composeTestRule.setContent {
            ImageTextAlertDialog(
                modifier = Modifier,
                imageUrl = null,
                title = "Title",
                text = "This is some text",
                onDismissDialog = {}
            )
        }

        composeTestRule.onNodeWithText("Title").assertExists()
        composeTestRule.onNodeWithText("This is some text").assertExists()
    }
}
