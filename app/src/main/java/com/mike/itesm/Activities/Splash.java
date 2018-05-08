package com.mike.itesm.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.mike.itesm.marti.R;

import java.util.Random;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle sa) {
        super.onCreate(sa);
        VideoView VideoView = new VideoView(this);
        try{


            setContentView(VideoView);

            Uri path= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a1);
            Random r = new Random();
            int ceis = r.nextInt(2 - 0) + 0;
            switch (ceis){
                case 0:
                    path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a1);
                    break;
                case 1:
                    path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a2);
                    break;
                case 2:
                    path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a3);
                    break;
            }

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
