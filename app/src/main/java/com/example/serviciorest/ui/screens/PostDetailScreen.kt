package com.example.serviciorest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviciorest.viewmodel.PostDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    // Observamos los flujos del ViewModel
    val post = viewModel.post.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value

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
                // 1) Cargando
                isLoading -> CircularProgressIndicator()

                // 2) Error en la llamada REST
                error != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(height = 12.dp))
                    Button(onClick = onRetry) { Text(text = "Reintentar") }
                }

                // 3) Datos cargados
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
                }
            }
        }
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
