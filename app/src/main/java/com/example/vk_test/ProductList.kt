package com.example.vk_test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vk_test.databinding.ActivityMainBinding
import com.example.vk_test.databinding.FragmentProductListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductList.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductList : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var service:RetrofitController
    lateinit var productList: List<ProductClass>
    lateinit var productAdapter: ProductAdapter
    private lateinit var binding: FragmentProductListBinding
    private var page by Delegates.notNull<Int>()
    private val args: ProductListArgs by navArgs()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitController::class.java)
        page = args.page

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        val view = binding.root
        productList= arrayListOf()

        productAdapter = ProductAdapter(productList)

        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        productRecyclerView.setItemViewCacheSize( 20 )
        val call: Call<ProductClassList> =  service.getProduct((page*20).toString(), "20")
        call.enqueue(object : Callback<ProductClassList> {
            override fun onResponse(call: Call<ProductClassList>, response: Response<ProductClassList>) {
                if (response.isSuccessful) {
                    val products: ProductClassList? = response.body()
                    productList = products?.products!!
                    productAdapter = ProductAdapter(productList)
                    productRecyclerView.layoutManager = LinearLayoutManager(context)
                    productRecyclerView.adapter = productAdapter
                    productAdapter.setOnClickListener(object :
                        ProductAdapter.OnClickListener {
                        override fun onClick(position: Int, model: ProductClass) {
                            val action = ProductListDirections.actionProductListToProductInfo(model)
                            navController.navigate(action)
                        }
                    })

                }
                else{
                    productAdapter = ProductAdapter(productList)
                }
            }
            override fun onFailure(call: Call<ProductClassList>, t: Throwable) {
                Toast.makeText(context, "Error Api", Toast.LENGTH_SHORT).show()
                Log.d("RetrofitClient", t.toString())
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.back -> {
                    if (page != 0)
                    {
                        val action = ProductListDirections.actionProductListSelf2(page-1)
                        navController.navigate(action)
                        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("Page")?.observe(viewLifecycleOwner) { result ->
                        }
                        navController.navigate(action)
                    }
                    true
                }
                R.id.find -> {
                    true
                }
                R.id.next -> {
                    val action = ProductListDirections.actionProductListSelf2(page+1)
                    navController.navigate(action)
                    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("Page")?.observe(viewLifecycleOwner) { result ->
                    }
                    navController.navigate(action)
                    true
                }
                else -> {
                    true
                }
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}