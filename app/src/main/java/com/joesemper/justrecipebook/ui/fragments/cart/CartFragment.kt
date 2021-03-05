package com.joesemper.justrecipebook.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.CartPresenter
import com.joesemper.justrecipebook.ui.fragments.cart.adapter.CartRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import kotlinx.android.synthetic.main.fragment_cart.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class CartFragment: MvpAppCompatFragment(), CartView, BackButtonListener {

    companion object {
        fun newInstance() = CartFragment()
    }

    val presenter: CartPresenter by moxyPresenter {
        CartPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var cartAdapter: CartRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_cart, null)

    override fun init() {
        initRV()
    }

    private fun initRV() {
        cartAdapter = CartRVAdapter(presenter.cartListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        with(rv_cart_ingredients) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = cartAdapter
        }
    }

    override fun updateList() {
        cartAdapter?.notifyDataSetChanged()
    }

    override fun showContent() {
    }

    override fun backPressed() = presenter.backPressed()

}