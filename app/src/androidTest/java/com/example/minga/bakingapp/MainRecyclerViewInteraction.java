package com.example.minga.bakingapp;

import android.support.test.espresso.NoMatchingViewException;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.List;

import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.Espresso.onView;

/**
 * Created by minga on 7/21/2018.
 */

public class MainRecyclerViewInteraction<A> {

    private Matcher<View> viewMatcher;
    private List<A> items;

    private MainRecyclerViewInteraction(Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    public static <A> MainRecyclerViewInteraction<A> onRecyclerView(Matcher<View> viewMatcher) {
        return new MainRecyclerViewInteraction<>(viewMatcher);
    }

    public MainRecyclerViewInteraction<A> withItems(List<A> items) {
        this.items = items;
        return this;
    }

    public MainRecyclerViewInteraction<A> check(ItemViewAssertion<A> itemViewAssertion) {
        for (int i = 0; i < items.size(); i++) {
            onView(viewMatcher)
                    .perform(scrollToPosition(i))
                    .check(new RecyclerItemViewAssertion<>(i, items.get(i), itemViewAssertion));
        }
        return this;
    }

    public interface ItemViewAssertion<A> {
        void check(A item, View view, NoMatchingViewException e);
    }

}
