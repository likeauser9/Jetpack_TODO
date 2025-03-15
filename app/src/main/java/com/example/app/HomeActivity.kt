package com.example.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    val today = LocalDate.now()
    val birthday = LocalDate.of(today.year, 2, 6)

    val nextBirthday = if (today.isAfter(birthday)) birthday.plusYears(1) else birthday
    val daysUntilBirthday = ChronoUnit.DAYS.between(today, nextBirthday)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = "Добро пожаловать в приложение Заметки",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.nunito)),
                fontStyle = FontStyle.Italic,
                lineHeight = 30.sp
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "До моего ДР осталось $daysUntilBirthday дней"
            )
        }
    }
}