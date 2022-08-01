package corella.oscar.check24.data.remote

import corella.oscar.check24.data.model.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Response

interface ProductsService {

    companion object {
        const val BASE_URL = "https://app.check24.de/"
    }

    @Headers("Content-Type: application/json")
    @GET("products-test.json")
    suspend fun getProducts(): Response<ProductsResponse>

}