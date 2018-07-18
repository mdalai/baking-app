package com.example.minga.bakingapp.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
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
    private String videoURL;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)  {
        if (this.getArguments() != null) {
            videoURL = this.getArguments ().getString ("VIDEO_URL");
        }

        View view = inflater.inflate (R.layout.video_fragment, parent, false);
        // initialize the view
        mPlayerView = (SimpleExoPlayerView ) view.findViewById (R.id.playerView);
        Uri videoUri = Uri.parse (videoURL);
        initializePlayer(videoUri);


        return view;
    }


    private void initializePlayer(Uri mediaUri){
        // initialize the player
        if (mExoPlayer == null){
            // 1. create a default TrackSelector
            TrackSelector trackSelector = new DefaultTrackSelector ();
            // 2. create a default LoadControl
            LoadControl loadControl = new DefaultLoadControl ();
            // 3. create the player
            mExoPlayer = ExoPlayerFactory.newSimpleInstance (getActivity (), trackSelector, loadControl);
            // bind the player to the view
            mPlayerView.setPlayer (mExoPlayer);
            // prepare the MediaSource - LiveStream
            String userAgent = Util.getUserAgent (getActivity (), "RecipeStepVideo");
            MediaSource mediaSource = new ExtractorMediaSource (mediaUri, new DefaultDataSourceFactory (getActivity (), userAgent), new DefaultExtractorsFactory (), null,null);
            mExoPlayer.prepare (mediaSource);
            //mExoPlayer.setPlayWhenReady (true);
        }
    }

    private void releasePlayer(){
        mExoPlayer.stop ();
        mExoPlayer.release ();
        mExoPlayer = null;
    }


}
