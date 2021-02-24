package com.joesemper.justrecipebook.ui.fragments.meal

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.data.model.Meal
import com.joesemper.justrecipebook.presenter.MealPresenter
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientsRVAdapter
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import com.joesemper.justrecipebook.ui.utilite.image.IImageLoader
import kotlinx.android.synthetic.main.fragment_meal.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


class MealFragment : MvpAppCompatFragment(), MealView, BackButtonListener {

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

    private var ingredientsAdapter: IngredientsRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return View.inflate(context, R.layout.fragment_meal, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_recipe, menu)
        presenter.onOptionsMenuCreated()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_to_favorite -> presenter.onAddToFavoriteClicked()
            R.id.menu_add_to_menu -> presenter.addToMenuClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun init() {
        executeInjection()
        initRV()
    }

    override fun setImage(url: String) {
        imageLoader.loadInto(url, iv_meal_image2)
    }

    override fun setInstructions(instruction: String) {
        tv_meal_instruction.text = instruction
    }

    override fun setIsFavorite(isFavorite: Boolean) {
        toolbar_recipe.menu.findItem(R.id.menu_add_to_favorite).icon = if (isFavorite) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_bookmark_24)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_bookmark_border_24)
        }
    }

    override fun initActionBar(title: String, subtitle:String) {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar_recipe)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setTitle(title)
                setSubtitle(subtitle)
            }
        }
        toolbar_recipe.setNavigationOnClickListener {
            backPressed()
        }
    }

    override fun showContent() {
        meal_content.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun playVideo(id: String) {
        watchYoutubeVideo(requireContext(), id)
    }

    override fun setOnPlayVideoClickListener(isExists: Boolean) {
        if (isExists) {
            fab_play_video.setOnClickListener {
                presenter.onWatchVideoClicked()
            }
        } else {
            fab_play_video.isEnabled = false
        }
    }

    private fun watchYoutubeVideo(context: Context, id: String) {
        val appIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("vnd.youtube:" + id)
        );
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=" + id)
        );
        try {
            context.startActivity(appIntent);
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent);
        }

    }

    private fun executeInjection() {
        App.instance.appComponent.inject(this)
    }

    private fun initRV() {
        ingredientsAdapter = IngredientsRVAdapter(presenter.ingredientsListPresenter)
        with(rv_ingredients) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = ingredientsAdapter
        }
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun updateList() {
        ingredientsAdapter?.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }
}