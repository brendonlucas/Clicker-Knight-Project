package com.example.brendon.sistemadelogin.Pops;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.brendon.sistemadelogin.Models.Boss;
import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.UsuarioLogado;
import com.example.brendon.sistemadelogin.R;
import com.example.brendon.sistemadelogin.dal.App;

import io.objectbox.Box;

public class PopStatus extends Activity {
    Box<Personagem> boxPersonagens;
    Box<Boss> boxBoss;
    Box<UsuarioLogado> boxDadosUserLogado;
    TextView txt_hp_Boss, txt_gold, txt_gold_clique, txt_dano, txt_nome_hero;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_layout);

        boxPersonagens = ((App) getApplication()).getBoxStore().boxFor(Personagem.class);
        boxBoss = ((App) getApplication()).getBoxStore().boxFor(Boss.class);
        boxDadosUserLogado = ((App)getApplication()).getBoxStore().boxFor(UsuarioLogado.class);

        txt_dano = findViewById(R.id.txt_dano);
        txt_nome_hero = findViewById(R.id.txt_nome);
        txt_gold_clique = findViewById(R.id.txt_gold_clique);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height= displayMetrics.heightPixels;
        int idUserLogado = boxDadosUserLogado.getAll().get(0).getNun_id();

        String nomepersonagem = boxPersonagens.getAll().get(idUserLogado -1).getNome();
        int danoPersonagem = boxPersonagens.getAll().get(idUserLogado -1).getPoderClique();
        int goldPorClique = boxPersonagens.getAll().get(idUserLogado -1).getPoderClique();

        txt_gold_clique.setText("Blood coins por clique: "+goldPorClique);
        txt_nome_hero.setText("Nome: " + nomepersonagem);
        txt_dano.setText("Dano por clique: " + danoPersonagem);

        getWindow().setLayout(width *1,height*1 );

    }

    public void fechastatus(View view) {
        finish();
    }
}
