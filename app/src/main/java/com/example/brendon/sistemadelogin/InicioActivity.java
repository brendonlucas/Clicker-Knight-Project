package com.example.brendon.sistemadelogin;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.TranslateAnimation;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AlertDialog;
import android.view.animation.Animation;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.TelasExtras.FinalGameActivity;
import com.example.brendon.sistemadelogin.Telas_login.LoginActivity;
import com.example.brendon.sistemadelogin.Adapters.UpgradsAdapter;
import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Pops.PopStatus;
import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.Pops.PopAjuda;
import com.example.brendon.sistemadelogin.Models.Boss;
import com.example.brendon.sistemadelogin.dal.App;

import java.util.ArrayList;
import io.objectbox.Box;
import java.util.Random;
import java.util.List;


public class InicioActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    private static final int REQUEST_CODE_END_GAME = 100;
    Box<Usuario> boxUsuarios;
    Box<Upgrade>  boxUpgrads;
    Box<Personagem> boxPersonagens;
    Box<Boss> boxBoss;

    MediaPlayer music_fundo;
    AlertDialog dialog;
    TextView txt_hp_Boss, txt_gold, txt_gold_clique, txt_dano, txt_dano_exibido, txt_info_sem_gold;
    ImageView image_hero, image_hit, image_boss;
    RecyclerView recicleUpgrads;
    Button botaoBook, botaoBau;
    AnimationDrawable animaHero,animeBoss,animaHit, animaBook, animaBau;
    public static String SHARED_PREFERENCES = "SharedPrefs";
    public final String TEXT = "idUser";
    private int idUserLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        boxUsuarios = ((App) getApplication()).getBoxStore().boxFor(Usuario.class);
        boxUpgrads = ((App) getApplication()).getBoxStore().boxFor(Upgrade.class);
        boxPersonagens = ((App) getApplication()).getBoxStore().boxFor(Personagem.class);
        boxBoss = ((App) getApplication()).getBoxStore().boxFor(Boss.class);

        loadDados();

        if (boxUsuarios.count() == 0 || idUserLogado == -1) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,REQUEST_CODE);
        }else{
            setAtributosAdicionais();
        }
    }

    public void loadDados(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        idUserLogado = sharedPreferences.getInt(TEXT,-1);
    }

    public void setAtributosAdicionais(){
        loadDados();
        music_fundo = MediaPlayer.create(InicioActivity.this, R.raw.song_bg);
        music_fundo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        music_fundo.start();

        int vida_boss = Boss.vidaBossAtual(boxBoss, idUserLogado);
        if (vida_boss <= 0){
            finalizaGame();
        }else{
            recicleUpgrads = findViewById(R.id.recyclerUpgrads);
            image_hero = findViewById(R.id.Hero);
            image_boss = findViewById(R.id.image_Boss);
            image_hit = findViewById(R.id.hit_boss);
            botaoBook = findViewById(R.id.buttonStatus);
            botaoBau = findViewById(R.id.bonus_blood_coins);
            txt_hp_Boss = findViewById(R.id.txt_hp_boss);
            txt_gold = findViewById(R.id.txt_gold);
            txt_dano_exibido = findViewById(R.id.mostra_hit);
            txt_info_sem_gold = findViewById(R.id.txt_blood_coins_insuficientes);

            image_hero.setBackgroundResource(R.drawable.sequencia_ataque);
            animaHero = (AnimationDrawable)image_hero.getBackground();
            animaHero.start();

            image_hit.setBackgroundResource(R.drawable.sequencia_hit);
            animaHit = (AnimationDrawable)image_hit.getBackground();
            animaHit.start();

            botaoBook.setBackgroundResource(R.drawable.animate_book);
            animaBook = (AnimationDrawable)botaoBook.getBackground();
            animaBook.start();

            image_boss.setBackgroundResource(R.drawable.sequencia_boss);
            animeBoss = (AnimationDrawable)image_boss.getBackground();
            animeBoss.start();

            setValores();

            UpgradsAdapter adapter = new UpgradsAdapter(this,txt_info_sem_gold, boxPersonagens, idUserLogado, boxUpgrads, setUpsDisponiveis(),txt_gold);
            recicleUpgrads.setAdapter(adapter);
            recicleUpgrads.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @SuppressLint("SetTextI18n")
    public void setValores(){
        txt_hp_Boss.setText("" + Boss.vidaBossAtual(boxBoss, idUserLogado));
        txt_gold.setText("" + Personagem.goldPersonagemAtual(boxPersonagens, idUserLogado));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setAtributosAdicionais();

            }else{
                if (idUserLogado == -1){
                    finish();
                }
            }
        }else if (requestCode == REQUEST_CODE_END_GAME){
            if (resultCode == RESULT_OK){
                finish();
            }else {
                finish();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void atacar(View view) {
        int goldAtualPersonagem = Personagem.goldPersonagemAtual(boxPersonagens, idUserLogado);
        int danoPersonagem = Personagem.danoPersonagemAtual(boxPersonagens, idUserLogado);
        int goldPorClique = Personagem.goldCliquePersonagemAtual(boxPersonagens, idUserLogado);
        int vida_boss = Boss.vidaBossAtual(boxBoss, idUserLogado);

        //0.2% de chance de receber Bonus
        Random geradorNum = new Random();
        int numero = geradorNum.nextInt(501);
        if (numero == 1){
            botaoBau.setVisibility(View.VISIBLE);
            botaoBau.setBackgroundResource(R.drawable.animate_bau);
            animaBau = (AnimationDrawable)botaoBau.getBackground();
            animaBau.start();
        }

        animaHero.stop();
        image_hero.setBackgroundResource(R.drawable.sequencia_ataque);
        animaHero.start();

        animaHit.stop();
        image_hit.setBackgroundResource(R.drawable.sequencia_hit);
        animaHit.start();

        int hpBossAposClique = Boss.hpBossAposClique(boxBoss, idUserLogado,danoPersonagem,vida_boss);
        int goldAposClique = Personagem.goldAposClique(boxPersonagens, idUserLogado,goldAtualPersonagem,goldPorClique);

        // seta informações dos valores de vida e Gold na tela
        setValores();
        // cria evento de animação de texto
        Animation mover_dano = new TranslateAnimation(0,20,0,-100);
        mover_dano.setDuration(300);
        txt_dano_exibido.setText(""+ danoPersonagem);
        txt_dano_exibido.startAnimation(mover_dano);

        // set som ao clicar
        MediaPlayer som_clique = MediaPlayer.create(InicioActivity.this, R.raw.hit_1);
        som_clique.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer som_clique) {
                som_clique.release();
            }
        });
        som_clique.start();

        if (hpBossAposClique <= 0){
            finalizaGame();
        }
    }

    // lista os upgrades disponiveis para uma determinada conta logada
    public List<Upgrade> setUpsDisponiveis(){
        List<Upgrade> listaUpsUserAtual = new ArrayList<>();
        for (int i = 0; i< boxUpgrads.getAll().size(); i++){
            Upgrade upgradeAtual = boxUpgrads.getAll().get(i);
            int idUpgradeAtual = upgradeAtual.getNum_id();
            if (idUpgradeAtual == idUserLogado){
                listaUpsUserAtual.add(upgradeAtual);
            }
        }
        return listaUpsUserAtual;
    }

    public void deslogar(View view) {
        dialog = new AlertDialog.Builder(this).setView(R.layout.card_alert_deslogar).show();
    }

    public void cancelaSaida(View v){
        dialog.cancel();
    }

    public void sairDoGame(View v){
        music_fundo.stop();
        dialog.cancel();
        finish();
    }

    // ao clicar no icone do bônus esta função é chamada
    public void recebeBonus(View view) {
        Personagem.setGoldComBonus(boxPersonagens, idUserLogado);
        setValores();
        botaoBau.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Bônus recebido!", Toast.LENGTH_SHORT).show();

        MediaPlayer som_bau = MediaPlayer.create(InicioActivity.this, R.raw.coin_1);
        som_bau.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer som_bau) {
                som_bau.release();
            }
        });
        som_bau.start();
    }
    public void finalizaGame(){
        music_fundo.stop();
        Intent intent = new Intent(this, FinalGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent,REQUEST_CODE_END_GAME);
    }

    public void sairDaConta(View view) {
        music_fundo.stop();
        dialog.cancel();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TEXT,-1);
        editor.apply();
        finish();
    }

    public void ajuda(View view) {
        startActivity(new Intent(this,PopAjuda.class));
    }

    public void verStatus(View view) {
        startActivity(new Intent(this,PopStatus.class));
    }

    @Override
    public void onBackPressed() {
        dialog = new AlertDialog.Builder(this).setView(R.layout.card_alert).show();
    }
}
