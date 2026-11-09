package com.example.serviciorest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.serviciorest.navigation.PostFormNavigation
import com.example.serviciorest.ui.theme.ServicioRestTheme

class PostFormActivity : ComponentActivity() {

    companion object {
        // Si viene este extra, el formulario está en modo edición (PUT)
        const val EXTRA_POST_ID = "extra_post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // -1 significa "sin id" → crear (POST)
        val postId = intent.getIntExtra(EXTRA_POST_ID, -1).takeIf { it > 0 }

        setContent {
            ServicioRestTheme {
                PostFormNavigation(
                    postId = postId,
                    onFinish = { finish() }
                )
            }
        }
    }
}
