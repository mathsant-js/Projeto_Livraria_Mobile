package com.example.projeto_livraria_mobile.books.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projeto_livraria_mobile.books.ui.home.HomeDestination
import com.example.projeto_livraria_mobile.books.ui.home.HomeScreen
import com.example.projeto_livraria_mobile.books.ui.book.BookDetailsDestination
import com.example.projeto_livraria_mobile.books.ui.book.BookDetailsScreen
import com.example.projeto_livraria_mobile.books.ui.book.BookEditDestination
import com.example.projeto_livraria_mobile.books.ui.book.BookEditScreen
import com.example.projeto_livraria_mobile.books.ui.book.BookEntryDestination
import com.example.projeto_livraria_mobile.books.ui.book.BookEntryScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun BooksNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToBookEntry = { navController.navigate(BookEntryDestination.route) },
                navigateToBookUpdate = {
                    navController.navigate("${BookDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = BookEntryDestination.route) {
            BookEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = BookDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(BookDetailsDestination.bookIdArg) {
                type = NavType.IntType
            })
        ) {
            BookDetailsScreen(
                navigateToEditBook = { navController.navigate("${BookEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = BookEditDestination.routeWithArgs,
            arguments = listOf(navArgument(BookEditDestination.bookIdArg) {
                type = NavType.IntType
            })
        ) {
            BookEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}