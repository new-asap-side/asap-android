package com.asap.aljyo.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


val Int.fsp: TextUnit @Composable get() = textDp()

val Double.fsp: TextUnit @Composable get() = textDp()

val Float.fsp: TextUnit @Composable get() = textDp()

@Composable
private fun Int.textDp() = with(LocalDensity.current) { this@textDp.dp.toSp() }

@Composable
private fun Double.textDp() = with(LocalDensity.current) { this@textDp.dp.toSp() }

@Composable
private fun Float.textDp() = with(LocalDensity.current) { this@textDp.dp.toSp() }