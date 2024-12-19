package com.asap.aljyo.ui.composable.group_form.group_create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black03
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.White
import com.asap.aljyo.ui.theme.pretendardFamily
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar

@Composable
fun CalendarView(
    selectedYear: Int,
    selectedMonth: Int,
    selectedDate: LocalDate?,
    onMonthSelected: (Int) -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val calendar = Calendar.getInstance().apply {
        set(selectedYear, selectedMonth - 1, 1)
    }

    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    val dateList = buildList {
        repeat(firstDayOfWeek - 1) { add(null) }
        val (year, month) = calendar.run { get(Calendar.YEAR) to get(Calendar.MONTH) + 1 }
        for (date in 1..daysInMonth) {
            add(LocalDate.of(year, month, date))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            text = "알람 종료 날짜",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 14.sp
            )
        )

        CalendarMonthSelector(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp),
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            onMonthSelect = onMonthSelected
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        color = if (day == "일") Red else Black03,
                    ),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        CalendarDate(
            dateList = dateList,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
        )
    }
}

@Composable
fun CalendarMonthSelector(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    selectedMonth: Int,
    onMonthSelect: (Int) -> Unit
) {
    val isPreviousEnabled = LocalDate.of(selectedYear, selectedMonth, 1) > LocalDate.now()
    val isNextEnabled =
        LocalDate.of(selectedYear, selectedMonth, 1).plusMonths(1) < LocalDate.now().plusYears(1)

    Row(
        modifier = modifier.padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .clickable(
                    enabled = isPreviousEnabled,
                    onClick = { onMonthSelect(selectedMonth - 1) }
                ),
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = "Calendar Left Arrow",
            tint = if (isPreviousEnabled) Color.Unspecified else Black04
        )

        Text(
            text = "${selectedYear}년 ${selectedMonth}월",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black01,
                fontSize = 15.sp
            )
        )

        Icon(
            modifier = Modifier
                .clickable(
                    enabled = isNextEnabled,
                    onClick = { onMonthSelect(selectedMonth + 1) }
                ),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "Calendar Right Arrow",
            tint = if (isNextEnabled) Color.Unspecified else Black04
        )
    }
}

@Composable
fun CalendarDate(
    dateList: List<LocalDate?>,
    selectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit
) {
    val weeks = dateList.chunked(7).map {
        if (it.size < 7) it + List(7 - it.size){null} else it
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        weeks.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                week.forEach { date ->
                    if (date != null) {
                        CalendarDateItem(
                            modifier = Modifier
                                .weight(1f),
                            date = date,
                            isSunday = date.isSunday(),
                            isEnabledDate = date.isEnableDate(),
                            isSelectedDate = (date == selectedDate),
                            onDateSelected = onDateSelected
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDateItem(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSunday: Boolean,
    isEnabledDate: Boolean,
    isSelectedDate: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val textColor = if (!isEnabledDate) {
        Black04
    } else if (isSelectedDate) {
        White
    } else if (isSunday) {
        Red
    } else {
        Black01
    }

    val background = if (isSelectedDate) Red01 else Color.Transparent

    Box(
        modifier = modifier
            .padding(top = 8.dp)
            .aspectRatio(1f) // 정사각형 비율 유지
            .clip(shape = CircleShape)
            .background(background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = isEnabledDate,
                onClick = { onDateSelected(date) }
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.headlineSmall.copy(
                color = textColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun LocalDate.isSunday(): Boolean {
    return this.dayOfWeek == DayOfWeek.SUNDAY
}

private fun LocalDate.isEnableDate(): Boolean {
    val today = LocalDate.now()
    val endDay = today.plusYears(1)
    return this in today..endDay
}

@Preview(
    showBackground = true, // 배경색을 활성화
    backgroundColor = 0xFFE0E0E0 // 배경색을 Hex 색상 코드로 설정 (회색)
)
@Composable
fun PreviewCalendar() {
    AljyoTheme {
        var selectedYear by remember { mutableIntStateOf(LocalDate.now().year) }
        var selectedMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }
        var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

        CalendarView(
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDate = selectedDate,
            onMonthSelected = { month ->
                if (month == 13) {
                    selectedMonth = 1
                    selectedYear += 1
                }
            },
            onDateSelected = { selectedDate = it },
        )
    }
}
