package com.lifting.app.feature_home.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserImage(
    modifier: Modifier = Modifier,
    userImage: String,
    onProfilePictureClicked: () -> Unit
) {
    AsyncImage(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clickable { onProfilePictureClicked() },
        model = userImage,
        contentDescription = "Circular User Image",
    )
}