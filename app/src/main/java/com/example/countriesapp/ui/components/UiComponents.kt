package com.example.countriesapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleLineTextField(
    placeholderValue: String,
    onInputText: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = true,
        keyboardType = KeyboardType.Text
    )
) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = inputValue.value,
        onValueChange = {
            inputValue.value = it
            onInputText(it.text)
        },
        placeholder = { Text(text = placeholderValue) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        maxLines = 1
    )
}

@Composable
fun RowItem(
    modifier: Modifier = Modifier,
    title: String,
    onItemClicked: () -> Unit,
    style: TextStyle = TextStyle(
        fontSize = 18.sp,
        color = LocalContentColor.current,
        textAlign = TextAlign.Start
    )
) {
    ClickableText(
        text = AnnotatedString(title),
        onClick = { onItemClicked() },
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth(),
        style = style
    )
}

@Composable
fun ImageTextAlertDialog(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String,
    text: String?,
    onDismissDialog: () -> Unit,
    imageContentDescription: String = ""
) {
    AlertDialog(
        onDismissRequest = { onDismissDialog() },
        title = { Text(text = title) },
        text = {
            Column {
                imageUrl?.let {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = imageContentDescription,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                text?.let {
                    Text(
                        text = it,
                        modifier = modifier.padding(top = 8.dp)
                    )
                }
            }

        },
        modifier = modifier
            .padding(24.dp)
            .wrapContentSize(),
        confirmButton = { /* Do nothing */ }
    )
}