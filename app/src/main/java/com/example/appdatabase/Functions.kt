package com.example.appdatabase

import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.appdatabase.ui.theme.AppDatabaseTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appdatabase.roomDB.Book
import com.example.appdatabase.roomDB.BookDataBase
import com.example.appdatabase.viewModel.BookViewModel
import com.example.appdatabase.viewModel.Repository
import kotlinx.coroutines.launch

class Functions : ComponentActivity() {
    fun delete(id: Int) {
        MainActivity().viewModel.deleteBookQuery(id)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App(
        modifier: Modifier = Modifier,
        viewModel: BookViewModel,
        mainActivity: MainActivity
    ) {
        var id by remember {
            mutableStateOf("")
        }
        var nome by remember {
            mutableStateOf("")
        }
        var autor by remember {
            mutableStateOf("")
        }
        var preco by remember {
            mutableStateOf("")
        }
        val book = Book(
            nome,
            autor,
            preco
        )

        var bookList by remember {
            mutableStateOf(listOf<Book>())
        }

        viewModel.getBook().observe(mainActivity) {
            bookList = it
        }

        var registroExistente = false
        val context = LocalContext.current
        val functions = Functions()

        Column(
            Modifier
                .background(Color(38, 38, 38))
                .fillMaxHeight()
        ) {
            CenterAlignedTopAppBar(
                title = {Text(
                    text = "Open Book",
                    color = Color(254, 102, 0),
                    fontSize = 30.sp
                ) },
                modifier = modifier
            )

            Row(
                Modifier
                    .padding(15.dp)
            ) {

            }
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Livro") },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(254, 102, 0),
                        unfocusedLabelColor = Color.White,
                        unfocusedContainerColor = Color(86, 86, 86),
                        unfocusedTextColor = Color.White,

                        focusedIndicatorColor = Color(254, 102, 0),
                        focusedLabelColor = Color(254, 102, 0),
                        focusedContainerColor = Color(86, 86, 86),
                        focusedTextColor = Color.White,

                        cursorColor = Color(254, 102, 0)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

            Row(
                Modifier
                    .padding(15.dp)
            ) {

            }
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                TextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Nome do Autor") },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(254, 102, 0),
                        unfocusedLabelColor = Color.White,
                        unfocusedContainerColor = Color(86, 86, 86),
                        unfocusedTextColor = Color.White,

                        focusedIndicatorColor = Color(254, 102, 0),
                        focusedLabelColor = Color(254, 102, 0),
                        focusedContainerColor = Color(86, 86, 86),
                        focusedTextColor = Color.White,

                        cursorColor = Color(254, 102, 0)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
            Row(
                Modifier
                    .padding(15.dp)
            ) {

            }
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                TextField(
                    value = preco,
                    onValueChange = { preco = it },
                    label = { Text("Preco") },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(254, 102, 0),
                        unfocusedLabelColor = Color.White,
                        unfocusedContainerColor = Color(86, 86, 86),
                        unfocusedTextColor = Color.White,

                        focusedIndicatorColor = Color(254, 102, 0),
                        focusedLabelColor = Color(254, 102, 0),
                        focusedContainerColor = Color(86, 86, 86),
                        focusedTextColor = Color.White,

                        cursorColor = Color(254, 102, 0)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }
            Row(
                Modifier
                    .padding(15.dp)
            ) {

            }

            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                val botaoNovo = Button(
                    onClick = {
                        id = ""
                        nome = ""
                        autor = ""
                        preco = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(254, 102, 0)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Novo",
                        color = Color.White
                    )
                }

                val botaoSalvar = Button(
                    onClick = {
                        viewModel.upsertBook(book)
                        id = ""
                        nome = ""
                        autor = ""
                        preco = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(254, 102, 0)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Salvar",
                        color = Color.White
                    )
                }

                val botaoAtualizar = Button(
                    onClick = {
                        if (id != "") {
                            viewModel.updateBook(nome, autor, preco, id)
                        } else {
                            Toast.makeText(context, "Selecione um registro para atualizar!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(254, 102, 0)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Atualizar",
                        color = Color.White
                    )
                }
            }

            Column (
                Modifier
                    .padding(24.dp)
            ){
                LazyColumn {
                    items(bookList) { book ->
                        Row(
                            Modifier
                                .fillMaxWidth(1f)
                                .padding(vertical = 4.dp),
                            Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier,
                                onClick = {
                                    id = "${book.id}"
                                    nome = "${book.nome}"
                                    autor = "${book.autor}"
                                    preco = "${book.preco}"
                                },
                                colors = CardColors(
                                    containerColor = Color(86, 86, 86),
                                    contentColor = Color.White,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color.DarkGray
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${book.nome}",
                                            color = Color(254, 102, 0),
                                            style = MaterialTheme.typography.titleLarge,
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Text(
                                            text = "${book.preco}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    Row(

                                    ) {
                                        Text(
                                            text = "${book.autor}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Icon(
                                            Icons.Rounded.Delete,
                                            contentDescription = "Deletar Livro",
                                            Modifier
                                                .clickable {
                                                    id = "${book.id}"
                                                    nome = "${book.nome}"
                                                    autor = "${book.autor}"
                                                    preco = "${book.preco}"
                                                    viewModel.deleteBook(nome, autor, preco, id)
                                                    id = ""
                                                    nome = ""
                                                    autor = ""
                                                    preco = ""
                                                },
                                            tint = Color(254, 108, 0)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.weight(3f))
            }
        }
    }
}