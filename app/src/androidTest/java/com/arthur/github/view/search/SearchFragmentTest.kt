package com.arthur.github.view.search

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.arthur.github.R
import com.arthur.github.ViewUtils.APP_CONTEXT
import com.arthur.github.ViewUtils.SEARCH
import com.arthur.github.ViewUtils.TITLE
import com.arthur.github.view.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@FlowPreview
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()

    @Before
    fun setUp() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun cleanup() {
        scenario.close()
    }

    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return SEARCH
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals(APP_CONTEXT, appContext.packageName)
    }

    @Test
    fun resultsLoadedInRecyclerView() {
        onView(withId(R.id.search_repos)).perform(click())
        onView(withId(R.id.search_repos)).perform(typeSearchViewText(SEARCH))
        instrumentationRegistry.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER)
        onView(withId(R.id.repo_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun isItemFoundInList() {
        onView(withId(R.id.search_repos)).perform(click())
        onView(withId(R.id.search_repos)).perform(typeSearchViewText(SEARCH))
        instrumentationRegistry.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER)
        onView(withId(R.id.repo_recycler_view))
        hasDescendant(withText(TITLE)).matches(isDisplayed())
    }
}