package com.example.brendon.sistemadelogin.Pops;

import android.util.DisplayMetrics;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.R;

public class PopAjuda extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajuda_layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height= displayMetrics.heightPixels;
        getWindow().setLayout(width *1,height*1 );
    }

    public void fechaAjuda(View view) {
        finish();
    }
}
