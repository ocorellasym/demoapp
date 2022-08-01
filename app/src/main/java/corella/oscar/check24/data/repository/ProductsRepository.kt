package corella.oscar.check24.data.repository

import corella.oscar.check24.data.Result
import corella.oscar.check24.data.model.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getProducts(): Flow<Result<ProductsResponse>>

}