package com.example.minga.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<> (MainActivity.class);

    @Test
    public void recipeDetailActivityTest() {
        ViewInteraction recyclerView = onView (
                allOf (withId (R.id.recipes_list_fragment),
                        childAtPosition (
                                withId (android.R.id.content),
                                0)));
        recyclerView.perform (actionOnItemAtPosition (1, click ()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (700);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction textView = onView (
                allOf (withId (R.id.ingredient_tv), withText ("unsalted butter: 226.0 G"),
                        childAtPosition (
                                childAtPosition (
                                        withId (R.id.ingredient_list_rv),
                                        1),
                                0),
                        isDisplayed ()));
        textView.check (matches (withText ("unsalted butter: 226.0 G")));

        ViewInteraction textView2 = onView (
                allOf (withId (R.id.steps_item_shortDesc_tv), withText ("Recipe Introduction"),
                        childAtPosition (
                                childAtPosition (
                                        withId (R.id.steps_item_cv),
                                        0),
                                1),
                        isDisplayed ()));
        textView2.check (matches (withText ("Recipe Introduction")));

        ViewInteraction textView3 = onView (
                allOf (withId (R.id.steps_item_shortDesc_tv), withText ("Recipe Introduction"),
                        childAtPosition (
                                childAtPosition (
                                        withId (R.id.steps_item_cv),
                                        0),
                                1),
                        isDisplayed ()));
        textView3.check (matches (withText ("Recipe Introduction")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View> () {
            @Override
            public void describeTo(Description description) {
                description.appendText ("Child at position " + position + " in parent ");
                parentMatcher.describeTo (description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent ();
                return parent instanceof ViewGroup && parentMatcher.matches (parent)
                        && view.equals ((( ViewGroup ) parent).getChildAt (position));
            }
        };
    }
}
