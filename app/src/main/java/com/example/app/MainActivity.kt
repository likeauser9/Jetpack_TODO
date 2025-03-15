package com.example.app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String
)

data class DropDownItem(
    val text: String
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val items = listOf(
        BottomNavigationItem(
            title = "Главная",
            selectedIcon = R.drawable.home_select,
            unselectedIcon = R.drawable.home_unselect,
            route = "home"
        ),
        BottomNavigationItem(
            title = "Список",
            selectedIcon =R.drawable.list_select,
            unselectedIcon = R.drawable.list_unselect,
            route = "list"
        ),
        BottomNavigationItem(
            title = "Избранное",
            selectedIcon = R.drawable.favorite_select,
            unselectedIcon = R.drawable.favorite_unselect,
            route = "favorites"
        ),
        BottomNavigationItem(
            title = "Настройки",
            selectedIcon = R.drawable.settings_select,
            unselectedIcon = R.drawable.settings_unselect,
            route = "settings"
        )
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentTitle = items[selectedItemIndex].title
    val navController = rememberNavController()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text("Drawer Title", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    HorizontalDivider()

                    Text("Section 1", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                    NavigationDrawerItem(
                        label = { Text("Item 1") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Item 2") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Text("20") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help and feedback") },
                        selected = false,
                        icon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        onClick = { /* Handle click */ },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    },
                    title = {
                        Text(
                            text = currentTitle,
                            fontFamily = FontFamily(Font(R.font.nunito))
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            selectedItemIndex = 2
                            navController.navigate("favorites")
                        }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Избранное")
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Search, contentDescription = "Поиск")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(bottomNavigationItem.route) {
//                                popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            label = {
                                Text(
                                    text = bottomNavigationItem.title,
                                    fontFamily = FontFamily(Font(R.font.nunito)),
                                )
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = if (index == selectedItemIndex) bottomNavigationItem.selectedIcon else bottomNavigationItem.unselectedIcon),
                                    contentDescription = bottomNavigationItem.title
                                )
                            }
                        )
                    }
                }
            },
            content = { padding ->
                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { HomeScreen() }
                    composable("list") { ListScreen() }
                    composable("favorites") { FavoriteScreen() }
                    composable("settings") { SettingsScreen() }
                }
            }
        )
    }
}

@Composable
fun PersonItem(
    personName: String,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        }
                    )
                }
                .padding(16.dp)
        )  {
            Text(
                text = personName
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false }
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.text) },
                    onClick = {
                        onItemClick(item)
                        isContextMenuVisible = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuSample() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
//        TextField(
//            value = selectedText,
//            onValueChange = {},
//            readOnly = true,
//            modifier = Modifier.menuAnchor(),
//            label = { Text("Choose an option") },
//            trailingIcon = {
//                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
//            }
//        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedText = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun MinimalDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("No category") }

    Column {
        OutlinedButton(
            onClick = { expanded != expanded },
        ) {
            Text(text = selectedText)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Option 1") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("Option 2") },
                onClick = { /* Do something... */ }
            )
        }
    }
}



//    Scaffold(
//        modifier = Modifier.border(2.dp, Color.Green, shape = RoundedCornerShape(8.dp)),
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {}
//            ) {
//                Icon(Icons.Default.Add, contentDescription = null)
//            }
//        },
//        floatingActionButtonPosition = FabPosition.End,
//        content = { padding ->
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding)
//            ) {
//                item {
//                    Button(
//                        onClick = {},
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        Text(
//                            text = "Развернуть"
//                        )
//                    }
//                }
//            }
//        }
//    )

//                TextField(
//                    value = text,
//                    onValueChange = { newText -> text = newText },
//                    label = { Text("Введите заметку") },
//                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
//                )

//                Button(
//                    onClick = {
//                        scope.launch {
//                            scaffoldState.bottomSheetState.partialExpand()
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    Text(
//                        text = "Свернуть"
//                    )
//                }

@Composable
fun MyText(text: String) {

}