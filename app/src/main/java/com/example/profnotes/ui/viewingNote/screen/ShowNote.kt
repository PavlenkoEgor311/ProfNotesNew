package com.example.profnotes.ui.viewingNote.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profnotes.R
import com.example.profnotes.core.styleText.Typo
import com.example.profnotes.data.models.GlobalNote
import com.example.profnotes.data.models.Notes
import com.example.profnotes.ui.addNote.screen.ProfileFriends

@Composable
fun ShowNote(
    title: String,
    date: String,
    description: String,
    listSelectedFriend: List<Int>?,
    listAddedFiles: List<Int>,
    key: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.title_note),
            color = White,
            style = Typo.DefaultTypography.h1
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Text(
                modifier = Modifier
                    .border(width = 2.dp, color = DarkGray, shape = RoundedCornerShape(12.dp))
                    .padding(10.dp),
                text = title,
                color = White,
                style = Typo.DefaultTypography.h3
            )
            if (key)
                Image(
                    painter = painterResource(id = R.drawable.ic_mock_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(size = 60.dp)
                        .clip(RoundedCornerShape(90.dp))
                        .border(width = 2.dp, color = Gray, shape = RoundedCornerShape(90.dp))
                        .background(White),
                )
        }
        Text(
            text = stringResource(id = R.string.description_note),
            color = White,
            style = Typo.DefaultTypography.h1,
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .border(width = 2.dp, color = DarkGray, shape = RoundedCornerShape(12.dp))
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = description,
                color = White,
                style = Typo.DefaultTypography.h3,
            )
        }
        Text(
            text = stringResource(id = R.string.status_note),
            color = White,
            style = Typo.DefaultTypography.h1,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Новое",
            color = White,
            style = Typo.DefaultTypography.h1,
            modifier = Modifier.padding(start = 10.dp)
        )
        if (key) {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(id = R.string.marked_friends),
                color = White,
                style = Typo.DefaultTypography.h1
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 10.dp)
            ) {
                (0..10).forEach { _ ->
                    ProfileFriends()
                }
            }
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(id = R.string.date_note),
            style = Typo.DefaultTypography.h1,
            color = White,
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .border(width = 2.dp, color = DarkGray, shape = RoundedCornerShape(12.dp))
                .padding(10.dp),
            text = date,
            style = Typo.DefaultTypography.h3,
            color = White
        )
        if (key)
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(id = R.string.аttached_files),
                style = Typo.DefaultTypography.h1,
                color = White,
            )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        ShowNote("Заказать сняряды", "23 января", "dfdfdfdfawwwER", listOf(), listOf(), true)
    }
}