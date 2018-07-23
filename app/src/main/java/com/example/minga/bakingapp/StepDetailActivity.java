package com.example.minga.bakingapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minga.bakingapp.fragments.StepInstructionFragment;
import com.example.minga.bakingapp.fragments.VideoFragment;
import com.example.minga.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity {

    static final String BUNDLE_STEP = "STEP";
    static final String BUNDLE_VIDEO_URL = "VIDEO_URL";
    static final String BUNDLE_STEP_INSTRUCTION = "STEP_INSTRUCTION";

    //@BindView(R.id.steps_item_desc_tv) TextView stepDescTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.step_detail);
        //ButterKnife.bind(this);

        if(savedInstanceState == null){
            Log.d ("PRINT","StepInstructionFragment|VideoFragment is initializing!");
            Intent intent = getIntent ();
            if(intent == null){
                finish ();
                Toast.makeText (this, "The Step Detail is not available", Toast.LENGTH_SHORT).show ();
                return;
            }

            // get the pass data
            Bundle data = intent.getExtras ();
            final Step step = (Step) data.getParcelable (BUNDLE_STEP);
            //Log.d ("PRINT",step.getVideoURL ());

            if (step.getDescription () != null){
                //stepDescTv.setText (step.getDescription ());
                String instruction = step.getDescription ();
                Bundle argsInstruction = new Bundle ();
                argsInstruction.putString (BUNDLE_STEP_INSTRUCTION,instruction);
                StepInstructionFragment stepInstructionFrag = new StepInstructionFragment ();
                stepInstructionFrag.setArguments (argsInstruction);
                FragmentTransaction transaction= this.getFragmentManager().beginTransaction ();
                transaction.replace (R.id.step_instruction_fragment_container, stepInstructionFrag);
                transaction.commit ();
            }


            if (step.getVideoURL () != null){
                String videoURL = step.getVideoURL ();
                Bundle argsVideo = new Bundle ();
                argsVideo.putString (BUNDLE_VIDEO_URL,videoURL);
                VideoFragment videoFrag= new VideoFragment ();
                videoFrag.setArguments (argsVideo);
                FragmentTransaction transaction= this.getFragmentManager().beginTransaction ();
                transaction.replace (R.id.video_fragment_container, videoFrag);
                transaction.commit ();
            }
        }
    }


}
