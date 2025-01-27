package com.asap.aljyo.ui.composable.group_form.group_create

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey02

@Composable
fun TimePicker(
    selectedTime: String,
    onClick: () -> Unit
) {
    val (hour, minutes) = selectedTime.split(":")
    val period = hour.toInt().let { if (it in 0..11)"오전" else "오후" }
    val hour12 = hour.toInt().let { if (it == 0 || it == 12) 12 else it % 12 }

    Column {
        Text(
            modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            text = "알람 시간",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black02,
                fontSize = 14.fsp
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Grey02,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$period $hour12:$minutes",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.fsp,
                    fontWeight = FontWeight.Bold,
                    color = Black01
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "Arrow Down Icon"
            )
        }
    }
}

@Composable
fun AlarmTimePicker(
    selectedTime: String,
    onHourSelected: (String) -> Unit,
    onMinutesSelected: (String) -> Unit
) {
    val hoursList = listOf("", "") + (0..23).map { it.toString().padStart(2, '0') } + listOf("", "")
    val minutesList =
        listOf("", "") + (0..59).map { it.toString().padStart(2, '0') } + listOf("", "")
    val (selectedHour, selectedMinutes) = selectedTime.split(":")

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(42.dp)
                .background(
                    color = Grey01,
                    shape = RoundedCornerShape(6.dp)
                )
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TimeWheelPicker(
                modifier = Modifier.weight(1f),
                itemsList = hoursList,
                horizontalAlignment = Alignment.End,
                onItemSelected = onHourSelected,
                selectedTime = selectedHour
            )

            Spacer(modifier = Modifier.width(24.dp))

            TimeWheelPicker(
                modifier = Modifier.weight(1f),
                itemsList = minutesList,
                horizontalAlignment = Alignment.Start,
                onItemSelected = onMinutesSelected,
                selectedTime = selectedMinutes
            )
        }
    }
}

@Composable
fun TimeWheelPicker(
    modifier: Modifier,
    itemsList: List<String>,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    selectedTime: String,
    onItemSelected: (String) -> Unit
) {
    val initialIdx = itemsList.indexOf(selectedTime)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIdx - 2)
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val currentFirstItemIdx by remember { derivedStateOf { listState.firstVisibleItemIndex } }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            onItemSelected(itemsList[currentFirstItemIdx + 2])
        }
    }

    LazyColumn(
        state = listState,
        flingBehavior = snapFlingBehavior,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(items = itemsList) { idx, hour ->
            Text(
                modifier = Modifier
                    .height(25.dp)
                    .width(80.dp),
                text = hour,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = if (idx == currentFirstItemIdx + 2) 18.fsp else 16.fsp,
                    fontWeight = FontWeight.Bold,
                    color = if (idx == currentFirstItemIdx + 2) Black01 else Black04
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
