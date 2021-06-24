package com.joesemper.justrecipebook

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import com.joesemper.justrecipebook.ui.fragments.home.HomeFragment
import com.joesemper.justrecipebook.ui.fragments.home.adapter.MealsRVAdapter
import com.joesemper.justrecipebook.ui.util.constants.SearchType
import kotlinx.android.synthetic.main.fragment_main.*
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
    }

    @Test
    fun test_fragment_notNull() {
        scenario.onFragment {
            assertNotNull(it)
        }
    }

    @Test
    fun test_fragment_presenter_notNull() {
        scenario.onFragment {
            assertNotNull(it.presenter)
        }
    }

    @Test
    fun test_bundle_goes_correct() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.QUERY, "MEAL" to "")
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        scenario.onFragment {
            assertEquals("", it.presenter.query)
            assertEquals(SearchType.QUERY, it.presenter.searchType)
        }
    }

    @Test
    fun test_searchView_isDisplayed() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.QUERY)
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        onView(withId(R.id.text_input_layout_search)).check(matches(isDisplayed()))
    }

    @Test
    fun test_onFavorites_searchView_isGone() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.FAVORITE)
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        scenario.onFragment {
            assertEquals(View.GONE, it.text_input_layout_search.visibility)
        }
    }

    @Test
    fun test_recycler_view_isDisplayed() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.QUERY, "MEAL" to "")
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        onView(withId(R.id.rv_meals)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recycler_view_empty_search() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.QUERY, "MEAL" to "")
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_meals)).perform(
            RecyclerViewActions.scrollTo<MealsRVAdapter.ViewHolder>(
                hasDescendant(withText("Sugar Pie"))
            )
        )
    }

    @Test
    fun test_recycler_view_category() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.CATEGORY, "MEAL" to "Pasta")
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_meals)).perform(
            RecyclerViewActions.scrollTo<MealsRVAdapter.ViewHolder>(
                hasDescendant(withText("Lasagne"))
            )
        )
    }

    @Test
    fun test_recycler_view_favorites() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.FAVORITE)
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_meals)).perform(
            RecyclerViewActions.scrollTo<MealsRVAdapter.ViewHolder>(
                hasDescendant(withText("Sugar Pie"))
            )
        )
    }

    @Test
    fun test_recycler_view_search() {
        val fragmentArgs = bundleOf("SEARCH" to SearchType.QUERY)
        val scenario =
            launchFragmentInContainer<HomeFragment>(fragmentArgs, themeResId = R.style.AppTheme)

        onView(withId(R.id.text_input_search)).perform(click())
        onView(withId(R.id.text_input_search)).perform(
            replaceText("Chicken"),
            ViewActions.closeSoftKeyboard()
        )

//        val viewAction: ViewAction = clickOnEndIconInTextInputLayout(R.id.text_input_layout_search)
//        onView(withId(R.id.text_input_layout_search)).perform(viewAction)

        onView(withId(R.id.text_input_end_icon)).perform(click())

        onView(isRoot()).perform(delay())
        onView(withId(R.id.rv_meals)).perform(
            RecyclerViewActions.scrollTo<MealsRVAdapter.ViewHolder>(
                hasDescendant(withText("Chicken Handi"))
            )
        )
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $7 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(7000)
            }
        }
    }



    /**
     * Не получилось сделать кастомный ViewAction,
     * почему-то метод падает с NullPointerException.
     * Должен кликать на EndIcon в TextInputLayout
     */
    private fun clickOnEndIconInTextInputLayout(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Clicks on search icon"
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<TextInputLayout>(id)
                (v.findViewById<View>(R.id.text_input_end_icon) as CheckableImageButton).performClick()
//                v.findViewById<CheckableImageButton>(R.id.text_input_end_icon)?.performClick()
            }
        }
    }
}