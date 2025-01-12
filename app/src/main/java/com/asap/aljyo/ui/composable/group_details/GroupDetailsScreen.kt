package com.asap.aljyo.ui.composable.group_details

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.asap.aljyo.R
import com.asap.aljyo.core.components.edit.GroupEditState
import com.asap.aljyo.core.components.group_details.GroupDetailsViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.core.navigation.ScreenRoute
import com.asap.aljyo.core.navigation.navtype.CustomNavType
import com.asap.aljyo.di.ViewModelFactoryProvider
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.common.ErrorBox
import com.asap.aljyo.ui.composable.common.dialog.LoadingDialog
import com.asap.aljyo.ui.composable.common.dialog.PrecautionsDialog
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.UserGroupType
import com.google.gson.Gson
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(
    navController: NavHostController,
    groupId: Int,
) {
    val context = LocalContext.current
    SideEffect {
        val activity = (context as ComponentActivity)
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                White.toArgb(),
                White.toArgb(),
            )
        )
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    val coroutineScope = rememberCoroutineScope()

    val factory = EntryPointAccessors.fromActivity(
        activity = context as Activity,
        entryPoint = ViewModelFactoryProvider::class.java
    ).groupDetailsViewModelFactory()

    val viewModel: GroupDetailsViewModel = viewModel(
        factory = GroupDetailsViewModel.provideGroupDetailsViewModelFactory(
            factory = factory,
            groupId = groupId
        )
    )

    val userGroupType = viewModel.userGroupType
    val groupDetails by viewModel.groupDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.groupEdit.collect{
            navController.navigate(
                "${ScreenRoute.GroupEdit.route}/${CustomNavType.groupEditType.serializeAsValue(it)}"
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.personalEdit.collect {
            navController.navigate(
                "${ScreenRoute.PersonalEdit.route}/$groupId/${CustomNavType.PersonalEditType.serializeAsValue(it)}"
            )
        }
    }

    AljyoTheme {
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        var showLeaveGroupDialog by remember { mutableStateOf(false) }
        var showReportGroupDialog by remember { mutableStateOf(false) }

        val hideBottomSheet = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                showBottomSheet = false
            }
        }

        if (showBottomSheet) {
            BottomSheet(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                sheetState = sheetState,
                onDismissRequest = { hideBottomSheet() },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.see_more),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 18.fsp,
                                color = Black01
                            )
                        )
                        IconButton(
                            onClick = { hideBottomSheet() }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "close"
                            )
                        }
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showLeaveGroupDialog = true
                                hideBottomSheet()
                            }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_leave_group),
                            contentDescription = "Leave group icon"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.leave_group),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.fsp,
                                color = Black02
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("${ScreenRoute.Report.route}/$groupId")
                                hideBottomSheet()
                            }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_leave_group),
                            contentDescription = "Leave group icon"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.report_group),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.fsp,
                                color = Black02
                            )
                        )
                    }
                }
            }
        }

        if (showLeaveGroupDialog) {
            PrecautionsDialog(
                title = stringResource(R.string.ask_leave_group),
                description = stringResource(R.string.ranking_initialized),
                onDismissRequest = { showLeaveGroupDialog = false },
                onConfirm = {
                    showLeaveGroupDialog = false
                    viewModel.withdrawGroup()
                }
            )
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            alpha = 0f
                        ),
                    ),
                    title = {
                        AlarmTimer(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .background(color = White.copy(alpha = 0.88f))
                                .padding(
                                    vertical = 6.dp,
                                    horizontal = 14.dp
                                ),
                            viewModel = viewModel
                        )
                    },
                    actions = {
                        when (userGroupType) {
                            null,
                            UserGroupType.NonParticipant -> Unit

                            UserGroupType.Leader,
                            UserGroupType.Participant -> {
                                IconButton(
                                    onClick = { showBottomSheet = true }
                                ) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        tint = White,
                                        contentDescription = "Top app bar first action"
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                tint = White,
                                contentDescription = "Top app bar navigation icon"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                GroupBottomNavBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .background(White)
                        .padding(20.dp),
                    userGroupType = userGroupType,
                    enabled = viewModel.checkJoinGroup(),
                    onRankingClick = {
                        navController.navigate(route = "${ScreenRoute.Ranking.route}/$groupId")
                    },
                    navigateToGroupEdit = {
                        viewModel.navigateToGroupEdit()
                    },
                    navigateToPersonalEdit = {
                        viewModel.navigateToPersonalEdit()
                    },
                    onJoinClick = {
                        viewModel.joinGroup()
                    }
                )
            }
        ) { padding ->
            Surface(
                modifier = Modifier
                    .padding(bottom = padding.calculateBottomPadding())
                    .fillMaxSize(),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        GroupSummation(
                            modifier = Modifier.fillMaxWidth(),
                            viewModel = viewModel
                        )
                    }
                    item {
                        AlarmDetails(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White),
                            viewModel = viewModel
                        )
                    }
                    item {
                        GroupMembers(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                                .padding(
                                    vertical = 24.dp,
                                    horizontal = 20.dp
                                ),
                            viewModel = viewModel,
                        )
                    }
                }

                if (groupDetails is UiState.Error) {
                    ErrorBox(modifier = Modifier.fillMaxSize()) {
                        viewModel.fetchGroupDetails()
                    }
                }

                if (isLoading) {
                    LoadingDialog()
                }
            }
        }
    }

}