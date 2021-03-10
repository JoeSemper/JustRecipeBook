package com.joesemper.justrecipebook.ui.fragments.cart

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.CartPresenter
import com.joesemper.justrecipebook.ui.fragments.cart.adapter.CartRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.util.view.callback.SwipeCallback
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
        val itemTouchHelper = ItemTouchHelper(getSwipeHandler())

        cartAdapter = CartRVAdapter(presenter.cartListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        with(rv_cart_ingredients) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = cartAdapter
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun getSwipeHandler() = object : SwipeCallback(requireContext()) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            presenter.onItemSwiped(position)
        }
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun updateList() {
        cartAdapter?.notifyDataSetChanged()
    }

    override fun updateItem(pos: Int) {
        cartAdapter?.notifyItemRemoved(pos)
    }

    override fun showContent() {
    }

    override fun backPressed() = presenter.backPressed()

    override fun vibrate() {
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val canVibrate: Boolean = vibrator.hasVibrator()
        val milliseconds = 100L

        if (canVibrate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // API 26
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        milliseconds,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                // This method was deprecated in API level 26
                vibrator.vibrate(milliseconds)
            }
        }
    }

}