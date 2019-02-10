package com.example.brendon.sistemadelogin.TelasExtras;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;


import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.dal.App;
import com.example.brendon.sistemadelogin.R;

import io.objectbox.Box;

public class HistoriaActivity extends AppCompatActivity {
    TextView txtHistoria1,txtHistoria2,txtHistoria3,txtHistoria4,txt_clique;
    Button bt_continuar;
    Box<Usuario> boxUsuarios;

    int cont;
    public static String SHARED_PREFERENCES = "SharedPrefs";
    public final String TEXT = "idUser";
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        loadDados();


        boxUsuarios = ((App) getApplication()).getBoxStore().boxFor(Usuario.class);
        //idUserLogado = boxDadosUserLogado.getAll().get(0).getNun_id();

        txtHistoria1 = findViewById(R.id.txtHistoria1);
        txtHistoria2 = findViewById(R.id.txtHistoria2);
        txtHistoria3 = findViewById(R.id.txtHistoria3);
        txtHistoria4 = findViewById(R.id.txtHistoria4);
        txt_clique = findViewById(R.id.txt_clique);
        bt_continuar = findViewById(R.id.bt_continuar);
    }

    public void loadDados(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        idUser = sharedPreferences.getInt(TEXT,-1);
    }

    @SuppressLint("SetTextI18n")
    public void fechaEiniciaGame(View view) {
        String nomeUsuario = boxUsuarios.getAll().get(idUser -1).getNome();
        cont++;
        if (cont == 1){
            txtHistoria1.setText(nomeUsuario + ", você será levado para LOST TITANS FOREST!");
            txtHistoria1.setVisibility(View.VISIBLE);
        }else if(cont == 2){
            txtHistoria2.setText("Onde a unica forma de sair");
            txtHistoria2.setVisibility(View.VISIBLE);
        }else if(cont == 3){
            txtHistoria3.setText("Será derrotando sua copia MALIGNA!!");
            txtHistoria3.setVisibility(View.VISIBLE);
        }else if(cont == 4){
            txtHistoria4.setText("Esta preparado(a)?");
            txtHistoria4.setVisibility(View.VISIBLE);
        }else if(cont == 5){
            txtHistoria4.setText("ENTãO!");
            txt_clique.setVisibility(View.VISIBLE);
            bt_continuar.setVisibility(View.INVISIBLE);
        }
    }
    public void comecar(View view) {
        finish();
    }
}
