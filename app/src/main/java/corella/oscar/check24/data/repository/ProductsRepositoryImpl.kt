package corella.oscar.check24.data.repository

import corella.oscar.check24.data.Result
import corella.oscar.check24.data.model.ProductsResponse
import corella.oscar.check24.data.remote.ProductsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductsService,
): ProductsRepository {

    override suspend fun getProducts(): Flow<Result<ProductsResponse>> {
        return flow {
            emit(Result.Loading(true))
            val productsResponse = try {
                val response = api.getProducts()
                Result.Success(response)
            } catch(e: Exception) {
                e.printStackTrace()
                emit(Result.Error("Error"))
                null
            }
            productsResponse?.data?.body()?.let { response ->
                emit(
                    Result.Success(
                        response
                    )
                )
                emit(Result.Loading(false))
            }

        }
    }

}