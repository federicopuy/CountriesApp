package com.example.countriesapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.countriesapp.ui.components.SingleLineTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SingleLineTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun placeHolderTextIsRendered() {
        composeTestRule.setContent {
            SingleLineTextField(
                placeholderValue = "Placeholder",
                onInputText = {}
            )

        }

        composeTestRule.onNodeWithText("Placeholder").assertExists()
    }

    @Test
    fun textFieldIsUpdatedWhenTextChanges() {
        val inputValue = mutableStateOf("")

        composeTestRule.setContent {
            SingleLineTextField(
                placeholderValue = "Placeholder",
                onInputText = { inputValue.value = it }
            )
        }

        composeTestRule.onNodeWithText("Placeholder").performTextInput("Hello")

        assert(inputValue.value == "Hello")
    }


    @Test
    fun onInputTextCallbackIsCalledWhenTextChanges() {
        val onInputTextCalled = mutableStateOf(false)
        composeTestRule.setContent {
            SingleLineTextField(
                placeholderValue = "Placeholder",
                onInputText = { onInputTextCalled.value = true }
            )
        }

        composeTestRule.onNodeWithText("Placeholder").performTextInput("Hello")

        assert(onInputTextCalled.value)
    }

}