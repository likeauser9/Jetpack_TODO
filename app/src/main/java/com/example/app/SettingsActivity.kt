package com.example.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text(text = "Settings", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        SettingItem(icon = Icons.Default.Notifications, title = "Enable Notifications") {
            Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
        }
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        SettingItem(icon = Icons.Default.Settings, title = "Dark Theme") {
            Switch(checked = darkThemeEnabled, onCheckedChange = { darkThemeEnabled = it })
        }
    }
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .border(2.dp, Color.Red)
//    ) {
//        val width = size.width
//        val height = size.height
//        val heartPath = Path().apply {
//            moveTo(width / 2f, height / 4f)
//
//            // Левая сторона сердца
//            cubicTo(
//                width * 0.1f, height * 0.1f,
//                0f, height * 0.4f,
//                width / 2f, height * 0.6f
//            )
//
//            // Правая сторона сердца
//            cubicTo(
//                width, height * 0.4f,
//                width * 0.9f, height * 0.1f,
//                width / 2f, height / 4f
//            )
//
//            close()
//        }
//
//        drawPath(
//            path = heartPath,
//            color = Color.Red
//        )
//    }

}

@Composable
fun SettingItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(end = 16.dp))
        Text(text = title, fontSize = 18.sp, modifier = Modifier.weight(1f))
        content()
    }
}
