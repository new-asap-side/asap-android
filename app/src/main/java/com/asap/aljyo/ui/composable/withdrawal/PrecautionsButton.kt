package com.asap.aljyo.ui.composable.withdrawal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.withdrawal.WithdrawalViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Grey02
import com.asap.aljyo.ui.theme.White

@Composable
internal fun PrecautionsButton(
    modifier: Modifier = Modifier,
    viewModel: WithdrawalViewModel = hiltViewModel()
) {
    val checked = viewModel.checkedPrecautions

    val contentColor = if (checked) {
        MaterialTheme.colorScheme.primary
    } else {
        Grey02
    }

    val containerColor = if (checked) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        White
    }

    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(
            if (checked) 1.5.dp else 1.dp,
            contentColor
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = { viewModel.checkPrecautions() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.checked_precautions),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if (checked) MaterialTheme.colorScheme.primary else Black01,
                    fontSize = 16.fsp
                )
            )

            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Check icon",
                tint = contentColor
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    AljyoTheme {
        PrecautionsButton(
            modifier = Modifier
                .width(350.dp)
                .height(68.dp)
        )
    }
}