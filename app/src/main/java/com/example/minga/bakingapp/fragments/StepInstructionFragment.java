package com.example.minga.bakingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minga.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by minga on 7/20/2018.
 */

public class StepInstructionFragment extends Fragment {
    private String instruction;
    static final String BUNDLE_STEP_INSTRUCTION = "STEP_INSTRUCTION";
    @BindView(R.id.steps_item_desc_tv) TextView stepDescTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)  {
        if (this.getArguments() != null) {
            instruction = this.getArguments ().getString (BUNDLE_STEP_INSTRUCTION);
        }

        View view = inflater.inflate (R.layout.step_instruction_fragment, parent, false);
        ButterKnife.bind(this,view);
        // initialize the view
        stepDescTv.setText (instruction);
        return view;
    }
}
