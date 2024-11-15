package com.example.projeto_livraria_mobile.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projeto_livraria_mobile.books.theme.Projeto_Livraria_MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projeto_Livraria_MobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    Projeto_Livraria_MobileTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            BookApp()
        }
    }
}