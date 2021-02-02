package com.joesemper.justrecipebook.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.data.AppDataManager
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.data.network.model.Meals
import com.joesemper.justrecipebook.ui.adapters.meals.MealsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.utilite.image.IImageLoader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class HomeFragment : MvpAppCompatFragment(), HomeView, BackButtonListener {


    companion object {
        fun newInstance() = HomeFragment()
    }

    val presenter: HomePresenter by moxyPresenter {
        HomePresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var adapter: MealsRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        View.inflate(context, R.layout.fragment_home, null)


    override fun init() {
        initRV()

    }

    private fun initRV() {
        rv_meals.layoutManager = GridLayoutManager(context, 2)
        adapter = MealsRVAdapter(presenter.mealsListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        rv_meals.adapter = adapter
    }


    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }



    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed(): Boolean = presenter.backPressed()




}