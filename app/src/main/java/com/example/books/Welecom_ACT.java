package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Welecom_ACT extends AppCompatActivity {

    TextView txttop, txtbottom;
    ImageView img;
    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.welecom_act );

        txttop = findViewById( R.id.txttop );
        txtbottom = findViewById( R.id.txtbottom );
        img = findViewById( R.id.welecom_img);
        top = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        img.setAnimation( top );
        txttop.setAnimation( bottom );
        txtbottom.setAnimation( bottom );
        th.start();


    }
    Thread th = new Thread( new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep( 4000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent in = new Intent( Welecom_ACT.this,Main_ACT.class );
            startActivity( in );
            finish();
        }
    } );
}
