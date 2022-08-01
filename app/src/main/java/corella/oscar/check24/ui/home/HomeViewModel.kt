package corella.oscar.check24.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corella.oscar.check24.data.Result
import corella.oscar.check24.data.model.Product
import corella.oscar.check24.data.model.ProductsResponse
import corella.oscar.check24.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _productStateFlow = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading(true))
    val productStateFlow = _productStateFlow.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            productsRepository
                .getProducts()
                .collect { result ->
                    when(result) {
                        is Result.Success -> {
                            result.data?.let { products ->
                                _productStateFlow.value = ProductsUiState.Success(products)
                            }
                        }
                        is Result.Error -> Unit
                        is Result.Loading -> {
                            if(result.isLoading) {
                                _productStateFlow.value = ProductsUiState.Loading(true)
                            }
                        }
                    }
                }
        }
    }

}

sealed class ProductsUiState {
    data class Success(var products: ProductsResponse): ProductsUiState()
    data class Error(var exception: Throwable): ProductsUiState()
    data class Loading(var isLoading: Boolean): ProductsUiState()
}