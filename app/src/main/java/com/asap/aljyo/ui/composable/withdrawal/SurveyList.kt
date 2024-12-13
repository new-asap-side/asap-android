package com.asap.aljyo.ui.composable.withdrawal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.withdrawal.WithdrawalViewModel
import com.asap.aljyo.ui.theme.AljyoTheme

@Composable
internal fun SurveyList(
    modifier: Modifier = Modifier,
    viewModel: WithdrawalViewModel = hiltViewModel()
) {
    val surveyContents = stringArrayResource(R.array.survey_list)
    val selectedIndex = viewModel.selectedIndex
    var selectedSurvey by remember { mutableStateOf("") }

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
            )
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