package com.example.call.models

import android.annotation.SuppressLint
import android.telecom.Call
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.call.enums.CallType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

// Mock Entity
data class UserEntity(
    val userId: String,
    val avatarImageUrl: String,
    val displayName: String
) {
    companion object {
        val default = UserEntity( // Init default value
            userId = "",
            avatarImageUrl = "",
            displayName = ""
        )
    }
}

// Model
class CallViewModel : ViewModel() {
    // Mock data
    val calleeData = MutableStateFlow<UserEntity>(UserEntity.default)
    val callerData = MutableStateFlow<UserEntity>(UserEntity.default)

    // Device handler
    private var _callType = MutableStateFlow<CallType>(CallType.VOICE_CALL)
    val callType: StateFlow<CallType> = _callType

    private val _hasSpeaker = MutableStateFlow(false)
    val hasSpeaker: StateFlow<Boolean> = _hasSpeaker

    private val _hasVideo = MutableStateFlow(false)
    val hasVideo: StateFlow<Boolean> = _hasVideo

    private val _hasMicrophone = MutableStateFlow(true)
    val hasMicrophone: StateFlow<Boolean> = _hasMicrophone

    // Timer & CallState
    private var callDurationSeconds = 0
    private val _callState = MutableStateFlow("00:00")
    val callState: StateFlow<String> get() = _callState

    fun setupData(callType: CallType) {
        viewModelScope.launch {
            _callType.value = callType

            // Callee & Caller data
            calleeData.value = UserEntity(
                userId = UUID.randomUUID().toString(),
                avatarImageUrl = "https://images.pexels.com/photos/29879483/pexels-photo-29879483/free-photo-of-festive-christmas-ornament-on-pine-tree-branch.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                displayName = "Thomas Editor"
            )
            callerData.value = UserEntity(
                userId = UUID.randomUUID().toString(),
                avatarImageUrl = "https://images.pexels.com/photos/11288126/pexels-photo-11288126.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                displayName = "Issac NewYork"
            )

            // Setup default value for button
            _hasVideo.value = callType == CallType.VIDEO_CALL
            _hasSpeaker.value = callType == CallType.VIDEO_CALL
            _hasMicrophone.value = callType == CallType.VOICE_CALL

            // Setup and Start Timer
            delay(2000)
            _callState.value = "Connected"
            startCallTimer()
        }
    }

    private fun startCallTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                callDurationSeconds++
                _callState.value = formatCallDuration(callDurationSeconds)
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatCallDuration(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%02d:%02d", minutes, secs)
        }
    }

    fun toggleSpeaker() {
        _hasSpeaker.value = !_hasSpeaker.value
    }

    fun toggleVideo() {
        _callType.value = CallType.VIDEO_CALL
        _hasVideo.value = !_hasVideo.value
    }

    fun toggleMicrophone() {
        _hasMicrophone.value = !_hasMicrophone.value
    }
}