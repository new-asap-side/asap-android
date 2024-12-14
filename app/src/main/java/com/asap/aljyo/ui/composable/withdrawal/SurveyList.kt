package com.asap.aljyo.ui.composable.withdrawal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.withdrawal.WithdrawalViewModel
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.Black04
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.White

private const val maxLength = 50

@Composable
internal fun SurveyList(
    modifier: Modifier = Modifier,
    viewModel: WithdrawalViewModel = hiltViewModel()
) {
    val surveyContents = stringArrayResource(R.array.survey_list)
    val selectedIndex = viewModel.selectedIndex
    var selectedSurvey by remember { mutableStateOf("") }

    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        surveyContents.forEachIndexed { index, survey ->
            SurveyItem(
                modifier = Modifier.fillMaxWidth(),
                survey = survey,
                selected = selectedIndex == index,
                onSelect = {
                    selectedSurvey = survey
                    if (selectedIndex == index) {
                        viewModel.select(null)
                        return@SurveyItem
                    }
                    viewModel.select(index)
                }
            ) {
                if (index == surveyContents.lastIndex && index == selectedIndex) {
                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        value = text,
                        onValueChange = { value ->
                            if (value.length <= maxLength) {
                                text = value
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = Black01,
                            fontSize = 15.sp,
                            lineHeight = 24.sp
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
                            if (text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.etc_hint),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Black04,
                                        fontSize = 15.sp,
                                        lineHeight = 24.sp
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
                                        append("${text.length}")
                                    }

                                    append(" / $maxLength")
                                },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Black04,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AljyoTheme {
        SurveyList(modifier = Modifier.fillMaxWidth())
    }
}