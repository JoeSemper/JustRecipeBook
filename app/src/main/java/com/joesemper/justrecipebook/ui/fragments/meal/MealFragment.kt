package com.joesemper.justrecipebook.ui.fragments.meal

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.data.network.model.Meal
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.utilite.image.IImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_meal.*
import kotlinx.android.synthetic.main.fragment_meal.toolbar_recipe
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
    ) :View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_meal, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_recipe, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
                R.id.menu_add_to_favorite -> presenter.onAddToFavoriteClicked()

                R.id.menu_add_to_menu -> {
                    Toast.makeText(context, "Add to menu", Toast.LENGTH_SHORT).show()
                    true
                } else -> super.onOptionsItemSelected(item)
            }
    }

    override fun setTitle(title: String) {
        tv_meal_header.text = title
    }

    override fun setImage(url: String) {
        imageLoader.loadInto(url, iv_meal_image)
    }

    override fun setRegion(region: String) {
        tv_region.text = region
    }

    override fun setInstructions(instruction: String) {
        tv_meal_instruction.text = instruction
    }

    override fun setIsFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            toolbar_recipe.menu[1].icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_bookmark_24)
        } else {
            toolbar_recipe.menu[1].icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_bookmark_border_24)
        }
    }

    override fun init() {
        executeInjection()
        initRV()
        initActionBar()
    }

    private fun executeInjection() {
        App.instance.appComponent.inject(this)
    }

    private fun initRV(){
        rv_ingredients.layoutManager = LinearLayoutManager(context)
        rv_ingredients.setHasFixedSize(true)
        adapter = IngredientsRVAdapter(presenter.inngredientsListPresenter)
        rv_ingredients.adapter = adapter
    }

    private fun initActionBar() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar_recipe)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)

            }
        }
        toolbar_recipe.setNavigationOnClickListener{
            presenter.backPressed()
        }
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }
}