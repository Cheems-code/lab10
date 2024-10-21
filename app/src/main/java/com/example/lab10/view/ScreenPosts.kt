package com.example.lab10.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lab10.data.PostApiService
import com.example.lab10.data.PostModel

@Composable
fun PostsListScreen(navController: NavHostController, service: PostApiService) {
    var posts by remember { mutableStateOf(listOf<PostModel>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        posts = service.getPosts()
        loading = false
    }

    if (loading) {
        CircularProgressIndicator()
    } else {
        Column {
            Button(onClick = { navController.navigate("createPost") }) {
                // Asegúrate de colocar el Icon dentro del Button
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp)) // Espacio entre el ícono y el texto
                Text("Crear Publicación")
            }
            LazyColumn {
                items(posts) { post ->
                    PostItem(post = post, onEditClick = { navController.navigate("editPost/${post.id}") })
                }
            }
        }
    }
}

@Composable
fun PostItem(post: PostModel, onEditClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = post.title, fontWeight = FontWeight.Bold)
        Text(text = post.body)
        Button(onClick = onEditClick) {
            Text("Editar")
        }
    }
}
