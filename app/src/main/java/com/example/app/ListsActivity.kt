package com.example.app

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    val tasks = remember { mutableStateListOf<String>() }
    var newEvent by remember { mutableStateOf("") }

    var isToastShown by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedText by remember { mutableStateOf("No category") }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isModalOpen by remember { mutableStateOf(false) }
    var tasks_my by remember { mutableStateOf(listOf("Buy groceries", "Study Kotlin", "Workout")) }
    var newTask by remember { mutableStateOf("") }

    var selectedColor by remember { mutableStateOf(Color.Blue) }
    var selectedCategory by remember { mutableStateOf("General") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                text = { Text("Show snackbar") },
//                icon = { Icon(Icons.Filled.Build, contentDescription = "") },
//                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Snackbar")
//                    }
//                }
//            )
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.End,
//                verticalArrangement = Arrangement.Bottom
//            ) {
//                ExtendedFloatingActionButton(
//                    text = { Text("Show Snackbar") },
//                    icon = { Icon(Icons.Filled.Build, contentDescription = "") },
//                    onClick = {
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Snackbar")
//                        }
//                    }
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                FloatingActionButton(onClick = { /* action for + button */ }) {
//                    Text("+")
//                }
//            }
            FloatingActionButton(onClick = { isModalOpen = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
//                Text("+")
            }
        },
        content = {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = newEvent,
                            onValueChange = { newEvent = it },
                            label = {
                                Text(
                                    text = "Введите заметку",
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.nunito)),
                                    )
                                )
                            },
                            textStyle = TextStyle(
                                fontFamily = FontFamily(Font(R.font.nunito)),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
//                    OutlinedButton(
//                        onClick = {  }
//                    ) {
//                        Text(
//                            text = "No category"
//                        )
//                    }

                            ExposedDropdownMenuWithButton(
                                snackbarHostState = snackbarHostState,
                                selectedText = selectedText,
                                onSelectedTextChanged = { newCategory ->
                                    selectedText = newCategory
                                }
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Button(
                                onClick = {
                                    scope.launch {
                                        if (selectedText == "No category") {
                                            snackbarHostState.showSnackbar("Выберите категорию!")
                                        } else {
                                            scaffoldState.bottomSheetState.partialExpand()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Добавить заметку"
                                )
                            }
                        }
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Button(
                            onClick = {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Развернуть"
                            )
                        }

                        Button(
                            onClick = {
                                isSheetOpen = true
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Развернуть"
                            )
                        }

//                        Text(
//                            text = "Tasks",
//                            fontSize = 32.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(bottom = 16.dp)
//                        )
                        tasks_my.forEach { task ->
                            TaskItem(task)
                        }
                    }
                }
            }

            if (isSheetOpen) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        isSheetOpen = false
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                }
            }

            if (isModalOpen) {
//                AlertDialog(
//                    onDismissRequest = { showDialog = false },
//                    confirmButton = {
//                        TextButton(onClick = {
//                            if (newTask.isNotEmpty()) {
//                                tasks = tasks + newTask
//                                newTask = ""
//                                showDialog = false
//                            }
//                        }) { Text("Add") }
//                    },
//                    text = {
//                        OutlinedTextField(
//                            value = newTask,
//                            onValueChange = { newTask = it },
//                            label = { Text("New Task") }
//                        )
//                    }
//                )

                AlertDialog(
                    onDismissRequest = {isModalOpen = false},
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newTask.isNotEmpty()) {
                                    tasks_my += newTask
                                    newTask = ""
                                    isModalOpen = false
                                }
                            },
                        ) {
                            Text(text = "Add")
                        }
                    },
                    text = {
                        Column {
                            Text(text = "Enter a task", fontSize = 25.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = newTask,
                                onValueChange = { newTask = it },
                                label = { Text("New Task") }
                            )
                            Text("Select Color:")
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                listOf(Color.Blue, Color.Green, Color.Red, Color.Yellow, Color.Cyan).forEach { color ->
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(color, CircleShape)
                                            .clickable { selectedColor = color }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Category:")
                            DropdownMenuExample(selectedCategory) { selectedCategory = it }
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun DropdownMenuExample(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("General", "Work", "Personal", "Urgent")
    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(onClick = { expanded = true }) {
            Text(selectedCategory)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { category ->
                DropdownMenuItem(text = { Text(category) }, onClick = {
                    onCategorySelected(category)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun ExposedDropdownMenuWithButton(
    snackbarHostState: SnackbarHostState,
    selectedText: String,
    onSelectedTextChanged: (String) -> Unit
) {
    val options = listOf("No category", "Work", "Personal", "Wishlist", "Create New")
    var expanded by remember { mutableStateOf(false) }


    Column {
        OutlinedButton(
            onClick = { expanded = true },
        ) {
            Text(text = selectedText)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }, // Закрытие при клике вне
            modifier = Modifier.heightIn(max = 300.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelectedTextChanged(option)
//                        selectedText = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: String) {
    var isModalOpen by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var tasks_my by remember { mutableStateOf(listOf("Buy groceries", "Study Kotlin", "Workout")) }
    var newTask by remember { mutableStateOf("") }

    if (isModalOpen) {
        AlertDialog(
            onDismissRequest = {isModalOpen = false},
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newTask.isNotEmpty()) {
                            tasks_my += newTask
                            newTask = ""
                            isModalOpen = false
                        }
                    },
                ) {
                    Text(text = "Add")
                }
            },
            text = {
                Column {
                    Text(text = "Enter a details", fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newTask,
                        onValueChange = { newTask = it },
                        label = { Text("New details") }
                    )
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF333333), RoundedCornerShape(8.dp))
                .padding(16.dp)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(task, color = Color.White, modifier = Modifier.weight(1f))
        }
        AnimatedVisibility(visible = expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(10)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Details about task",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 10.dp)
                )
                IconButton(
                    onClick = { isModalOpen = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add details",
                    )
                }
            }
        }
    }
}