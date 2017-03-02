package com.example.user.AndRoy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URI;

public class LoadActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    VideoView videoView;
    String url = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    // http://www.androidbegin.com/tutorial/AndroidCommercial.3gp
    // https://www.youtube.com/watch?v=Lcj0LntTHJI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        videoView = (VideoView) findViewById(R.id.videoView3);
        mProgressDialog = new ProgressDialog(LoadActivity.this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.video));
            mProgressDialog.setTitle("youtube Video");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
        try {
            MediaController mediaController = new MediaController(LoadActivity.this);
            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse(url);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);

        } catch (Exception e) {
            e.printStackTrace();
        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mProgressDialog.dismiss();
                mProgressDialog.setIcon(R.drawable.rounded_button);
                videoView.start();
                videoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoView.canPause();
                    }
                });
            }
        });

    }
}



