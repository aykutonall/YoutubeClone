package com.mobil.onal.youtubesearch;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Onal on 5.01.2018.
 */
//Burda listview da tıklanan ilgili video için Youtube player açılıp video oynatılıyor.
public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView mYouTubePlayerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_player);

        mYouTubePlayerView = (YouTubePlayerView)findViewById(R.id.player_view);
        mYouTubePlayerView.initialize(YoutubeConnector.KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        if(!restored){
            player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }
}
