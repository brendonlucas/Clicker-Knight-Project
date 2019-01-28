package com.example.brendon.sistemadelogin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brendon.sistemadelogin.Adapters.UpgradsAdapter;
import com.example.brendon.sistemadelogin.Models.Boss;
import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.UsuarioLogado;
import com.example.brendon.sistemadelogin.Telas_login.LoginActivity;

import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.dal.App;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class InicioActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    Box<Usuario> boxUsuarios;
    Box<Upgrade>  boxUpgrads;
    Box<Personagem> boxPersonagens;
    Box<Boss> boxBoss;
    Box<UsuarioLogado> boxDadosUserLogado;

    MediaPlayer music_fundo;
    AlertDialog dialog;
    TextView txt_hp_Boss, txt_gold;
    ImageView image_hero, image_hit, image_boss;
    AnimationDrawable anima;
    RecyclerView recicleUpgrads;
    List<Usuario> dadosUserlogado ;
    AnimationDrawable animaHero,animeBoss,animaHit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        boxUsuarios = ((App) getApplication()).getBoxStore().boxFor(Usuario.class);
        boxUpgrads = ((App) getApplication()).getBoxStore().boxFor(Upgrade.class);
        boxPersonagens = ((App) getApplication()).getBoxStore().boxFor(Personagem.class);
        boxBoss = ((App) getApplication()).getBoxStore().boxFor(Boss.class);
        boxDadosUserLogado = ((App)getApplication()).getBoxStore().boxFor(UsuarioLogado.class);

        if (boxUsuarios.count() == 0 || boxDadosUserLogado.count() == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(this, "cadastrese ou logue", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent,REQUEST_CODE);
        }else{
            setAtributosAdicionais();
        }
    }





    public void setAtributosAdicionais(){

        verificaUserLogado();
        music_fundo = MediaPlayer.create(InicioActivity.this, R.raw.song_bg);
        music_fundo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        music_fundo.start();


        recicleUpgrads = findViewById(R.id.recyclerUpgrads);
        image_hero = findViewById(R.id.Hero);
        image_boss = findViewById(R.id.image_Boss);
        image_hit = findViewById(R.id.hit_boss);
        txt_hp_Boss = findViewById(R.id.txt_hp_boss);
        txt_gold = findViewById(R.id.txt_gold);



        image_hero.setBackgroundResource(R.drawable.sequencia_ataque);
        animaHero = (AnimationDrawable)image_hero.getBackground();
        animaHero.start();

        image_hit.setBackgroundResource(R.drawable.sequencia_hit);
        animaHit = (AnimationDrawable)image_hit.getBackground();
        animaHit.start();

        image_boss.setBackgroundResource(R.drawable.sequencia_boss);
        animeBoss = (AnimationDrawable)image_boss.getBackground();
        animeBoss.start();

        setUpsDisponiveis();
        setValores();
        UpgradsAdapter adapter = new UpgradsAdapter(this, boxPersonagens, boxDadosUserLogado, boxUpgrads, setUpsDisponiveis());
        recicleUpgrads.setAdapter(adapter);
        recicleUpgrads.setLayoutManager(new LinearLayoutManager(this));
    }
    @SuppressLint("SetTextI18n")
    public void setValores(){
        int idUserLogado = boxDadosUserLogado.getAll().get(0).getNun_id();
        txt_hp_Boss.setText("" + boxBoss.getAll().get(idUserLogado - 1).getVida());
        txt_gold.setText("" + boxPersonagens.getAll().get(idUserLogado - 1).getGold());

    }

    public boolean verificaUserLogado(){
        Toast.makeText(this, "achou", Toast.LENGTH_SHORT).show();
        for (int i = 0; i< boxUsuarios.count(); i++ ){
            Usuario userAtualVerificado = boxUsuarios.getAll().get(i);
            boolean statusDoUsuario = userAtualVerificado.isLogado();
            if (statusDoUsuario){
                Toast.makeText(this, "achou", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setAtributosAdicionais();

            }else{
                if (!verificaUserLogado()){
                    finish();
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    public void atacar(View view) {
        int idUserLogado = boxDadosUserLogado.getAll().get(0).getNun_id();

        animaHero.stop();
        image_hero.setBackgroundResource(R.drawable.sequencia_ataque);
        animaHero.start();

        animaHit.stop();
        image_hit.setBackgroundResource(R.drawable.sequencia_hit);
        animaHit.start();



        int danoPersonagem = boxPersonagens.getAll().get(idUserLogado -1).getPoderClique();
        int vida_boss = boxBoss.getAll().get(idUserLogado -1).getVida();
        int goldAtualPersonagem = boxPersonagens.getAll().get(idUserLogado -1).getGold();

        int hpBossAposClique = vida_boss - danoPersonagem;

        Boss boss = boxBoss.get(idUserLogado);
        boss.setVida(hpBossAposClique);
        boxBoss.put(boss);

        // A quantidade de gold ganho por clique equivale a seu ataque
        int goldAposClique = goldAtualPersonagem + danoPersonagem;
        Personagem personagem = boxPersonagens.get(idUserLogado);
        personagem.setGold(goldAposClique);
        boxPersonagens.put(personagem);

        setValores();

        MediaPlayer mp = MediaPlayer.create(InicioActivity.this, R.raw.hit_1);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();
    }

    public void sairDaConta(View view) {
        music_fundo.stop();
        boxDadosUserLogado.removeAll();
        setaUserDeslogado();
        finish();
    }

    public void setaUserDeslogado(){
        for (int i = 1; i<= boxUsuarios.getAll().size(); i++){
            boolean userStatus = boxUsuarios.getAll().get(i - 1).isLogado();
            if (userStatus){
                Usuario usuario = boxUsuarios.get(i);
                usuario.setLogado(false);
                boxUsuarios.put(usuario);
                break;
            }
        }
    }

    public List<Upgrade> setUpsDisponiveis(){
        int idUserlogado = boxDadosUserLogado.getAll().get(0).getNun_id();
        List<Upgrade> listaUpsUserAtual = new ArrayList<>();
        for (int i = 0; i< boxUpgrads.getAll().size(); i++){
            Upgrade upgradeAtual = boxUpgrads.getAll().get(i);
            int idUpgradeAtual = upgradeAtual.getNum_id();
            if (idUpgradeAtual == idUserlogado){
                listaUpsUserAtual.add(upgradeAtual);
            }
        }
        return listaUpsUserAtual;
    }

    public void qtd_ups(View view) {
        int qtd = setUpsDisponiveis().size();
        Toast.makeText(this, ""+ qtd, Toast.LENGTH_SHORT).show();
    }

    public void sair(View view) {
        dialog = new AlertDialog.Builder(this).setView(R.layout.card_alert).show();

    }

    public void cancelaSaida(View v){
        dialog.cancel();
    }

    public void sairDoGame(View v){
        music_fundo.stop();
        Toast.makeText(InicioActivity.this, "saiu", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        dialog = new AlertDialog.Builder(this).setView(R.layout.card_alert).show();
    }
}