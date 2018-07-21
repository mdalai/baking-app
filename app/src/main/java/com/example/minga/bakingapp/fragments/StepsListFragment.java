package com.example.minga.bakingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.adapters.StepsAdapter;
import com.example.minga.bakingapp.models.Step;

import java.util.ArrayList;

/**
 * Created by minga on 7/14/2018.
 */

public class StepsListFragment extends Fragment {
    ArrayList<Step> steps;
    StepsAdapter stepsAdapter;
    boolean mTwoPane;

    static final String BUNDLE_STEPS = "STEPS";
    static final String BUNDLE_STEPS_TWO_PANE = "TWO_PANE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            steps = this.getArguments ().getParcelableArrayList (BUNDLE_STEPS);
            mTwoPane = this.getArguments ().getBoolean (BUNDLE_STEPS_TWO_PANE);
        }

        View view = inflater.inflate (R.layout.steps_list, parent, false);

        // RecyclerView setup
        RecyclerView rvSteps= (RecyclerView )view.findViewById (R.id.steps_list_rv);
        // use this setting to improve performance if you know that changes
        //rvSteps.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager (getActivity ());
        layoutManager.setOrientation (LinearLayoutManager.VERTICAL);
        rvSteps.setLayoutManager (layoutManager);
        stepsAdapter = new StepsAdapter (this.getActivity (), steps, mTwoPane);
        rvSteps.setAdapter (stepsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSteps.getContext(),layoutManager.getOrientation());
        rvSteps.addItemDecoration(dividerItemDecoration);
        rvSteps.setItemAnimator(new DefaultItemAnimator ());

        return view;
    }
}
