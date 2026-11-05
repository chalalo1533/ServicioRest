package com.example.serviciorest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviciorest.ui.screens.PostDetailScreen
import com.example.serviciorest.ui.theme.ServicioRestTheme
import com.example.serviciorest.viewmodel.PostDetailViewModel

class PostDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de edge-to-edge con barras de sistema oscuras estilo Cyberpunk
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )

        val id = intent.getIntExtra(EXTRA_POST_ID, -1)

        setContent {
            ServicioRestTheme {
                val detailViewModel: PostDetailViewModel = viewModel()

                LaunchedEffect(key1 = id) {
                    detailViewModel.fetchProducto(id)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0A0B10) // CyberpunkDarkBg
                ) {
                    PostDetailScreen(
                        viewModel = detailViewModel,
                        onBack = { finish() },
                        onRetry = { detailViewModel.fetchProducto(id) }
                    )
                }
            }
        }
    }
}