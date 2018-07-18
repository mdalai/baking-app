package com.example.minga.bakingapp.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.fragments.VideoFragment;
import com.example.minga.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by minga on 7/14/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Step> steps;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView (R.id.steps_item_cv) CardView cv;
        @BindView(R.id.steps_item_id_tv) TextView stepIdTv;
        @BindView(R.id.steps_item_shortDesc_tv) TextView stepShortDescTv;
        @BindView(R.id.steps_item_desc_tv) TextView stepDescTv;
        //TextView stepVideoUrlTv;

        public ViewHolder(View itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // constructor
    public StepsAdapter(Context c, ArrayList<Step> steps){
        this.context = c;
        this.steps = steps;
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
        holder.stepDescTv.setText (step.getDescription ());
        if (step.getVideoURL () != null){
            //holder.stepVideoUrlTv.setText (step.getVideoURL ());

            String videoURL = step.getVideoURL ();
            Bundle argsVideo = new Bundle ();
            argsVideo.putString ("VIDEO_URL",videoURL);
            VideoFragment videoFrag= new VideoFragment ();
            videoFrag.setArguments (argsVideo);
            FragmentTransaction transaction= ((FragmentActivity)context).getFragmentManager().beginTransaction ();
            transaction.replace (R.id.video_fragment_container, videoFrag);
            transaction.commit ();
        }
    }

    @Override
    public int getItemCount() {
        return this.steps.size ();
    }
}
