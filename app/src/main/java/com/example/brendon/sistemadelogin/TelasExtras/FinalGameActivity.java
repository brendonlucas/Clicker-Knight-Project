package com.example.brendon.sistemadelogin.TelasExtras;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.widget.VideoView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

import com.example.brendon.sistemadelogin.R;


public class FinalGameActivity extends AppCompatActivity {
    TextView txtAgradecimentos;
    Button avancar,finalizar;
    VideoView videoFim;
    Uri uri;
    public static String SHARED_PREFERENCES = "SharedPrefs";
    public final String TEXT = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_game);

        txtAgradecimentos = findViewById(R.id.txt_agradecimentos);
        finalizar = findViewById(R.id.botao_finalizar);
        avancar = findViewById(R.id.botao_avancar);
        videoFim = findViewById(R.id.videoFimGame);

        String path = "android.resource://" + getPackageName() +"/" +R.raw.videoplayback;
        uri = Uri.parse(path);
        videoFim.setVideoURI(uri);

        videoFim.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                txtAgradecimentos.setVisibility(View.VISIBLE);
            }
        });
        videoFim.start();
    }

    public void avancar(View view) {
        txtAgradecimentos.setVisibility(View.VISIBLE);
        finalizar.setVisibility(View.VISIBLE);
        avancar.setVisibility(View.VISIBLE);
    }

    public void finalizaGame(View view) {
        Intent intent = new  Intent();
        setResult(RESULT_OK,intent);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TEXT,-1);
        editor.apply();
        finish();
    }
}
