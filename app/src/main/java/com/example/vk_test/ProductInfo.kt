package com.example.vk_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.navArgs
import com.example.vk_test.databinding.FragmentProductInfoBinding
import com.example.vk_test.databinding.FragmentProductListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val args: ProductInfoArgs by navArgs()
    private lateinit var product:ProductClass
    private lateinit var binding: FragmentProductInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        product = args.product

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        binding.id.text = product.id.toString()
        binding.title.text = product.title
        binding.description.text = product.description
        binding.price.text = product.price.toString()
        binding.discountPercentage.text = product.discountPercentage.toString()
        binding.rating.text = product.rating.toString()
        binding.stock.text = product.stock.toString()
        binding.brand.text = product.brand
        binding.category.text = product.category

        val view = binding.root
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}