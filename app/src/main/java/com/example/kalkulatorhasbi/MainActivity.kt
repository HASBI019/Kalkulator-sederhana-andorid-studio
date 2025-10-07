package com.example.kalkulatorhasbi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalkulatorhasbi.ui.theme.KalkulatorHasbiTheme
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.alpha

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalkulatorHasbiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KalkulatorUI()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KalkulatorUI() {
    var angka1 by remember { mutableStateOf("") }
    var angka2 by remember { mutableStateOf("") }
    var hasil by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<String>()) }

    // 🌈 Background gradasi biru ke putih
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0D47A1),
            Color(0xFF1976D2),
            Color(0xFFE3F2FD),
            Color.White
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "KALKULATOR HASBI",
            color = Color(0xFFE3F2FD),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
        )

        // Input angka pertama
        OutlinedTextField(
            value = angka1,
            onValueChange = { angka1 = it },
            label = { Text("Masukkan angka pertama") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFE3F2FD),
                unfocusedBorderColor = Color(0xFFE3F2FD),
                focusedLabelColor = Color(0xFFE3F2FD),
                cursorColor = Color(0xFFE3F2FD)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Input angka kedua
        OutlinedTextField(
            value = angka2,
            onValueChange = { angka2 = it },
            label = { Text("Masukkan angka kedua") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFE3F2FD),
                unfocusedBorderColor = Color(0xFFE3F2FD),
                focusedLabelColor = Color(0xFFE3F2FD),
                cursorColor = Color(0xFFE3F2FD)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Hasil
        Text(
            text = if (hasil.isEmpty()) "Hasil akan muncul di sini" else "Hasil: $hasil",
            color = Color(0xFFE3F2FD),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tombol baris 1
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            KalkulatorButton("+") {
                hasil = hitung(angka1, angka2, '+')
                if (hasil.isNotEmpty()) history = listOf("${angka1} + ${angka2} = $hasil") + history
            }
            KalkulatorButton("-") {
                hasil = hitung(angka1, angka2, '-')
                if (hasil.isNotEmpty()) history = listOf("${angka1} - ${angka2} = $hasil") + history
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tombol baris 2
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            KalkulatorButton("×") {
                hasil = hitung(angka1, angka2, '*')
                if (hasil.isNotEmpty()) history = listOf("${angka1} × ${angka2} = $hasil") + history
            }
            KalkulatorButton("÷") {
                hasil = hitung(angka1, angka2, '/')
                if (hasil.isNotEmpty()) history = listOf("${angka1} ÷ ${angka2} = $hasil") + history
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // History section
        Text(
            text = "Riwayat Perhitungan",
            color = Color(0xFFE3F2FD),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(history) { item ->
                Text(
                    text = item,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

// 🔹 Gambar Killua di bawah, di luar LazyColumn
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.killua),
            contentDescription = "Killua Zoldyck",
            modifier = Modifier
                .fillMaxWidth()        // selebar layar
                .fillMaxHeight(0.8f)   // tinggi 80% layar
                .align(Alignment.CenterHorizontally)
                .alpha(0.25f)          // transparansi lembut
        )

    }
}

@Composable
fun KalkulatorButton(symbol: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)), // 🔵 Tombol biru
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(130.dp)
            .height(60.dp)
    ) {
        Text(
            text = symbol,
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun hitung(a1: String, a2: String, operasi: Char): String {
    return try {
        val x = a1.toFloat()
        val y = a2.toFloat()
        val hasil = when (operasi) {
            '+' -> x + y
            '-' -> x - y
            '*' -> x * y
            '/' -> if (y != 0f) x / y else return "Tidak bisa bagi 0"
            else -> 0f
        }
        hasil.toString()
    } catch (e: Exception) {
        "Input tidak valid"
    }
}

@Preview(showBackground = true)
@Composable
fun KalkulatorPreview() {
    KalkulatorHasbiTheme {
        KalkulatorUI()
    }
}
