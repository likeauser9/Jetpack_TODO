package com.example.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoApp() {
    var showDialog by remember { mutableStateOf(false) }
    var tasks by remember { mutableStateOf(listOf("Buy groceries", "Study Kotlin", "Workout")) }
    var newTask by remember { mutableStateOf("") }

    MaterialTheme(colorScheme = darkColorScheme()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "Tasks",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            tasks.forEach { task ->
                TaskItem(task)
            }
            Spacer(modifier = Modifier.weight(1f))
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (newTask.isNotEmpty()) {
                        tasks = tasks + newTask
                        newTask = ""
                        showDialog = false
                    }
                }) { Text("Add") }
            },
            text = {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("New Task") }
                )
            }
        )
    }
}

//@Composable
//fun TaskItem(task: String) {
//    var expanded by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF333333), RoundedCornerShape(8.dp))
//                .padding(16.dp)
//                .clickable { expanded = !expanded },
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(task, color = Color.White, modifier = Modifier.weight(1f))
//        }
//        AnimatedVisibility(visible = expanded) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//                    .border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(10)),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Details about task",
//                    color = Color.Gray,
//                    modifier = Modifier.padding(start = 10.dp)
//                )
//                IconButton(
//                    onClick = {}
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.add),
//                        contentDescription = "Add details",
//                    )
//                }
//            }
//        }
//    }
//}