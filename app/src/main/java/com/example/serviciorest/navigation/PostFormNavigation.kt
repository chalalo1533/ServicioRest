package com.example.serviciorest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.serviciorest.ui.screens.PostFormScreen
import com.example.serviciorest.ui.screens.PostResultadoScreen
import com.example.serviciorest.viewmodel.PostFormViewModel

@Composable
fun PostFormNavigation(
    postId: Int?,          // null = crear, con valor = editar
    onFinish: () -> Unit   // cierra la actividad
) {
    val navController = rememberNavController()

    // Un solo ViewModel compartido por formulario y resultado
    val postFormViewModel: PostFormViewModel = viewModel()

    // En modo edición traemos los datos actuales del post
    LaunchedEffect(key1 = postId) {
        if (postId != null) postFormViewModel.cargarPost(postId)
    }

    NavHost(
        navController = navController,
        startDestination = "formulario"
    ) {
        composable(route = "formulario") {
            PostFormScreen(navController, postFormViewModel, onCancelar = onFinish)
        }
        composable(route = "resultado") {
            PostResultadoScreen(postFormViewModel, onVolver = onFinish)
        }
    }
}
