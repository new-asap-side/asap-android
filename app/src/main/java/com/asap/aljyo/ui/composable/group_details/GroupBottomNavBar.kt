package com.asap.aljyo.ui.composable.group_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R
import com.asap.aljyo.ui.composable.common.sheet.BottomSheet
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Black02
import com.asap.aljyo.ui.theme.White
import com.asap.domain.entity.remote.UserGroupType
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
internal fun GroupBottomNavBar(
    modifier: Modifier = Modifier,
    userGroupType: UserGroupType?,
    onRankingClick: () -> Unit,
    navigateToGroupEdit: () -> Unit,
    navigateToPersonalEdit: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val mod = Modifier.weight(1f).height(52.dp)

        when (userGroupType) {
            UserGroupType.NonParticipant -> NonParticipantBottomBar(
                modifier = mod
            )

            UserGroupType.Leader -> ParticipantBottomBar(
                modifier = mod,
                isLeader = true,
                onRankingClick = onRankingClick,
                navigateToGroupEdit = navigateToGroupEdit,
                navigateToPersonalEdit = navigateToPersonalEdit
            )

            UserGroupType.Participant -> ParticipantBottomBar(
                modifier = mod,
                isLeader = false,
                onRankingClick = onRankingClick,
                navigateToPersonalEdit = navigateToPersonalEdit,
                navigateToGroupEdit = {}
            )

            null -> Unit
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParticipantBottomBar(
    modifier: Modifier = Modifier,
    isLeader: Boolean,
    onRankingClick: () -> Unit,
    navigateToGroupEdit: () -> Unit,
    navigateToPersonalEdit: () -> Unit
) {
    var showPopup by remember { mutableStateOf(true) }
    var popupWidth by remember { mutableIntStateOf(0) }
    var popupOffset by remember { mutableStateOf(IntOffset.Zero) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val hide = {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    if (showBottomSheet) {
        BottomSheet(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.edit_alarm),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 18.sp,
                            color = Black01
                        )
                    )
                    IconButton(
                        onClick = { hide() }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "close"
                        )
                    }
                }
            }
        ) {
            if (isLeader) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToGroupEdit()
                        }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Edit group"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.edit_group),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            color = Black02
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navigateToPersonalEdit()
                    }
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_setting),
                    contentDescription = "Edit setting"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.edit_private_setting),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = Black02
                    )
                )
            }
        }
    }

    Button(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        onClick = {
            showBottomSheet = true
        }
    ) {
        Text(
            text = stringResource(R.string.modifiy),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
            )
        )
    }
    Button(
        modifier = modifier.onGloballyPositioned { coordinates ->
            val y = coordinates.positionInRoot().y.roundToInt()
            val btnHeight = coordinates.size.height

            val rootBounds = coordinates.boundsInRoot()

            val popupX = rootBounds.right.toInt() - popupWidth
            val popupY = y - btnHeight

            popupOffset = IntOffset(
                x = popupX,
                y = popupY
            )

        },
        shape = RoundedCornerShape(10),
        onClick = onRankingClick
    ) {
        if (showPopup) {
            ConfirmRankingPopUp(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    popupWidth = coordinates.size.width
                },
                onDismiss = {
                    showPopup = false
                },
                offset = popupOffset
            )
        }
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
                modifier = Modifier.weight(1f),
                isLeader = true,
                onRankingClick = { },
                navigateToGroupEdit = {},
                navigateToPersonalEdit = {}
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