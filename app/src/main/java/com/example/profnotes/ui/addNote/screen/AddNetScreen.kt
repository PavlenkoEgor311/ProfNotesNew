package com.example.profnotes.ui.addNote.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profnotes.R
import com.example.profnotes.core.colorCompose.DarkGray
import com.example.profnotes.core.colorCompose.LightestGray
import com.example.profnotes.core.colorCompose.Purple80
import com.example.profnotes.data.models.GlobalNote
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import ru.mrz.profnotes.core.translate
import java.time.LocalDate
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNetScreen(
    globalNote: GlobalNote?,
    returnNote: (note: GlobalNote) -> Unit,
) {
    var titleNote by remember { mutableStateOf(globalNote?.title ?: "") }
    var descriptionNote by remember { mutableStateOf(globalNote?.description ?: "") }
    var dateNote by remember { mutableStateOf("") }

    var currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        Text(text = stringResource(id = R.string.title_note), color = White)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DarkGray),
            value = titleNote,
            onValueChange = {
                titleNote = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.place_holder_text_field),
                    color = LightestGray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = White,
                focusedIndicatorColor = Transparent
            ),
        )
        Text(
            text = stringResource(id = R.string.description_note),
            modifier = Modifier.padding(top = 16.dp),
            color = White,
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
            onValueChange = {
                descriptionNote = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.place_holder_text_field),
                    color = LightestGray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = White,
                focusedIndicatorColor = Transparent
            ),
        )
        Text(
            text = stringResource(id = R.string.circle_friends),
            color = White,
            modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)
        )
        HorizontalCalendar(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkGray),
            dayContent = {
                Day(
                    day = it,
                    clickOfDay = { day ->
                        dateNote = day.toString()
                    },
                    currentMonth = currentMonth
                )
            },
            monthHeader = {
                currentMonth = it.yearMonth
                Month(month = it)
            },
            state = state,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        return returnNote(
            GlobalNote(
                id = 2,
                title = titleNote,
                description = descriptionNote,
                date = dateNote,
                friendId = null
            )
        )
    }
}


@Composable
fun Month(
    month: CalendarMonth,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val daysOfWeek = daysOfWeek().map { dayWeek -> dayWeek.translate() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        )
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = White,
                text = month.yearMonth.month.translate().capitalize(),
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in daysOfWeek) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = dayOfWeek.capitalize().take(3),
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun Day(day: CalendarDay, clickOfDay: (day: LocalDate) -> Unit, currentMonth: YearMonth) {
    var selectedDay by remember { mutableStateOf(false) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dayWeek = day.date.dayOfWeek.toString()
        val colorDay =
            if (currentMonth.month.value != day.date.monthValue) Gray
            else if (dayWeek == "SATURDAY" || dayWeek == "SUNDAY") Yellow else White

        Box(modifier = Modifier
            .padding(vertical = 12.dp)
            .clickable {
                clickOfDay(day.date)
                selectedDay = !selectedDay
            }
            .clip(RoundedCornerShape(90.dp))
            .background(if (selectedDay) Purple80 else Transparent)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
                color = colorDay,
                text = day.date.dayOfMonth.toString(),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ShowNet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        AddNetScreen(returnNote = {}, globalNote = null)
    }
}