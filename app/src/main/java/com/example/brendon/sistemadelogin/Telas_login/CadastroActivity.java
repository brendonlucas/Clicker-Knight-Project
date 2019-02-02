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

        } else if (boxUsuarios.getAll().size() == 0 || !encontrou(nomeUsuario)) {
            int novaId = boxUsuarios.getAll().size() + 1;
            boxUsuarios.put(new Usuario(novaId, nomeUsuario, senhaUsuario,true));
            boxPersonagens.put(new Personagem(novaId, nomeUsuario,0,1,1,false));
            boxBoss.put(new Boss(novaId, "Dark knight",1000000000));
            setUpgrads(novaId);
            Toast.makeText(this, "Cadastrado com sucesso!" , Toast.LENGTH_LONG).show();
            finish();

        }else if(encontrou(nomeUsuario)){
            Toast.makeText(this, "Usuario existente!" , Toast.LENGTH_LONG).show();
        }
    }

    private void setUpgrads(int id){
        boxUpgrads.put(new Upgrade(id,"Força +1: Dano x2",100,2));
        boxUpgrads.put(new Upgrade(id,"Força +2: Dano x2",700,2));
        boxUpgrads.put(new Upgrade(id,"Destreza +1: Dano x2",1900,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 1: Dano x2",3500,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 2: Dano x3",10000,3));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 3: Dano x3",25000,3));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 4: Dano x3",43300,3));
        boxUpgrads.put(new Upgrade(id,"Fortifica Armadura: Dano x2",70000,2));
        boxUpgrads.put(new Upgrade(id,"Força +3: Dano x2",100000,2));
        boxUpgrads.put(new Upgrade(id,"Destreza +2: Dano x2",260000,2));
        boxUpgrads.put(new Upgrade(id,"Força +4: Dano x2",600000,2));
        boxUpgrads.put(new Upgrade(id,"Força +5: Dano x2",999999,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 5: Dano x3",2700000,3));
        boxUpgrads.put(new Upgrade(id,"Força +6: Dano x3",4400000,60));
    }

    public boolean encontrou(String nomeUsuario) {
        for (int i = 0; i < boxUsuarios.count(); i++) {
            Usuario userAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = userAtual.getNome();
            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                return true;
            }
        }
        return false;
    }
}

