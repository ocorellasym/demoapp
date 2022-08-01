package corella.oscar.check24.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ProductsResponse (
    val header: Header,
    val filters: List<String>,
    val products: List<Product>
)

data class Header (
    val headerTitle: String,
    val headerDescription: String
)

@Parcelize
data class Product (
    val id: Int,
    val name: String,
    val type: String,
    val imageURL: String,
    val available: Boolean,
    val releaseDate: Long,
    val description: String,
    val longDescription: String,
    val rating: Float,
    val price: Price,
    var isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class Price (
    val value: Float,
    val currency: String
) : Parcelable