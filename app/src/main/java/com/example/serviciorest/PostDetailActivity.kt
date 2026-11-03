package com.example.serviciorest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviciorest.ui.screens.PostDetailScreen
import com.example.serviciorest.ui.theme.ServicioRestTheme
import com.example.serviciorest.viewmodel.PostDetailViewModel

class PostDetailActivity : ComponentActivity() {

    companion object {
        // Clave para pasar el id del post en el Intent
        const val EXTRA_POST_ID = "extra_post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Leemos el id que envió MainActivity
        val postId = intent.getIntExtra(EXTRA_POST_ID, -1)

        setContent {
            ServicioRestTheme {
                val detailViewModel: PostDetailViewModel = viewModel()

                // Lanza la llamada REST de detalle al abrir la pantalla
                LaunchedEffect(key1 = postId) {
                    detailViewModel.fetchPost(postId)
                }

                PostDetailScreen(
                    viewModel = detailViewModel,
                    onBack = { finish() },
                    onRetry = { detailViewModel.fetchPost(postId) }
                )
            }
        }
    }
}
