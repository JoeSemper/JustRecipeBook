package com.joesemper.justrecipebook.ui.fragments.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.adapters.ingredients.IngredientsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.utilite.image.IImageLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_meal.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MealFragment: MvpAppCompatFragment(), MealView, BackButtonListener {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    companion object {
        private const val MEAL_ARG = "MEAL"

        fun newInstance(meal: Meal) = MealFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MEAL_ARG, meal)
            }
        }
    }

    val presenter: MealPresenter by moxyPresenter {
        val meal = arguments?.getParcelable<Meal>(MEAL_ARG) as Meal

        MealPresenter(meal).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var adapter: IngredientsRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        View.inflate(context, R.layout.fragment_meal, null)

    override fun setTitle(title: String) {
        tv_meal_header.text = title
    }

    override fun setImage(url: String) {
        imageLoader.loadInto(url, iv_meal_image)
    }

    override fun setIngredients(ingredients: List<String>) {

    }

    override fun setInstructions(instruction: String) {
        tv_meal_instruction.text = instruction
    }

    override fun init() {
        App.instance.appComponent.inject(this)
        initRV()
    }

    private fun initRV(){
        rv_ingredients.layoutManager = LinearLayoutManager(context)
        rv_ingredients.setHasFixedSize(true)
        adapter = IngredientsRVAdapter(presenter.inngredientsListPresenter)
        rv_ingredients.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }
}