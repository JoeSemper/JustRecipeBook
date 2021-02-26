package com.joesemper.justrecipebook.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.joesemper.justrecipebook.App
import com.joesemper.justrecipebook.R
import com.joesemper.justrecipebook.presenter.CategoriesPresenter
import com.joesemper.justrecipebook.ui.fragments.categories.inner.AriesInnerFragment
import com.joesemper.justrecipebook.ui.fragments.categories.inner.AriesInnerView
import com.joesemper.justrecipebook.ui.fragments.categories.inner.CategoriesInnerFragment
import com.joesemper.justrecipebook.ui.fragments.categories.inner.CategoriesInnerView
import com.joesemper.justrecipebook.ui.interfaces.BackButtonListener
import kotlinx.android.synthetic.main.fragment_categories.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class CategoriesFragment : MvpAppCompatFragment(), CategoriesView, BackButtonListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    val presenter: CategoriesPresenter by moxyPresenter {
        CategoriesPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var categoriesView: CategoriesInnerView? = null
    private var ariesView: AriesInnerView? = null

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_categories, null)
    }

    override fun init() {
        initViewPager()
        initTabs()
    }

    private fun initViewPager() {
        viewPager = requireActivity().findViewById(R.id.view_pager_categories)
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter
    }

    private fun initTabs() {
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Categories"
                1 -> tab.text = "Aries"
            }
        }.attach()
    }

    override fun initCategories() {
        categoriesView?.init()
    }

    override fun initAries() {
        ariesView?.init()
    }

    override fun updateCategoriesList() {
        categoriesView?.updateList()
    }

    override fun updateAriesList() {
        ariesView?.updateList()
    }

    override fun showContent() {
        progressbar_categories.visibility = View.GONE
        content_categories.visibility =View.VISIBLE
    }

    override fun backPressed() = presenter.backPressed()

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CategoriesInnerFragment.newInstance(presenter).apply {
                    categoriesView = this
                }
                1 -> AriesInnerFragment.newInstance(presenter).apply {
                    ariesView = this
                }
                else -> CategoriesInnerFragment.newInstance(presenter)
            }
        }
    }

}

