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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.data.model.Meal
import com.joesemper.justrecipebook.presenter.MealPresenter
import com.joesemper.justrecipebook.ui.fragments.categories.inner.AriesInnerFragment
import com.joesemper.justrecipebook.ui.fragments.categories.inner.CategoriesInnerFragment
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientItemView
import com.joesemper.justrecipebook.ui.fragments.meal.adapter.IngredientsRVAdapter
import com.joesemper.justrecipebook.ui.fragments.meal.inner.IngredientsInnerFragment
import com.joesemper.justrecipebook.ui.fragments.meal.inner.IngredientsInnerView
import com.joesemper.justrecipebook.ui.fragments.meal.inner.InstructionInnerFragment
import com.joesemper.justrecipebook.ui.fragments.meal.inner.InstructionInnerView
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

    private var ingredientsInnerView: IngredientsInnerView? = null
    private var instructionInnerView: InstructionInnerView? = null

    private lateinit var viewPager: ViewPager2

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
            R.id.item_add_to_favorite -> presenter.onAddToFavoriteClicked()
            R.id.item_add_to_cart -> presenter.onAddToCartClicked()
            R.id.item_play_video -> presenter.onWatchVideoClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initActionBar(title: String, subtitle: String) {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar_recipe)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setTitle(title)
            }
        }
        toolbar_recipe.setNavigationOnClickListener {
            backPressed()
        }
    }

    override fun init() {
        executeInjection()
        initNavigation()
    }

    private fun initNavigation() {
        initViewPager()
        initTabs()
    }

    override fun initIngredients() {
        ingredientsInnerView?.init()
    }

    override fun initInstruction() {
        instructionInnerView?.init()
    }

    override fun updateIngredientsList() {
        ingredientsInnerView?.updateList()
    }

    override fun setInstruction(text: String) {
        instructionInnerView?.setInstruction(text)
    }

    override fun setImage(url: String) {
        imageLoader.loadInto(url, iv_meal_image2)
    }

    override fun setIsFavorite(isFavorite: Boolean) {
        toolbar_recipe.menu.findItem(R.id.item_add_to_favorite).icon = if (isFavorite) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun showContent() {
        view_pager_meals.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun playVideo(id: String) {
        watchYoutubeVideo(requireContext(), id)
    }

    private fun watchYoutubeVideo(context: Context, id: String) {
        val appIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("vnd.youtube:$id")
        )
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context.startActivity(appIntent);
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent);
        }
    }

    private fun executeInjection() {
        App.instance.appComponent.inject(this)
    }

    private fun initViewPager() {
        viewPager = requireActivity().findViewById(R.id.view_pager_meals)
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter
    }

    private fun initTabs() {
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tabLayout_meal)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Ingredients"
                1 -> tab.text = "Instruction"
            }
        }.attach()
    }

    override fun showResult(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed(): Boolean {
        presenter.backPressed()
        return true
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> IngredientsInnerFragment.newInstance(presenter).apply {
                    ingredientsInnerView = this
                }
                1 -> InstructionInnerFragment.newInstance(presenter).apply {
                    instructionInnerView = this
                }
                else -> IngredientsInnerFragment.newInstance(presenter)
            }
        }
    }
}