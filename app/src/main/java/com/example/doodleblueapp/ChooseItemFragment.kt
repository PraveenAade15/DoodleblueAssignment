package com.example.doodleblueapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ccom.example.doodleblueapp.itemclicklistener.OnItemClickListener
import com.example.doodleblueapp.data.CartEntity
import com.example.doodleblueapp.viewmodel.CartApplication
import com.example.doodleblueapp.viewmodel.CartViewModel
import com.example.doodleblueapp.viewmodel.CartViewModelFactory
import kotlinx.android.synthetic.main.fragment_choose_item.*


class ChooseItemFragment : Fragment(), OnItemClickListener {

    lateinit var viewModel: CartViewModel
    var productList = mutableListOf<CartEntity>()
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cartApplication = activity?.application as CartApplication
        val cartRepo = cartApplication.cartRepository
        val viewModelFactory = CartViewModelFactory(cartRepo)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CartViewModel::class.java)
    }

//    private fun setViewModel() {
//        val cartApplication = activity?.application as CartApplication
//        val cartRepo = cartApplication.cartRepository
//        val viewModelFactory = CartViewModelFactory(cartRepo)
//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//            .get(CartViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        var cart1 = CartEntity(
            "Guac de la Costa",
            "tortillas de mais, fruit de la passion, mango",
            7, 1
        )
        var cart2 =
            CartEntity(
                "Chicharron y Cerveza",
                "citron vert / Corona sauce, mango",
                7, 1
            )

        var cart3 =
            CartEntity(
                "Chicharron y Cerveza",
                "citron vert / Corona sauce, mango",
                7, 1
            )
        viewModel.getAllItem().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                viewModel.addProductModel(cart1)
                viewModel.addProductModel(cart2)
                viewModel.addProductModel(cart3)
            }
        })
        return inflater.inflate(R.layout.fragment_choose_item, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChooseItemFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observedTotalCartItems()
        showAllItems()
        btnViewCart.setOnClickListener {
            findNavController().navigate(ChooseItemFragmentDirections.actionChooseItemFragmentToCartFragment())
        }

    }

    private fun showAllItems() {
        viewModel.getAllItem().observe(viewLifecycleOwner, Observer {
            productList.clear()
            productList.addAll(it)
            productAdapter.notifyDataSetChanged()
        })
    }

    private fun observedTotalCartItems() {
        viewModel.getCartCountModel().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                CartItemCount.text = it.toString()
            }
        })
    }

    private fun setRecyclerView() {
        productAdapter = ProductAdapter(productList, this)
        RvItemList.layoutManager = LinearLayoutManager(context)
        RvItemList.adapter = productAdapter
    }

    override fun incrementCount(cartEntity: CartEntity, position: Int) {
        var itemQ = cartEntity.productQuantity + 1
        var price: Int = 7 * itemQ
        if (itemQ <= 20) {
            viewModel.updateProductQuantityModel(itemQ, price.toLong(), cartEntity.cId!!)
            productAdapter.notifyItemChanged(position)
        }
    }

    override fun decrementCount(cartEntity: CartEntity, position: Int) {
        var itemQ = cartEntity.productQuantity - 1
        var price = 7 * itemQ
        if (itemQ >= 0) {
            viewModel.updateProductQuantityModel(itemQ, price.toLong(), cartEntity.cId!!)
            productAdapter.notifyItemChanged(position)
        }
    }
}