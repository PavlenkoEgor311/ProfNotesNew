package com.example.profnotes.ui.addNote.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profnotes.R
import com.example.profnotes.core.DateTransformation
import com.example.profnotes.core.colorCompose.*
import com.example.profnotes.data.models.Notes

@Composable
fun AddLocalScreen(
    localNote: Notes?,
    returnLocalNote: (note: Notes) -> Unit
) {
    var titleNote by remember { mutableStateOf(localNote?.title ?: "") }
    var descriptionNote by remember { mutableStateOf(localNote?.description ?: "") }
    var dateNote by remember { mutableStateOf(localNote?.date ?: "") }
    val maxLengthDate = 8
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = stringResource(id = R.string.title_note), color = Color.White)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DarkGray),
            value = titleNote,
            onValueChange = { titleNote = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.place_holder_text_field),
                    color = LightestGray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        Text(
            text = stringResource(id = R.string.description_note),
            modifier = Modifier.padding(top = 16.dp),
            color = Color.White
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DarkGray)
                .verticalScroll(rememberScrollState()),
            value = descriptionNote,
            onValueChange = { descriptionNote = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.place_holder_text_field),
                    color = LightestGray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        Text(
            text = stringResource(id = R.string.date_note),
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DarkGray),
            value = dateNote,
            onValueChange = {
                if (it.length <= maxLengthDate) dateNote = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.place_holder_text_field_date),
                    color = LightestGray
                )
            },
            visualTransformation = DateTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
        )
        return returnLocalNote(
            Notes(
                id = 0,
                title = titleNote,
                date = dateNote,
                description = descriptionNote,
                status = "Новое",
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
fun Show() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AddLocalScreen(returnLocalNote = {}, localNote = null)
    }
}
