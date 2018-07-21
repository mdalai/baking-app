package com.example.minga.bakingapp.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.StepDetailActivity;
import com.example.minga.bakingapp.fragments.StepInstructionFragment;
import com.example.minga.bakingapp.fragments.VideoFragment;
import com.example.minga.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by minga on 7/14/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Step> steps;
    private boolean mTwoPane;
    static final String BUNDLE_STEP = "STEP";
    static final String BUNDLE_VIDEO_URL = "VIDEO_URL";
    static final String BUNDLE_STEP_INSTRUCTION = "STEP_INSTRUCTION";

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView (R.id.steps_item_cv) CardView cv;
        @BindView(R.id.steps_item_id_tv) TextView stepIdTv;
        @BindView(R.id.steps_item_shortDesc_tv) TextView stepShortDescTv;

        //TextView stepVideoUrlTv;

        public ViewHolder(View itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // constructor
    public StepsAdapter(Context c, ArrayList<Step> steps, boolean mTwoPane){
        this.context = c;
        this.steps = steps;
        this.mTwoPane = mTwoPane;
    }


    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext ();
        LayoutInflater inflater = LayoutInflater.from (context);
        View view = inflater.inflate (R.layout.steps_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        final Step step = this.steps.get(position);

        holder.stepIdTv.setText (String.valueOf (step.getId ()));
        holder.stepShortDescTv.setText (step.getShortDescription ());

        // set click listener
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (mTwoPane == true){
                    //Log.d ("PRINT", String.valueOf (mTwoPane));
                    if (step.getDescription () != null){
                        String instruction = step.getDescription ();
                        Bundle argsInstruction = new Bundle ();
                        argsInstruction.putString (BUNDLE_STEP_INSTRUCTION,instruction);
                        StepInstructionFragment stepInstructionFrag = new StepInstructionFragment ();
                        stepInstructionFrag.setArguments (argsInstruction);
                        FragmentTransaction transaction= ((Activity) context).getFragmentManager().beginTransaction ();
                        transaction.replace (R.id.step_instruction_fragment_container, stepInstructionFrag);
                        transaction.commit ();
                    }

                    if (step.getVideoURL () != null){
                        String videoURL = step.getVideoURL ();
                        Bundle argsVideo = new Bundle ();
                        argsVideo.putString (BUNDLE_VIDEO_URL,videoURL);
                        VideoFragment videoFrag= new VideoFragment ();
                        videoFrag.setArguments (argsVideo);
                        FragmentTransaction transaction= ((Activity) context).getFragmentManager().beginTransaction ();
                        transaction.replace (R.id.video_fragment_container, videoFrag);
                        transaction.commit ();
                    }
                } else {
                    startStepDetailActivity(context, step);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.steps.size ();
    }

    private void startStepDetailActivity(Context context, Step step){
        // destination activity
        Intent intent = new Intent (context, StepDetailActivity.class);
        intent.putExtra (BUNDLE_STEP, step);
        context.startActivity (intent);
    }
}
