package com.example.serviciorest.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.model.Producto
import com.example.serviciorest.repository.PostRepository
import com.example.serviciorest.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = ProductoRepository()

    private val _prdList = MutableStateFlow<List<Producto>>(value = emptyList())
    // Flujo público de solo lectura
    val prdList: StateFlow<List<Producto>> = _prdList

    // Se llama automáticamente al iniciar
    init {
        fetchProductis()
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Tu lógica de petición HTTP / fetch de productos aquí...
            _isRefreshing.value = false
        }
    }


    private fun fetchProductis() {
        viewModelScope.launch {
            try {
                _prdList.value = repository.getProductos()
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }

    // Función que obtiene los datos en segundo plano

}