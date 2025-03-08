package com.asap.aljyo.ui.composable.main.my_page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.asap.aljyo.R
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.theme.Black01
import com.asap.aljyo.ui.theme.Red01
import com.asap.aljyo.ui.theme.Red02
import com.asap.aljyo.ui.theme.White

enum class CustomItemState {
    LOCK, UNLOCKABLE, UNLOCK
}

@Composable
fun CustomItem(
    item: ProfileCustom,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
     Box(
         modifier = Modifier
             .clickable(onClick = onClick)
             .size(100.dp)
             .clip(RoundedCornerShape(14.dp))
             .then(
                 if (isSelected) Modifier.background(Red02).border(1.5.dp, Red01, RoundedCornerShape(14.dp)) else Modifier
             )
     ) {
         AsyncImage(
             modifier = Modifier
                 .fillMaxSize()
                 .then(
                     if (item.state != CustomItemState.UNLOCK) {
                         Modifier.blur(10.dp)
                     } else {
                         Modifier
                     }
                 ),
             model = item.image,
             error = painterResource(R.drawable.ic_card_touch),
             contentDescription = "CUSTOM ITEM IMAGE",
             contentScale = ContentScale.Crop
         )
     }

    if (item.state == CustomItemState.LOCK) {
        Box(
            modifier = Modifier
                .background(
                    color = Black01.copy(0.5f),
                    shape = RoundedCornerShape(14.dp)
                )
                .size(102.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    modifier = Modifier.padding(bottom = 6.dp),
                    painter = painterResource(R.drawable.ic_custom_lock),
                    contentDescription = "LOCK ICON",
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = "20,000점\n도달 시 해제",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.fsp,
                        color = White,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (item.state == CustomItemState.UNLOCKABLE) {
        Box(
            modifier = Modifier
                .clickable(onClick = onClick)
                .background(
                    color = Red01.copy(0.5f),
                    shape = RoundedCornerShape(14.dp)
                )
                .size(102.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = "터치하여\n아이템 해제!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 14.fsp,
                    color = White,
                    lineHeight = 22.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun PreviewCustomItem() {
    val dummy = ProfileCustom(R.drawable.ic_custom_2, CustomItemState.UNLOCK)

    CustomItem(dummy, true) {}
}