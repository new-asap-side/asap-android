package com.asap.aljyo.ui.composable.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.asap.aljyo.R
import com.asap.aljyo.core.components.report.ReportViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.common.custom.AljyoToggleButton
import com.asap.aljyo.ui.composable.common.dialog.LoadingDialog
import com.asap.aljyo.ui.composable.common.dialog.PrecautionsDialog
import com.asap.aljyo.ui.composable.withdrawal.PrecautionsButton
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.White

private const val maxLength = 50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onBackClick: () -> Unit,
    navigateToComplete: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val survey by viewModel.survey.collectAsStateWithLifecycle()
    var selectedIdx by remember { mutableIntStateOf(-1) }

    AljyoTheme {
        Scaffold(
            containerColor = White,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = White
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.report_group),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Black01,
                                fontSize = 16.fsp
                            ),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { onBackClick() }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close icon"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                var showReportDialog by remember { mutableStateOf(false) }
                var showLoadingDialog by remember { mutableStateOf(false) }

                if (showReportDialog) {
                    PrecautionsDialog(
                        title = stringResource(R.string.complete_report_group),
                        description = stringResource(R.string.complete_report_group_detail),
                        onDismissRequest = { showReportDialog = false },
                        onConfirm = { showReportDialog = false }
                    )
                }

                if (showLoadingDialog) {
                    LoadingDialog()
                }

                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { onBackClick() },
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.fsp
                            )
                        )
                    }

                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        enabled = selectedIdx != -1 && (selectedIdx != 4 || survey.isNotEmpty()),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = White,
                            disabledContainerColor = Grey02,
                        ),
                        onClick = {
                            // TODO: 신고하기 API 연결
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.report_group),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.fsp
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 32.dp
                    )
            ) {
                ReportSurvey(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Grey01)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 24.dp
                        ),
                    selectedIdx = selectedIdx,
                    onChangeIdx = {
                        selectedIdx = it
                    },
                    survey = survey,
                    onChangeSurvey = viewModel::updateSurvey
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row {
                    Text(
                        text = stringResource(R.string.wait),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.fsp,
                        )
                    )

                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_hand),
                        contentDescription = "Wait icon",
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(R.string.withdrawal_information_title),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 16.fsp,
                        )
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    stringArrayResource(R.array.withdrawal_informations).forEach { info ->
                        Row {
                            Text(
                                text = "\u2022",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Black02,
                                    fontSize = 12.fsp,
                                )
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = info,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Black02,
                                    fontSize = 12.fsp,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ReportSurvey(
    modifier: Modifier = Modifier,
    selectedIdx: Int,
    onChangeIdx: (Int) -> Unit,
    survey: String,
    onChangeSurvey: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.report_survey),
            style = MaterialTheme.typography.labelMedium.copy(
                color = Black02,
                fontSize = 14.fsp,
                lineHeight = 22.fsp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        ReportSurveyList(
            modifier = Modifier.fillMaxWidth(),
            selectedIdx = selectedIdx,
            onChangeIdx = onChangeIdx,
            value = survey,
            onChangeValue = onChangeSurvey
        )
    }
}

@Composable
internal fun ReportSurveyList(
    modifier: Modifier = Modifier,
    selectedIdx: Int,
    onChangeIdx: (Int) -> Unit,
    value: String,
    onChangeValue: (String) -> Unit
//    viewModel: WithdrawalViewModel = hiltViewModel()
) {
    val surveyContents = stringArrayResource(R.array.report_survey_list)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        surveyContents.forEachIndexed { index, survey ->
            ReportSurveyItem(
                modifier = Modifier.fillMaxWidth(),
                survey = survey,
                selected = selectedIdx == index,
                onSelect = { onChangeIdx(index) }
            ) {
                if (index == surveyContents.lastIndex && index == selectedIdx) {
                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        value = value,
                        onValueChange = { value ->
                            if (value.contains("\n")) {
                                return@BasicTextField
                            }

                            if (value.length <= maxLength) {
                                onChangeValue(value)
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = Black01,
                            fontSize = 15.fsp,
                            lineHeight = 24.fsp
                        ),
                    ) { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(128.dp)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(6.dp),
                                    color = Grey02
                                )
                                .clip(RoundedCornerShape(6.dp))
                                .background(White)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.etc_hint),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Black04,
                                        fontSize = 15.fsp,
                                        lineHeight = 24.fsp
                                    )
                                )
                            }
                            innerTextField()

                            Text(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Black02
                                        )
                                    ) {
                                        append("${value.length}")
                                    }

                                    append(" / $maxLength")
                                },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Black04,
                                    fontSize = 11.fsp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun ReportSurveyItem(
    modifier: Modifier = Modifier,
    survey: String,
    selected: Boolean,
    onSelect: (Boolean) -> Unit,
    inputField: (@Composable () -> Unit)? = null
) {
    Column {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AljyoToggleButton(
                selected = selected,
                onSelect = onSelect
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = survey,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black01,
                    fontSize = 15.fsp
                )
            )
        }

        if (inputField != null) {
            inputField()
        }
    }
}