package com.example.serviciorest
import PostScreen
import androidx.core.view.WindowCompat //

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviciorest.ui.theme.ServicioRestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,  false)

        enableEdgeToEdge()
        setContent {
            ServicioRestTheme {
                val postViewModel: com.example.serviciorest.viewmodel.PostViewModel = viewModel()
                PostScreen(
                    viewModel = postViewModel,
                    onPostClick = { post ->
                        // Abrimos la actividad de detalle enviando solo el id
                        val intent = Intent(this, PostDetailActivity::class.java)
                        intent.putExtra(PostDetailActivity.EXTRA_POST_ID, post.id)
                        startActivity(intent)
                    },
                    onCrearClick = {
                        // Abrimos el formulario vacío (modo crear → POST)
                        startActivity(Intent(this, PostFormActivity::class.java))
                    }
                )
            }
        }
    }
}
