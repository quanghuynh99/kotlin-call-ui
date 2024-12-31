package com.example.call

import android.annotation.SuppressLint
import android.os.Bundle
import android.telecom.Call
import android.view.MenuItem
import android.widget.VideoView
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.example.call.enums.CallType
import com.example.call.enums.ProfileViewSize
import com.example.call.models.CallViewModel
import com.example.call.models.UserEntity
import com.example.call.views.HorizontalButton
import com.example.call.views.VideoView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CallActivity : AppCompatActivity() {
    private var callType: CallType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callType = intent.getSerializableExtra("CallType") as? CallType
        // Navigation bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        // Bind view model
        val viewModel = ViewModelProvider(this)[CallViewModel::class.java]
        val profileViewModel: CallViewModel by viewModels()
        profileViewModel.setupData(callType = callType ?: CallType.VOICE_CALL)

        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Bind ViewModel
                val calleeData by viewModel.calleeData.collectAsState()
                val callerData by viewModel.callerData.collectAsState()
                val isVideoCall by viewModel.callType.collectAsState()
                val callState by viewModel.callState.collectAsState()

                val hasVideo by viewModel.hasVideo.collectAsState()
                val hasSpeaker by viewModel.hasSpeaker.collectAsState()
                val hasMicrophone by viewModel.hasMicrophone.collectAsState()

                // Remote user
                Box {
                    VideoView(
                        profileSize = ProfileViewSize.FULLSCREEN,
                        avatarImageUrl = calleeData.avatarImageUrl ?: "",
                        displayName = calleeData.displayName,
                        callState = callState,
                        defaultAvatarResId = R.drawable.img_avatar_default
                    )

                    // Local user
                    if (callType == CallType.VIDEO_CALL) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 100.dp, end = 16.dp)
                        ) {
                            VideoView(
                                profileSize = ProfileViewSize.MINIMIZE,
                                avatarImageUrl = callerData.avatarImageUrl,
                                displayName = callerData.displayName,
                                defaultAvatarResId = R.drawable.img_avatar_default,
                                hasVideo = hasVideo
                            )
                        }
                    }
                }

                // Bottom Buttons
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(
                                bottom = WindowInsets.systemBars
                                    .asPaddingValues()
                                    .calculateBottomPadding() + 30.dp
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalButton(
                            iconResId = if (hasSpeaker) R.drawable.ic_speaker_enable else R.drawable.ic_speaker_disable,
                            label = if (hasSpeaker) "Speaker out" else "Speaker in",
                            onClick = {
                                //
                                viewModel.toggleSpeaker()
                            })
                        HorizontalButton(
                            iconResId = if (hasVideo) R.drawable.ic_camera_enable else R.drawable.ic_camera_disable,
                            label = "Camera",
                            onClick = {
                                // Handle camera action
                                viewModel.toggleVideo()
                                callType = CallType.VIDEO_CALL
                            })
                        HorizontalButton(
                            iconResId = if (hasMicrophone) R.drawable.ic_microphone_enable else R.drawable.ic_microphone_disable,
                            label = if (hasMicrophone) "Mute" else "Un mute",
                            onClick = {
                                //
                                viewModel.toggleMicrophone()
                            })
                        HorizontalButton(iconResId = R.drawable.ic_end, label = "End", onClick = {
                            //
                            finish()
                        })
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

