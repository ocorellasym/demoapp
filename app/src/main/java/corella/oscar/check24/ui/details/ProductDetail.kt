package corella.oscar.check24.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import corella.oscar.check24.R
import corella.oscar.check24.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetail : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailArgs by navArgs()
    private var isFav = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            product = args.product
            footer.setOnClickListener {
                val customTabsIntent :CustomTabsIntent  = CustomTabsIntent.Builder().build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse("https://m.check24.de/rechtliche-hinweise/?deviceoutput=app"))
            }
            Picasso.get().load(product?.imageURL).into(image)
            vormerken.setOnClickListener {
                isFav = !isFav
                if(isFav) {
                    vormerken.text = getString(R.string.vergessen)
                    name.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_700));

                }
                else {
                    vormerken.text = getString(R.string.vormerken)
                    name.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}