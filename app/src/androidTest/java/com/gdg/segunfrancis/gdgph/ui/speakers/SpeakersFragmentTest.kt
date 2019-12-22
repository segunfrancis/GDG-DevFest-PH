package com.gdg.segunfrancis.gdgph.ui.speakers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.gdg.segunfrancis.gdgph.DetailsActivity
import com.gdg.segunfrancis.gdgph.R
import com.gdg.segunfrancis.gdgph.adapter.SpeakersAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by SegunFrancis
 */

@RunWith(JUnit4::class)
class SpeakersFragmentTest {

    @Rule
    @JvmField
    val mainActivity = ActivityTestRule(DetailsActivity::class.java)

    @Test
    fun displaySpeakerListAndSpeakerDetails() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_speakers))

        val speakerPosition = 1
        onView(withId(R.id.speakers_recycler_view)).perform(RecyclerViewActions
            .actionOnItemAtPosition<SpeakersAdapter.SpeakersViewHolder>(speakerPosition, click()))
    }
}