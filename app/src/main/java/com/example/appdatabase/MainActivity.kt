package com.example.appdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.appdatabase.roomDB.Book
import com.example.appdatabase.roomDB.BookDataBase
import com.example.appdatabase.ui.theme.AppDatabaseTheme
import com.example.appdatabase.viewModel.BookViewModel
import com.example.appdatabase.viewModel.Repository
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    @Serializable
    object Inicial
    @Serializable
    object Cadastro

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BookDataBase::class.java,
            "book.db"
        ).build()
    }

    public val viewModel by viewModels<BookViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T{
                    return BookViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val act = this
                    // TelaCadastro(Modifier, viewModel, this)
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = Inicial) {
                        composable<Inicial> {
                            TelaInicial(
                                Modifier,
                                viewModel,
                                act,
                                onNavigateToCadastro = { navController.navigate(Cadastro) },
                                navController
                            )
                        }
                        composable<Cadastro> {
                            TelaCadastro(
                                Modifier,
                                viewModel,
                                act,
                                navController
                            )
                        }
                        composable(
                            "atualizar/{id}/{nome}/{autor}/{editora}/{ano}/{preco}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType },
                                navArgument("nome") { type = NavType.StringType },
                                navArgument("autor") { type = NavType.StringType },
                                navArgument("editora") { type = NavType.StringType },
                                navArgument("ano") { type = NavType.StringType },
                                navArgument("preco") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments!!.getInt("id")
                            val nome = backStackEntry.arguments!!.getString("nome")
                            val autor = backStackEntry.arguments!!.getString("autor")
                            val editora = backStackEntry.arguments!!.getString("editora")
                            val ano = backStackEntry.arguments!!.getString("ano")
                            val preco = backStackEntry.arguments!!.getString("preco")

                            if (nome != null) {
                                if (autor != null) {
                                    if (editora != null) {
                                        if (ano != null) {
                                            if (preco != null) {
                                                TelaAtualizar(
                                                    Modifier,
                                                    viewModel,
                                                    act,
                                                    id,
                                                    nome,
                                                    autor,
                                                    editora,
                                                    ano,
                                                    preco,
                                                    navController
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TelaInicial(
        modifier: Modifier = Modifier,
        viewModel: BookViewModel,
        mainActivity: MainActivity,
        onNavigateToCadastro: () -> Unit,
        navHostController: NavHostController
    ) {
        var bookList by remember {
            mutableStateOf(listOf<Book>())
        }

        viewModel.getAllBooks().observe(mainActivity) {
            bookList = it
        }

        val context = LocalContext.current

        Column(
            Modifier
                .background(Color(38, 38, 38))
                .fillMaxHeight()
        ) {
            CenterAlignedTopAppBar(
                title = {Text(
                    text = "Livros Cadastrados",
                    color = Color(254, 102, 0),
                    fontSize = 30.sp
                ) },
                actions = {
                    IconButton(onClick = {onNavigateToCadastro()}) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Novo",
                            modifier = Modifier.padding(end = 2.dp)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(25, 25, 25),
                    scrolledContainerColor = Color(25, 25, 25),
                    navigationIconContentColor = Color(254, 102, 0),
                    titleContentColor = Color(254, 102, 0),
                    actionIconContentColor = Color(254, 102, 0)
                ),
                modifier = modifier
            )

            Row (
                modifier.padding(3.dp)
            ){

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
                                .padding(vertical = 8.dp),
                            Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier,
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
                                            fontSize = 24.sp
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Icon(
                                            Icons.Rounded.Delete,
                                            contentDescription = "Deletar Livro",
                                            Modifier
                                                .clickable {
                                                    viewModel.deleteBook(
                                                        book.nome,
                                                        book.autor,
                                                        book.editora,
                                                        book.ano,
                                                        book.preco,
                                                        book.id.toString()
                                                    )
                                                },
                                            tint = Color(254, 102, 0)
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "R$ ${book.preco}",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontSize = 24.sp
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Autor: ${book.autor}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Editora: ${book.editora}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Ano de Publicação: ${book.ano}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Button(
                                            onClick = {
                                                navHostController.navigate(
                                                    "atualizar/${book.id}/${book.nome}/${book.autor}/${book.editora}/${book.ano}/${book.preco}",
                                                )
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(254, 102, 0)),
                                            modifier = Modifier
                                                .align(alignment = Alignment.End)
                                        ) {
                                            Text(
                                                text = "Atualizar",
                                                color = Color.White
                                            )
                                        }
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TelaCadastro(
        modifier: Modifier = Modifier,
        viewModel: BookViewModel,
        mainActivity: MainActivity,
        navHostController: NavHostController
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
        var editora by remember {
            mutableStateOf("")
        }
        var ano by remember {
            mutableStateOf("")
        }
        var preco by remember {
            mutableStateOf("")
        }
        val book = Book(
            nome,
            autor,
            editora,
            ano,
            preco
        )

        var bookList by remember {
            mutableStateOf(listOf<Book>())
        }

        viewModel.getAllBooks().observe(mainActivity) {
            bookList = it
        }

        val context = LocalContext.current

        Column(
            Modifier
                .background(Color(38, 38, 38))
                .fillMaxHeight()
        ) {
            CenterAlignedTopAppBar(
                title = {Text(
                    text = "Adicionar Livro",
                    color = Color(254, 102, 0),
                    fontSize = 30.sp
                ) },
                navigationIcon = {
                    IconButton(onClick = {navHostController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(25, 25, 25),
                    scrolledContainerColor = Color(25, 25, 25),
                    navigationIconContentColor = Color(254, 102, 0),
                    titleContentColor = Color(254, 102, 0),
                    actionIconContentColor = Color(254, 102, 0)
                ),
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
                    value = editora,
                    onValueChange = { editora = it },
                    label = { Text("Editora") },
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
                    value = ano,
                    onValueChange = { ano = it },
                    label = { Text("Ano de publicação") },
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
                        keyboardType = KeyboardType.Number,
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
                    label = { Text("Preço") },
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
                        keyboardType = KeyboardType.Number,
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
                val botaoSalvar = Button(
                    onClick = {
                        viewModel.upsertBook(book)
                        id = ""
                        nome = ""
                        autor = ""
                        editora = ""
                        ano = ""
                        preco = ""
                        navHostController.popBackStack()
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
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TelaAtualizar(
        modifier: Modifier = Modifier,
        viewModel: BookViewModel,
        mainActivity: MainActivity,
        codigo: Int,
        inome: String,
        iautor: String,
        ieditora: String,
        iano: String,
        ipreco: String,
        navHostController: NavHostController
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
        var editora by remember {
            mutableStateOf("")
        }
        var ano by remember {
            mutableStateOf("")
        }
        var preco by remember {
            mutableStateOf("")
        }

        LaunchedEffect(codigo, inome, iautor, ieditora, iano) {
            id = codigo.toString()
            nome = inome
            autor = iautor
            editora = ieditora
            ano = iano
            preco = ipreco
        }

        val context = LocalContext.current

        Column(
            Modifier
                .background(Color(38, 38, 38))
                .fillMaxHeight()
        ) {
            CenterAlignedTopAppBar(
                title = {Text(
                    text = "Atualizar Livro",
                    color = Color(254, 102, 0),
                    fontSize = 30.sp
                ) },
                navigationIcon = {
                    IconButton(onClick = {navHostController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(25, 25, 25),
                    scrolledContainerColor = Color(25, 25, 25),
                    navigationIconContentColor = Color(254, 102, 0),
                    titleContentColor = Color(254, 102, 0),
                    actionIconContentColor = Color(254, 102, 0)
                ),
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
                    value = editora,
                    onValueChange = { editora = it },
                    label = { Text("Editora") },
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
                    value = ano,
                    onValueChange = { ano = it },
                    label = { Text("Ano de publicação") },
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
                        keyboardType = KeyboardType.Number,
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
                    label = { Text("Preço") },
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
                        keyboardType = KeyboardType.Number,
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
                val botaoSalvar = Button(
                    onClick = {
                        viewModel.updateBook(nome, autor, editora, ano, preco, id)
                        id = ""
                        nome = ""
                        autor = ""
                        editora = ""
                        ano = ""
                        preco = ""
                        navHostController.popBackStack()
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
        }
    }
}