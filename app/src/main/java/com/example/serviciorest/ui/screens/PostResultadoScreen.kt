package com.example.serviciorest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviciorest.viewmodel.PostFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostResultadoScreen(
    viewModel: PostFormViewModel,
    onVolver: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val respuesta = estado.respuesta
    val operacion = if (estado.id == null) "POST /posts" else "PUT /posts/${estado.id}"

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Respuesta del servidor") }) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            Text(text = "Operación: $operacion", style = MaterialTheme.typography.titleMedium)

            if (respuesta != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(Modifier.padding(all = 16.dp)) {
                        Text(text = "Id: ${respuesta.id}")
                        Text(text = "Id de usuario: ${respuesta.userId}")
                        Text(text = "Título: ${respuesta.title}")
                        Text(text = "Contenido: ${respuesta.body}")
                    }
                }
            }

            // JSONPlaceholder es una API de prueba: responde OK pero no guarda nada
            Text(
                text = "Nota: JSONPlaceholder simula la operación. Responde como si se hubiera guardado, " +
                        "pero no persiste los cambios, por eso el listado no cambia.",
                style = MaterialTheme.typography.bodySmall
            )

            Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Volver")
            }
        }
    }
}
