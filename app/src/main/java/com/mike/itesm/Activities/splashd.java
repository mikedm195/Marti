package com.mike.itesm.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.mike.itesm.marti.R;

public class splashd extends Activity {
    @Override
    protected void onCreate(Bundle sa) {
        super.onCreate(sa);

        try{
            VideoView VideoView = new VideoView(this);
            setContentView(VideoView);

            Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a1);
            VideoView.setVideoURI(path);

            VideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    jump();

                }
            });
        VideoView.start();
        }catch(Exception e){
            jump();
        }
    }
    private void jump() {
        if(isFinishing()){
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
