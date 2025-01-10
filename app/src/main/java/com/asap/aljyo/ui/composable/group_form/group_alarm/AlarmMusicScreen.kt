package com.asap.aljyo.ui.composable.group_form.group_alarm

import android.media.MediaPlayer
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey03
import com.asap.aljyo.ui.theme.Red01

data class Music(
    val musicTitle: String,
    @RawRes val music: Int
)

@Composable
fun AlarmMusicScreen(
    musicTitle: String? = null,
    onBackClick: () -> Unit,
    onCompleteClick: (String) -> Unit,
) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var selectedMusic by remember { mutableStateOf(musicTitle) }
    val alarmMusicList = listOf(
        Music("Alarm01", R.raw.alarm1),
        Music("Alarm02", R.raw.alarm2),
        Music("Alarm03", R.raw.alarm3),
        Music("Alarm04", R.raw.alarm4),
        Music("Alarm05", R.raw.alarm5),
        Music("Alarm06", R.raw.alarm6),
        Music("Alarm07", R.raw.alarm7),
        Music("Alarm08", R.raw.alarm8),
        Music("Alarm09", R.raw.alarm9),
        Music("Alarm10", R.raw.alarm10)
    )

    Scaffold(
        containerColor = White,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(56.dp)
                    .background(White)
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_top_back),
                        contentDescription = "BACK"
                    )
                }

                Text(
                    text = "노래선택",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Black,
                        fontSize = 16.fsp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(alarmMusicList) { music ->
                    MusicItem(
                        modifier = Modifier.padding(bottom = 24.dp),
                        item = music,
                        isSelected = music.musicTitle == selectedMusic,
                        onClick = {
                            selectedMusic = music.musicTitle
                            mediaPlayer?.release()
                            mediaPlayer = MediaPlayer.create(context, music.music).apply {
                                isLooping = true
                                start()
                            }
                        }
                    )
                }
            }

            CustomButton(
                modifier = Modifier.padding(bottom = 6.dp),
                text = "완료",
                enable = selectedMusic.isNullOrEmpty().not(),
                onClick = {
                    onCompleteClick(selectedMusic!!)
                }
            )
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}

@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    item: Music,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            text = item.musicTitle,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Black01,
                fontSize = 15.fsp,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Box(
            modifier = Modifier
                .size(22.dp)
                .background(color = White, shape = CircleShape)
                .border(
                    width = if (isSelected) 6.dp else 1.dp,
                    color = if (isSelected) Red01 else Grey03,
                    shape = CircleShape
                )
        )
    }
}

@Preview
@Composable
fun PreviewAlarmMusicScreen() {
    AljyoTheme() {
        AlarmMusicScreen(
            onBackClick = {},
            onCompleteClick = {}
        )
    }
}