package com.example.lab10.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab10.data.PostApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PostsApp() {
    val urlBase = "https://jsonplaceholder.typicode.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PostApiService::class.java)
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.padding(top = 40.dp),
        content = { paddingValues ->
            NavHost(navController = navController, startDestination = "inicio", Modifier.padding(paddingValues)) {
                composable("inicio") { InicioScreen(navController) }
                composable("posts") { PostsListScreen(navController, service) }
                composable("createPost") { CreateEditPostScreen(navController, service) }
                composable("editPost/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                    CreateEditPostScreen(navController, service, id)
                }
            }
        }
    )
}
