package com.example.profnotes.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profnotes.core.colorCompose.*
import com.example.profnotes.R
import com.example.profnotes.data.models.Notes
import com.example.profnotes.ui.home.HomeFragment.Companion.STATUS_COMPLETED_RED
import com.example.profnotes.ui.home.HomeFragment.Companion.STATUS_NEW
import com.example.profnotes.ui.home.HomeFragment.Companion.STATUS_POSTEPONED

@Composable
fun ItemNote(
    note: Notes,
    onClickDel: () -> Unit,
    onClickChangeNote: () -> Unit,
    onClickChangeStatus: () -> Unit,
    onClickShow: () -> Unit,
) {
    val openNote = remember { mutableStateOf(false) }
    val colorNote = remember { mutableStateOf(getColorNote(note)) }
    Row(
        modifier = Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .background(DarkGray)
                .background(shape = RoundedCornerShape(15.dp), color = Dark)
                .weight(1f)
                .height(IntrinsicSize.Min)
                .clickable { onClickShow() }
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                text = note.status,
                color = colorNote.value,
                style = textStyle.body1
            )
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .background(LightestGray)
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                        .background(colorNote.value)
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(0.7f)
                ) {
                    Text(
                        text = note.title,
                        color = White,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1
                    )
                    Text(
                        text = note.description,
                        color = LightestGray,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            openNote.value = !openNote.value
                        }
                        .height(24.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "Сменить статус",
                    color = White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.clickable {
                        onClickChangeStatus()
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 6.dp, start = 20.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = note.date ?: "",
                    color = White,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
        AnimatedVisibility(
            visible = openNote.value,
            modifier = Modifier
                .background(DarkGray)
                .weight(0.15f)
                .align(Alignment.CenterVertically)
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 10.dp)
                        .size(16.dp)
                        .clickable {
                            onClickChangeNote()
                        },
                    painter = painterResource(id = R.drawable.ic_changes),
                    contentDescription = null
                )
                Box(modifier = Modifier.weight(1f)) {
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(LightestGray)
                    )
                }
                Image(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 24.dp)
                        .size(16.dp)
                        .clickable {
                            onClickDel()
                        },
                    painter = painterResource(id = R.drawable.ic_basket),
                    contentDescription = null
                )
            }
        }
    }
}

private fun getColorNote(note: Notes): Color {
    return when (note.status.trim()) {
        STATUS_COMPLETED_RED, STATUS_POSTEPONED -> Red
        STATUS_NEW -> Blue
        else -> Yellow
    }
}

@Preview(showBackground = true)
@Composable
fun Show() {
    val note = Notes(12, "Чет выучить", "Сегодня", "Новое", "ДАБ ДАБ")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        ItemNote(
            note = note,
            onClickChangeNote = {},
            onClickDel = {},
            onClickChangeStatus = {},
            onClickShow = {})
    }
}
