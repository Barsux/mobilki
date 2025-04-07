package com.barsux.barsux_experimental.presentation.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.barsux.barsux_experimental.databinding.FragmentNoconnectionBinding

class NoConnectionFragment : Fragment() {

    private var _binding: FragmentNoconnectionBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PREVIOUS_FRAGMENT_TAG = "previous_fragment_tag"

        fun newInstance(previousFragmentTag: String): NoConnectionFragment {
            val fragment = NoConnectionFragment()
            val args = Bundle()
            args.putString(ARG_PREVIOUS_FRAGMENT_TAG, previousFragmentTag)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoconnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.retryButton.setOnClickListener {
            if (isNetworkAvailable(requireContext())) {
                val previousTag = arguments?.getString(ARG_PREVIOUS_FRAGMENT_TAG)
                if (previousTag != null) {
                    parentFragmentManager.popBackStack(previousTag, 0)
                } else {
                    parentFragmentManager.popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
