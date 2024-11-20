package com.asap.aljyo.ui.composable.group_ranking

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asap.aljyo.R
import com.asap.aljyo.components.group_ranking.GroupRankingViewModel
import com.asap.aljyo.di.ViewModelFactoryProvider
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.White
import dagger.hilt.android.EntryPointAccessors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RankingScreen(
    onBackPressed: () -> Unit,
    groupId: Int
) {
    val context = LocalContext.current

    SideEffect {
        val activity = (context as ComponentActivity)
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb()
            ),
        )
    }

    val factory = EntryPointAccessors.fromActivity(
        context as Activity,
        ViewModelFactoryProvider::class.java
    ).groupRankingViewModelFactory()

    val viewModel: GroupRankingViewModel = viewModel(
        factory = GroupRankingViewModel.provideGroupRankingViewModelFactory(
            factory = factory,
            groupId = groupId
        )
    )

    AljyoTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(vertical = 10.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = White
                    ),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.group_ranking),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp,
                                color = Black01
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackPressed
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                tint = Black01,
                                contentDescription = "Top app bar navigation icon"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                // 시간 선택 bottom sheet
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_question_mark),
                                tint = Color.Unspecified,
                                contentDescription = "Question mark icon"
                            )
                        }

                    }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {

            }
        }
    }
}
