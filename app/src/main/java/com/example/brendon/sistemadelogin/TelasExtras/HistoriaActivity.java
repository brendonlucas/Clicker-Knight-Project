package com.example.brendon.sistemadelogin.TelasExtras;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.R;

public class HistoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
    }

    public void fechaEiniciaGame(View view) {
        finish();
    }
}
