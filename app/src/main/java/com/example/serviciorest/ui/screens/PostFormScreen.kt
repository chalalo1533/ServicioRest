package com.example.serviciorest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.serviciorest.viewmodel.PostFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostFormScreen(
    navController: NavController,
    viewModel: PostFormViewModel,
    onCancelar: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val esEdicion = estado.id != null

    // Cuando el servidor responde OK, pasamos a la pantalla de resultado
    LaunchedEffect(key1 = estado.respuesta) {
        if (estado.respuesta != null) {
            navController.navigate(route = "resultado") {
                // Sacamos el formulario de la pila: "atrás" cierra la actividad
                popUpTo(route = "formulario") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (esEdicion) "Editar Post #${estado.id}" else "Nuevo Post") },
                navigationIcon = {
                    TextButton(onClick = onCancelar) { Text(text = "← Volver") }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            if (estado.cargando) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            // Campo userId
            OutlinedTextField(
                value = estado.userId,
                onValueChange = viewModel::onUserIdChange,
                label = { Text(text = "Id de usuario") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = estado.errores.userId != null,
                supportingText = {
                    estado.errores.userId?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo título
            OutlinedTextField(
                value = estado.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text(text = "Título") },
                isError = estado.errores.title != null,
                supportingText = {
                    estado.errores.title?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo contenido
            OutlinedTextField(
                value = estado.body,
                onValueChange = viewModel::onBodyChange,
                label = { Text(text = "Contenido") },
                minLines = 4,
                isError = estado.errores.body != null,
                supportingText = {
                    estado.errores.body?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Error de la API (si lo hubo)
            estado.errorServidor?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            // Botón: enviar (valida y llama a POST o PUT)
            Button(
                onClick = { viewModel.guardar() },
                enabled = !estado.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (estado.cargando) {
                    CircularProgressIndicator(modifier = Modifier.size(size = 20.dp), strokeWidth = 2.dp)
                } else {
                    Text(text = if (esEdicion) "Actualizar (PUT)" else "Crear (POST)")
                }
            }
        }
    }
}
