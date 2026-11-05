package com.example.serviciorest.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviciorest.viewmodel.PostDetailViewModel

// Paleta de Colores Cyberpunk 2077
private val CyberpunkYellow = Color(0xFFFCEE09)
private val CyberpunkCyan = Color(0xFF00F0FF)
private val CyberpunkPink = Color(0xFFFF0055)
private val CyberpunkDarkBg = Color(0xFF0A0B10)
private val CyberpunkCardBg = Color(0xFF14161F)
private val CyberpunkTextDim = Color(0xFF8A90A6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    // Observamos los flujos del ViewModel del código original
    val prd = viewModel.prd.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value

    Scaffold(
        containerColor = CyberpunkDarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberpunkDarkBg,
                    titleContentColor = CyberpunkYellow,
                    navigationIconContentColor = CyberpunkYellow
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(
                            text = "< VOLVER",
                            color = CyberpunkYellow,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp, 20.dp)
                                .background(CyberpunkYellow)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "// NET_DB :: ITEM_INSPECT",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .background(CyberpunkDarkBg),
            contentAlignment = Alignment.Center
        ) {
            when {
                // 1) Cargando estilo Cyberpunk
                isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = CyberpunkYellow)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "SCANNING_MEMORY_BLOCK...",
                            color = CyberpunkCyan,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                    }
                }

                // 2) Error en la llamada REST
                error != null -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "ERR_CONNECTION_REFUSED",
                            color = CyberpunkPink,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            color = CyberpunkTextDim,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = onRetry,
                            shape = CutCornerShape(8.dp),
                            border = BorderStroke(1.dp, CyberpunkYellow),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = CyberpunkYellow
                            )
                        ) {
                            Text(
                                text = "[ RETRY_LINK ]",
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                // 3) Datos cargados
                prd != null -> {
                    val cyberpunkShape = CutCornerShape(
                        topStart = 0.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 0.dp
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(all = 16.dp)
                    ) {
                        Surface(
                            shape = cyberpunkShape,
                            color = CyberpunkCardBg,
                            border = BorderStroke(1.5.dp, CyberpunkYellow),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(all = 20.dp)) {
                                // Encabezado de la tarjeta con ID
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "ID_HEX // 0x${prd.id.toString(16).uppercase()}",
                                        color = CyberpunkTextDim,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        text = "STATUS // ONLINE",
                                        color = CyberpunkPink,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Nombre del producto
                                Text(
                                    text = "NOMBRE_PRODUCTO",
                                    color = CyberpunkTextDim,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = prd.nombre.uppercase(),
                                    color = CyberpunkYellow,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Línea de separación estilo escáner
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(CyberpunkCyan.copy(alpha = 0.5f))
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Filas de detalle
                                DetailRow(
                                    label = "PRECIO",
                                    value = "€$ ${prd.precio}",
                                    valueColor = CyberpunkCyan
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                DetailRow(
                                    label = "STOCK DISPONIBLE",
                                    value = "${prd.stock} UNIDADES",
                                    valueColor = Color.White
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                // Pie decorativo HUD
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(CyberpunkYellow.copy(alpha = 0.1f))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = ">>> DATA_STREAM_SECURE <<<",
                                        color = CyberpunkYellow,
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace,
                                        letterSpacing = 2.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Color.Unspecified
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = CyberpunkTextDim,
            style = MaterialTheme.typography.labelLarge,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}