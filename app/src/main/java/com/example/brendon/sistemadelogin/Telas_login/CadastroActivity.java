package com.example.brendon.sistemadelogin.Telas_login;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.Models.Boss;
import com.example.brendon.sistemadelogin.Models.Personagem;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.Usuario;
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

        } else if (boxUsuarios.getAll().size() == 0 || !encontrou(nomeUsuario)) {
            int novaId = boxUsuarios.getAll().size() + 1;
            boxUsuarios.put(new Usuario(novaId, nomeUsuario, senhaUsuario,false));
            boxPersonagens.put(new Personagem(novaId, nomeUsuario,0,1,false));
            boxBoss.put(new Boss(novaId, "Dark knight",1000000000));
            setUpgrads(novaId);
            Toast.makeText(this, "Cadastrado com sucesso!" , Toast.LENGTH_LONG).show();
            finish();

        }else if(encontrou(nomeUsuario)){
            Toast.makeText(this, "Usuario existente!" , Toast.LENGTH_LONG).show();
        }
    }

    private void setUpgrads(int id){
        boxUpgrads.put(new Upgrade(id,"Eleva a força",100,2));
        boxUpgrads.put(new Upgrade(id,"Melhoramento na espada",700,2));
        boxUpgrads.put(new Upgrade(id,"Eleva a destreza",1900,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 1",3500,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 2",10000,3));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 3",25000,3));
        boxUpgrads.put(new Upgrade(id,"Novo cenario",43300,3));
        boxUpgrads.put(new Upgrade(id,"Fortifica Armadura",70000,4));
    }

    public boolean encontrou(String nomeUsuario) {
        for (int i = 0; i < boxUsuarios.count(); i++) {
            Usuario userAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = userAtual.getNome();
            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                Toast.makeText(this, "achou" , Toast.LENGTH_LONG).show();
                return true;
            }
        }
        Toast.makeText(this, "nao achou" , Toast.LENGTH_LONG).show();
        return false;
    }
}

