import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviciorest.data.model.Post
import com.example.serviciorest.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostViewModel,
    onPostClick: (Post) -> Unit = {},
    onCrearClick: () -> Unit = {}
) {
    // Observamos el flujo de datos del ViewModel
    val posts = viewModel.postList.collectAsState().value
    // Scaffold con TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Listado de Posts") }
            )
        },
        // Botón flotante para crear un post nuevo (POST)
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onCrearClick) {
                Text(text = "+ Nuevo post")
            }
        }
    ) { innerPadding ->
        // Aplicamos el padding de seguridad del sistema
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)) // ➡️ Esto garantiza el uso de edge-to-edge
        {
            // Lista de publicaciones
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp) // Espaciado interior del contenido
            ) {
                items(items = posts) { post ->
                    Card(
                        // Al tocar la tarjeta se abre el detalle
                        onClick = { onPostClick(post) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(all = 16.dp)) {
                            Text(
                                text = "Título: ${post.title}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(height = 4.dp))
                            Text(
                                text = post.body,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
