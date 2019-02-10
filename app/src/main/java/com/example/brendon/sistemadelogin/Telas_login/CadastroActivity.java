package com.example.brendon.sistemadelogin.Telas_login;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.Models.Boss;
import com.example.brendon.sistemadelogin.dal.App;
import com.example.brendon.sistemadelogin.R;
import io.objectbox.Box;


public class CadastroActivity extends AppCompatActivity {
    Box<Usuario> boxUsuarios;
    Box<Upgrade> boxUpgrads;
    Box<Personagem> boxPersonagens;
    Box<Boss> boxBoss;
    EditText usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        boxUsuarios = ((App) getApplication()).getBoxStore().boxFor(Usuario.class);
        boxUpgrads = ((App) getApplication()).getBoxStore().boxFor(Upgrade.class);
        boxPersonagens = ((App) getApplication()).getBoxStore().boxFor(Personagem.class);
        boxBoss = ((App) getApplication()).getBoxStore().boxFor(Boss.class);

        usuario = findViewById(R.id.usuario_cadastro);
        senha = findViewById(R.id.senha_cadastro);
    }

    public void confirmar_cadastro(View view) {
        String nomeUsuario = usuario.getText().toString();
        String senhaUsuario = senha.getText().toString();
        if (nomeUsuario.equals("") || senhaUsuario.equals("")) {
            Toast.makeText(this, "Dados Insuficentes", Toast.LENGTH_LONG).show();

        } else if (boxUsuarios.getAll().size() == 0 || !Usuario.encontraUsuario(boxUsuarios, nomeUsuario)) {
            int novaId = boxUsuarios.getAll().size() + 1;
            Usuario.adicionarUsuario(boxUsuarios, novaId, nomeUsuario, senhaUsuario);
            Personagem.criaPersonagem(boxPersonagens, novaId, nomeUsuario);
            Boss.criaBoss(boxBoss,novaId);
            Upgrade.setUpgrads(novaId, boxUpgrads);
            Toast.makeText(this, "Cadastrado com sucesso!" , Toast.LENGTH_LONG).show();
            finish();

        }else if(Usuario.encontraUsuario(boxUsuarios,nomeUsuario)){
            Toast.makeText(this, "Usuario existente!" , Toast.LENGTH_LONG).show();
        }
    }
}

