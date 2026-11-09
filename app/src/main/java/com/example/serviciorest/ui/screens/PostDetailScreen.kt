package com.example.serviciorest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviciorest.viewmodel.PostDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onEdit: (Int) -> Unit
) {
    // Observamos los flujos del ViewModel
    val post = viewModel.post.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value
    val eliminado = viewModel.eliminado.collectAsState().value

    // Estado solo de UI: mostrar o no el diálogo de confirmación
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalle del Post") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text(text = "← Volver") }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                // 1) Cargando (GET o DELETE)
                isLoading -> CircularProgressIndicator()

                // 2) Error en la llamada REST
                error != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(height = 12.dp))
                    Button(onClick = onRetry) { Text(text = "Reintentar") }
                }

                // 3) Post eliminado
                eliminado != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = eliminado, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(height = 12.dp))
                    Button(onClick = onBack) { Text(text = "Volver al listado") }
                }

                // 4) Datos cargados
                post != null -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(all = 16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(all = 16.dp)) {
                            DetailRow(label = "ID del post", value = post.id.toString())
                            DetailRow(label = "ID de usuario", value = post.userId.toString())
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            Text(text = "Título", style = MaterialTheme.typography.labelLarge)
                            Text(text = post.title, style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.height(height = 12.dp))
                            Text(text = "Contenido", style = MaterialTheme.typography.labelLarge)
                            Text(text = post.body, style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                    Spacer(modifier = Modifier.height(height = 16.dp))

                    // Acciones: editar (PUT) y eliminar (DELETE)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onEdit(post.id) },
                            modifier = Modifier.weight(1f)
                        ) { Text(text = "Editar") }

                        Button(
                            onClick = { mostrarConfirmacion = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.weight(1f)
                        ) { Text(text = "Eliminar") }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación antes del DELETE
    if (mostrarConfirmacion && post != null) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text(text = "Eliminar post") },
            text = { Text(text = "¿Seguro que quieres eliminar el post #${post.id}?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarConfirmacion = false
                    viewModel.eliminarPost()
                }) { Text(text = "Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) { Text(text = "Cancelar") }
            }
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
