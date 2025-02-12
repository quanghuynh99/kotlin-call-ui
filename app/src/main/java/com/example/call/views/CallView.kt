package com.example.call.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import com.example.call.enums.ProfileViewSize

@Composable
fun CallView(
    profileSize: ProfileViewSize,
    avatarImageUrl: String,
    displayName: String,
    callState: String? = null,
    defaultAvatarResId: Int,
    hasVideo: Boolean = false
) {
    Box(
        modifier = profileSize.modifier
    ) {
        if (hasVideo) {
            Box(modifier = Modifier.fillMaxSize()) {
                ExoplayerExample(url = "https://assets.mixkit.co/active_storage/video_items/100352/1723572658/100352-video-720.mp4")
            }
        } else {
            // Background Image
            AsyncImageView(
                imageUrl = avatarImageUrl,
                defaultAvatarResId = defaultAvatarResId,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(if (profileSize == ProfileViewSize.MINIMIZE) 14.dp else 0.dp))
                    .blur(30.dp)
            )

            // Foreground Content
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Image
                AsyncImageView(
                    imageUrl = avatarImageUrl,
                    defaultAvatarResId = defaultAvatarResId,
                    modifier = Modifier
                        .size(if (profileSize == ProfileViewSize.MINIMIZE) 40.dp else 64.dp)
                        .clip(CircleShape)
                )

                // Display Name
                DisplayNameView(
                    name = displayName,
                    isMinimized = profileSize == ProfileViewSize.MINIMIZE
                )

                // Call State
                callState?.let {
                    ContentView(state = callState)
                }
            }
        }
    }
}