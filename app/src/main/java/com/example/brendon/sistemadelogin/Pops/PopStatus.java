package com.example.brendon.sistemadelogin.Pops;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Boss;
import com.example.brendon.sistemadelogin.dal.App;
import com.example.brendon.sistemadelogin.R;

import io.objectbox.Box;

public class PopStatus extends Activity {
    Box<Personagem> boxPersonagens;
    Box<Boss> boxBoss;
    TextView txt_hp_Boss, txt_gold, txt_gold_clique, txt_dano, txt_nome_hero;
    public static String SHARED_PREFERENCES = "SharedPrefs";
    public final String TEXT = "idUser";
    private int idUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_layout);

        boxPersonagens = ((App) getApplication()).getBoxStore().boxFor(Personagem.class);
        boxBoss = ((App) getApplication()).getBoxStore().boxFor(Boss.class);

        loadDados();
        txt_dano = findViewById(R.id.txt_dano);
        txt_nome_hero = findViewById(R.id.txt_nome);
        txt_gold_clique = findViewById(R.id.txt_gold_clique);
        txt_hp_Boss = findViewById(R.id.txt_vida_do_boss);
        txt_gold = findViewById(R.id.txt_gold_atual);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height= displayMetrics.heightPixels;
        getWindow().setLayout(width *1,height*1 );


        String nomepersonagem = boxPersonagens.getAll().get(idUser -1).getNome();
        int danoPersonagem = Personagem.danoPersonagemAtual(boxPersonagens,idUser);
        int goldPorClique = Personagem.goldCliquePersonagemAtual(boxPersonagens,idUser);
        int vida_do_Boss = Boss.vidaBossAtual(boxBoss,idUser);
        int goldAtual = Personagem.goldPersonagemAtual(boxPersonagens,idUser);

        txt_gold_clique.setText("Blood coins por clique: "+ goldPorClique);
        txt_nome_hero.setText("Nome: " + nomepersonagem);
        txt_dano.setText("Dano por clique: " + danoPersonagem);
        txt_hp_Boss.setText("Vida do Boss: "+ vida_do_Boss);
        txt_gold.setText("Gold Atual: " + goldAtual);
    }

    public void loadDados(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        idUser = sharedPreferences.getInt(TEXT,-1);
    }

    public void fechastatus(View view) {
        finish();
    }
}
