package com.example.call.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun AsyncImageView(
    imageUrl: String,
    defaultAvatarResId: Int,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(id = defaultAvatarResId),
        error = painterResource(id = defaultAvatarResId),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}