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
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_choose_item.*

class CartFragment : Fragment(), OnItemClickListener {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CartFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observedTotalAmount()
        setRecyclerview()
        showTwoItems()
        setItemClickButton()
        ivBackToChooseItem.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToChooseItemFragment())
        }
    }

    private fun setItemClickButton() {
        tvViewMore.setOnClickListener {
            viewModel.getAllItem().observe(viewLifecycleOwner, Observer {
                productList.clear()
                productList.addAll(it)
                productAdapter.notifyDataSetChanged()
                tvViewMore.visibility = View.GONE
            })
        }
    }

    private fun showTwoItems() {
        viewModel.getAllItem().observe(viewLifecycleOwner, Observer {
            if (it.size <= 2) {
                productList.clear()
                productList.add(it[0])
                productList.add(it[1])
                productAdapter.notifyDataSetChanged()
                tvViewMore.visibility = View.GONE
            } else if (it.size > 2) {
                productList.clear()
                productList.add(it[0])
                productList.add(it[1])
                productAdapter.notifyDataSetChanged()
                tvViewMore.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerview() {
        productAdapter = ProductAdapter(productList, this)
        RvCartItemList.layoutManager = LinearLayoutManager(context)
        RvCartItemList.adapter = productAdapter
    }

    private fun observedTotalAmount() {
        var totalAmount = ""
        viewModel.getTotalCartAmountModel().observe(viewLifecycleOwner, Observer {
            totalAmount = it!!.toString()
            tvTotalCost.text = "€${totalAmount}"
        })
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