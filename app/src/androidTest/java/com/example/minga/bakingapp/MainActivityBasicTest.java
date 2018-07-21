package com.example.minga.bakingapp;

import android.content.ClipData;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by minga on 7/21/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void recyclerViewTest() {

        onView(withId(R.id.recipe_cards_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_cards_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("whatever")), click()));

        onView(withId(R.id.recipe_cards_rv))
                .check(matches(hasDescendant(withText("whatever"))));

    }

}
