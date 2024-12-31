package com.example.call

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.call.enums.CallType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)) {
                val context = LocalContext.current
                Column(modifier = Modifier.fillMaxSize()) {
                    Button(onClick = {
                        // Mở VideoCallActivity
                        val intent = Intent(context, CallActivity::class.java)
                        intent.putExtra("CallType", CallType.VIDEO_CALL)
                        context.startActivity(intent)
                    }) {
                        Text("Video Call", color = Color.White)
                    }
                    Button(onClick = {
                        // Mở VoiceCallActivity
                        val intent = Intent(context, CallActivity::class.java)
                        intent.putExtra("CallType", CallType.VOICE_CALL)
                        context.startActivity(intent)
                    }) {
                        Text("Voice Call", color = Color.White)
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CallTheme {
//    }
//}