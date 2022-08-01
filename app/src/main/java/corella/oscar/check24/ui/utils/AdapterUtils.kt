package corella.oscar.check24.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import corella.oscar.check24.data.model.Product
import corella.oscar.check24.ui.home.ProductsAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:products")
fun setProducts(listView: RecyclerView, items: List<Product>?) {
    items?.let { (listView.adapter as ProductsAdapter).setProducts(it) }
}

@BindingAdapter("app:dateFromEpoch")
fun TextView.setDateFromEpoch(date: Long) {
    val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
    this.text = sdf.format(Date(date* 1000L))
}

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    var scrollUpChild: View? = null

    override fun canChildScrollUp() =
        scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()
}