package com.example.serviciorest.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviciorest.data.model.Producto
import com.example.serviciorest.viewmodel.PostViewModel

// Paleta de Colores Cyberpunk 2077
private val CyberpunkYellow = Color(0xFFFCEE09)
private val CyberpunkCyan = Color(0xFF00F0FF)
private val CyberpunkPink = Color(0xFFFF0055)
private val CyberpunkDarkBg = Color(0xFF0A0B10)
private val CyberpunkCardBg = Color(0xFF14161F)
private val CyberpunkTextDim = Color(0xFF8A90A6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostViewModel,
    onPostClick: (Producto) -> Unit = {}
) {
    val productos = viewModel.prdList.collectAsState().value

    // Observamos el estado de carga/recarga desde el ViewModel
    val isRefreshing by viewModel.isRefreshing.collectAsState(initial = false)
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        containerColor = CyberpunkDarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberpunkDarkBg,
                    titleContentColor = CyberpunkYellow
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp, 20.dp)
                                .background(CyberpunkYellow)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "// NET_DB :: PRODUCTOS",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // PullToRefreshBox envuelve la lista
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() }, // Método para recargar datos en el ViewModel
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .background(CyberpunkDarkBg),
            indicator = {
                // Indicador visual personalizado con el estilo neón del juego
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter),
                    containerColor = CyberpunkCardBg,
                    color = CyberpunkYellow
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(items = productos) { prd ->
                    CyberpunkProductCard(
                        producto = prd,
                        onClick = { onPostClick(prd) }
                    )
                }
            }
        }
    }
}

@Composable
fun CyberpunkProductCard(
    producto: Producto,
    onClick: () -> Unit
) {
    val cyberpunkShape = CutCornerShape(
        topStart = 0.dp,
        topEnd = 14.dp,
        bottomStart = 14.dp,
        bottomEnd = 0.dp
    )

    Surface(
        onClick = onClick,
        shape = cyberpunkShape,
        color = CyberpunkCardBg,
        border = BorderStroke(1.5.dp, CyberpunkCyan),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "[ ITEM_DATA_FILE ]",
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

            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                text = producto.nombre.uppercase(),
                color = CyberpunkYellow,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 1.dp)
                    .background(color = CyberpunkCyan.copy(alpha = 0.3f))
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "PRECIO: €$ ${producto.precio}",
                    color = CyberpunkCyan,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "STOCK: ${producto.stock} UNID",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}