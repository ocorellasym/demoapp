package corella.oscar.check24.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import corella.oscar.check24.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewmodel = homeViewModel
            footer.setOnClickListener {
                val customTabsIntent : CustomTabsIntent = CustomTabsIntent.Builder().build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse("https://m.check24.de/rechtliche-hinweise/?deviceoutput=app"))
            }
        }
        val root: View = binding.root

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.productStateFlow.collect { uiState ->
                    when (uiState) {
                        is ProductsUiState.Success -> {
                            binding.productList.adapter = ProductsAdapter(mutableListOf(), OnClickListener { product ->
                                findNavController().navigate(HomeFragmentDirections.showProductDetail(product))
                            })
                            binding.product = uiState.products
                            binding.isLoading = false
                        }
                        is ProductsUiState.Error -> {} //TODO
                        is ProductsUiState.Loading -> binding.isLoading = uiState.isLoading
                    }
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}