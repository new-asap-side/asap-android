package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.UserGroupType


@Composable
internal fun GroupBottomNavBar(
    modifier: Modifier = Modifier,
    userGroupType: UserGroupType,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (userGroupType) {
            UserGroupType.NonParticipant -> NonParticipantBottomBar(
                modifier = Modifier.weight(1f)
            )
            else -> ParticipantBottomBar(
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
private fun ParticipantBottomBar(
    modifier: Modifier = Modifier
) {
    Button (
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        onClick = {}
    ) {
        Text(
            text = stringResource(R.string.modifiy),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
            )
        )
    }
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10),
        onClick = {}
    ) {
        Text(
            text = stringResource(R.string.ranking),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                color = White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ParticipantBottomBarPreview() {
    AljyoTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ParticipantBottomBar(
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NonParticipantBottomBar(
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10),
        onClick = {}
    ) {
        Text(
            text = stringResource(R.string.participate),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                color = White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NonParticipantPreview() {
    AljyoTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NonParticipantBottomBar(
                modifier = Modifier.weight(1f)
            )
        }
    }
}