package com.example.brendon.sistemadelogin.TelasExtras;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

import com.example.brendon.sistemadelogin.R;

public class FinalGameActivity extends AppCompatActivity {
    Button avancar,finalizar;
    TextView txtAgradecimentos;
    VideoView videoFim;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_game);

        avancar = findViewById(R.id.botao_avancar);
        finalizar = findViewById(R.id.botao_finalizar);
        txtAgradecimentos = findViewById(R.id.txt_agradecimentos);
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
        avancar.setVisibility(View.VISIBLE);
        finalizar.setVisibility(View.VISIBLE);
        txtAgradecimentos.setVisibility(View.VISIBLE);
    }

    public void finalizaGame(View view) {
        Intent intent = new  Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
