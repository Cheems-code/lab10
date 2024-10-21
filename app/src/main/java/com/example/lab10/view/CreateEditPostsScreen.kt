package com.example.lab10.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lab10.data.PostApiService
import com.example.lab10.data.PostModel
import kotlinx.coroutines.launch

@Composable
fun CreateEditPostScreen(navController: NavHostController, service: PostApiService, postId: Int? = null) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var body by remember { mutableStateOf(TextFieldValue()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(postId) {
        if (postId != null) {
            val post = service.getPost(postId).body()
            title = TextFieldValue(post?.title ?: "")
            body = TextFieldValue(post?.body ?: "")
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
        TextField(value = body, onValueChange = { body = it }, label = { Text("Cuerpo") })

        Button(onClick = {
            coroutineScope.launch {
                val post = PostModel(userId = 1, id = postId ?: 0, title = title.text, body = body.text)
                if (postId == null) {
                    service.createPost(post)
                } else {
                    service.updatePost(postId, post)
                }
                navController.navigate("posts")
            }
        }) {
            Text("Guardar")
        }
    }
}
