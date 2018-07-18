package com.example.minga.bakingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            steps = this.getArguments ().getParcelableArrayList ("STEPS");
        }

        View view = inflater.inflate (R.layout.steps_list, parent, false);

        // RecyclerView setup
        RecyclerView rvSteps= (RecyclerView )view.findViewById (R.id.steps_list_rv);
        // use this setting to improve performance if you know that changes
        rvSteps.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager (getActivity ());
        layoutManager.setOrientation (LinearLayoutManager.VERTICAL);
        rvSteps.setLayoutManager (layoutManager);
        stepsAdapter = new StepsAdapter (this.getActivity (), steps);
        rvSteps.setAdapter (stepsAdapter);
        rvSteps.setItemAnimator(new DefaultItemAnimator ());

        return view;
    }
}
