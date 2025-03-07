package com.asap.aljyo.ui.composable.search

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asap.aljyo.R
import com.asap.aljyo.core.components.viewmodel.SearchViewModel
import com.asap.aljyo.core.fsp
import com.asap.aljyo.ui.composable.SharedElement
import com.asap.aljyo.ui.theme.Black00
import com.asap.aljyo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SearchTopBar(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onFocusChanged: (Boolean) -> Unit,
    onBackClick: () -> Unit,
) {
    val viewmodel: SearchViewModel = hiltViewModel()
    TopAppBar(
        modifier = modifier,
        title = {
            with(sharedTransitionScope) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val source = remember { MutableInteractionSource() }
                    val query by viewmodel.query.collectAsState()
                    val focusRequester = FocusRequester()

                    LaunchedEffect(Unit) { focusRequester.requestFocus() }

                    IconButton(onClick = onBackClick) {
                        Icon(
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(SharedElement.SEARCH_TITLE),
                                animatedVisibilityScope = animatedContentScope
                            ),
                            painter = painterResource(R.drawable.ic_top_back),
                            contentDescription = "back button icon"
                        )
                    }

                    BasicTextField(
                        modifier = Modifier
                            .onFocusChanged { onFocusChanged(it.isFocused) }
                            .focusRequester(focusRequester = focusRequester)
                            .sharedElement(
                                rememberSharedContentState(SharedElement.SEARCH_BAR),
                                animatedVisibilityScope = animatedContentScope
                            )
                            .weight(1f)
                            .height(44.dp),
                        value = query ?: "",
                        singleLine = true,
                        onValueChange = { viewmodel.onQueryChanged(it) },
                        textStyle = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 15.fsp,
                            color = Black00
                        ),
                    ) { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = query ?: "",
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            interactionSource = source,
                            visualTransformation = VisualTransformation.None,
                            contentPadding = PaddingValues(start = 12.dp),
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewmodel.search() }
                                ) {
                                    Icon(
                                        modifier = Modifier.sharedElement(
                                            rememberSharedContentState(SharedElement.SEARCH_ICON),
                                            animatedVisibilityScope = animatedContentScope
                                        ),
                                        painter = painterResource(R.drawable.ic_search),
                                        contentDescription = "search bar tailing icon"
                                    )
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            placeholder = {
                                Text(
                                    text = "알람 제목을 검색해보세요",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontSize = 15.fsp,
                                        color = Color(0xFFAAAAAA)
                                    ),
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White,
            titleContentColor = Black00
        )
    )
}