package com.example.minga.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/**
 * Created by minga on 7/14/2018.
 */

public class VideoFragment extends Fragment {
    private String videoURL = null;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    static final String BUNDLE_VIDEO_URL = "VIDEO_URL";
    static final String STATE_PLAYER_PLAYBACK_POS = "PLAYBACK_POSITION";
    static final String STATE_PLAYER_CURRENT_WIN = "CURRENT_WINDOW";
    static final String STATE_PLAYER_WHEN_READY = "PLAY_WHEN_READY";

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)  {
        if (this.getArguments() != null) {
            videoURL = this.getArguments ().getString (BUNDLE_VIDEO_URL);
        }

        // recovering the instance state
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong (STATE_PLAYER_PLAYBACK_POS);
            currentWindow = savedInstanceState.getInt (STATE_PLAYER_CURRENT_WIN);
            playWhenReady = savedInstanceState.getBoolean (STATE_PLAYER_WHEN_READY);
            //Log.d ("PRINT2:", String.valueOf (playbackPosition) +" | " +String.valueOf (currentWindow) +" | " + String.valueOf (playWhenReady));
        }

        View view = inflater.inflate (R.layout.video_fragment, parent, false);
        // initialize the view
        mPlayerView = (SimpleExoPlayerView ) view.findViewById (R.id.playerView);
        //Uri videoUri = Uri.parse (videoURL);
        //initializePlayer(videoUri);
        return view;
    }


    private void initializePlayer(){
        Uri mediaUri = Uri.parse (videoURL);
        // create the player
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory (getActivity ()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        // bind the player to the view
        mPlayerView.setPlayer (mExoPlayer);
        mExoPlayer.setPlayWhenReady (playWhenReady);
        mExoPlayer.seekTo (currentWindow, playbackPosition);
        // prepare the MediaSource - LiveStream
        String userAgent = Util.getUserAgent (getActivity (), "RecipeStepVideo");
        MediaSource mediaSource = new ExtractorMediaSource (mediaUri, new DefaultDataSourceFactory (getActivity (), userAgent), new DefaultExtractorsFactory (), null,null);
        mExoPlayer.prepare (mediaSource);
    }

    private void releasePlayer(){
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save the state before onPause() or onStop()
        playbackPosition = mExoPlayer.getCurrentPosition();
        currentWindow = mExoPlayer.getCurrentWindowIndex();
        playWhenReady = mExoPlayer.getPlayWhenReady();
        //Log.d ("PRINT1:", String.valueOf (playbackPosition) +" | " +String.valueOf (currentWindow) +" | " + String.valueOf (playWhenReady));
        outState.putLong (STATE_PLAYER_PLAYBACK_POS, playbackPosition);
        outState.putInt (STATE_PLAYER_CURRENT_WIN, currentWindow);
        outState.putBoolean (STATE_PLAYER_WHEN_READY,playWhenReady);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onPause(){
        super.onPause ();
        if (Util.SDK_INT <=23 ){
            // release player
            releasePlayer ();
        }
    }

    @Override
    public void onStop(){
        super.onStop ();
        if (Util.SDK_INT > 23 ){
            // release player
            releasePlayer ();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
