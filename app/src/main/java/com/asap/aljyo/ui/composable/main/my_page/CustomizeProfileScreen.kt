package com.asap.aljyo.ui.composable.main.my_page

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.components.main.CustomItemState
import com.asap.aljyo.core.components.main.CustomizeProfileScreenState
import com.asap.aljyo.core.components.main.CustomizeProfileViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.CustomButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White
import kotlinx.coroutines.time.delay

data class ProfileCustom(
    @DrawableRes val image: Int,
    val state: CustomItemState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeProfileScreen(
    viewModel: CustomizeProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    var openItemEvent by remember { mutableStateOf(false) }
    var touchUnlockItemIdx: Int = -1
    val state by viewModel.state.collectAsStateWithLifecycle()
    val usedItemIdx by remember {
        derivedStateOf { state.profileItems.indexOfFirst { it.isUsed } }
    }
    var selectedItemIdx by remember { mutableIntStateOf(usedItemIdx) }

    LaunchedEffect(usedItemIdx) {
        selectedItemIdx = usedItemIdx
    }

    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "내 프로필 꾸미기",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.fsp,
                                color = Black01
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(R.drawable.ic_top_back),
                                contentDescription = "BACK"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                ) {
                    IconButton(
                        onClick = { selectedItemIdx = usedItemIdx },
                        modifier = Modifier
                            .size(52.dp)
                            .border(1.dp, Red01, RoundedCornerShape(10.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_reset),
                            contentDescription = "RESET ICON",
                            tint = Color.Unspecified
                        )
                    }

                    CustomButton(
                        modifier = Modifier
                            .height(52.dp)
                            .padding(start = 8.dp),
                        text = "저장하기",
                        enable = true,
                        onClick = {
                            viewModel.setProfileItem(selectedItemIdx)
                        }
                    )
                }
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFE6E6E6)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .padding(top = 32.dp)
                        .size(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        model = state.profileImage,
                        error = painterResource(R.drawable.ic_empty_profile),
                        contentDescription = "My page profile image",
                        contentScale = ContentScale.Crop
                    )

                    if (selectedItemIdx != -1) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(state.profileItems[selectedItemIdx].customItem),
                            contentDescription = "SELECTED CUSTOM ITEM",
                            tint = Color.Unspecified
                        )
                    }
                }

                Spacer(modifier = Modifier.height(42.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 24.dp, bottom = 24.dp)
                                .background(Grey01, CircleShape)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_trophy),
                                contentDescription = "TROPHY ICON",
                                tint = Color.Unspecified
                            )
                            Text(
                                text = "누적 랭킹 점수",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.fsp,
                                    color = Black02
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = state.totalRankScore,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 14.fsp,
                                    color = Black01
                                )
                            )
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp),
                            horizontalArrangement = Arrangement.spacedBy(7.dp),
                            verticalArrangement = Arrangement.spacedBy(7.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            itemsIndexed(state.profileItems) { idx, item ->
                                CustomItem(
                                    item = item,
                                    isSelected = selectedItemIdx == idx,
                                    onUnlockableClick = {
                                        item.isUnlocked = CustomItemState.UNLOCK
                                        touchUnlockItemIdx = idx
                                        openItemEvent = true
                                        viewModel.unlockProfileItem(item.profileId)
                                    },
                                    onUnlockClick = {
                                        selectedItemIdx = if (selectedItemIdx == idx) -1 else idx
                                    },
                                )
                            }
                        }

                        if (openItemEvent && touchUnlockItemIdx != -1) {
                            Dialog(
                                onDismissRequest = { openItemEvent = false }
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(266.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.bg_unlock_2),
                                        contentDescription = "UNLOCK BG",
                                        tint = Color.Unspecified
                                    )
                                    Icon(
                                        painter = painterResource(R.drawable.bg_unlock_1),
                                        contentDescription = "UNLOCK BG",
                                        tint = Color.Unspecified
                                    )
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(75.dp)
                                            .clip(CircleShape),
                                        model = state.profileImage,
                                        error = painterResource(R.drawable.ic_empty_profile),
                                        contentDescription = "My page profile image",
                                        contentScale = ContentScale.Crop
                                    )
                                    Icon(
                                        painter = painterResource(state.profileItems[touchUnlockItemIdx].customItem),
                                        contentDescription = "UNLOCK CUSTOM ITEM",
                                        tint = Color.Unspecified
                                    )

                                    Text(
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        text = "${touchUnlockItemIdx.plus(1)}단계 아이템 해제!",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            color = White,
                                            fontSize = 24.fsp
                                        )
                                    )
                                }
                            }
                        }

                        ShowUnlockMessage(state)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowUnlockMessage(state: CustomizeProfileScreenState) {
    var isShow by remember { mutableStateOf(true) }

    LaunchedEffect(isShow) {
        kotlinx.coroutines.delay(5000)
        isShow = false
    }

    if (state.profileItems.any { it.isUnlocked == CustomItemState.UNLOCKABLE }
        && isShow) {
        val unlockScore = state.profileItems.first { it.isUnlocked == CustomItemState.UNLOCKABLE }
            .itemName.replace("_",",")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 20.dp, end = 20.dp)
                .background(Red02, RoundedCornerShape(14.dp))
                .padding(top = 9.dp, bottom = 9.dp, start = 24.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(28.dp),
                painter = painterResource(R.drawable.ic_bell),
                contentDescription = "BELL ICON",
                tint = Color.Unspecified
            )
            Text(
                text = "누적 랭킹 점수가 ${unlockScore}점이 넘어\n" +
                        "아이템을 해제할 수 있어요!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.fsp,
                    color = Black01
                )
            )
        }
    }
}


@Composable
@Preview
fun PreviewCustomizeProfileScreen() {
    AljyoTheme {
        CustomizeProfileScreen {}
    }
}