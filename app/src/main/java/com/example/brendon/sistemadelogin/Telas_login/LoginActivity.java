package com.example.brendon.sistemadelogin.Telas_login;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.UsuarioLogado;
import com.example.brendon.sistemadelogin.InicioActivity;
import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.dal.App;
import com.example.brendon.sistemadelogin.R;

import io.objectbox.Box;

public class LoginActivity extends AppCompatActivity {

    Box<Usuario> boxUsuarios;
    Box<UsuarioLogado> boxDadosUsuariosLogado;
    Box<Upgrade> boxUpdrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boxUsuarios = ((App)getApplication()).getBoxStore().boxFor(Usuario.class);
        boxUpdrades = ((App)getApplication()).getBoxStore().boxFor(Upgrade.class);
        boxDadosUsuariosLogado = ((App)getApplication()).getBoxStore().boxFor(UsuarioLogado.class);
    }

    public void Cadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void Entrar(View view) {
        EditText usuario, senha;

        usuario = findViewById(R.id.usuario_login);
        senha = findViewById(R.id.senha_login);

        String nomeUsuario = usuario.getText().toString();
        String senhaUsuario = senha.getText().toString();

        if (nomeUsuario.equals("") || senhaUsuario.equals("")){
            Toast.makeText(this, "Dados Insuficentes", Toast.LENGTH_SHORT).show();
        }
        else if(!encontraUsuario(nomeUsuario)){
            Toast.makeText(this, "Usuario não cadastrado", Toast.LENGTH_SHORT).show();
        } else{
            for (int i = 0; i < boxUsuarios.count(); i++){
                Usuario usuarioAtual = boxUsuarios.getAll().get(i);
                String nomeUsuarioAtual = usuarioAtual.getNome();
                String senhaUsuarioAtual = usuarioAtual.getSenha();
                int idUsuarioAtual= usuarioAtual.getNun_id();

                if (nomeUsuario.equals(nomeUsuarioAtual)){
                    if(senhaUsuario.equals(senhaUsuarioAtual)){

                        Intent intent = new Intent();
                        intent.putExtra("logado", true);
                        setResult(RESULT_OK, intent);
                        setaLogado(nomeUsuarioAtual);
                        boxDadosUsuariosLogado.put(new UsuarioLogado(idUsuarioAtual));

                        Toast.makeText(this, "Bem vindo de volta!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void setaLogado(String nomeUser){
        for (int i = 1; i< boxUsuarios.getAll().size(); i++){
            String nome = boxUsuarios.getAll().get(i - 1).getNome();
            if (nome.equals(nomeUser)){
                Toast.makeText(this, nome +"  - ", Toast.LENGTH_SHORT).show();
                Usuario usuario = boxUsuarios.get(i);
                usuario.setLogado(true);
                boxUsuarios.put(usuario);
                break;
            }
        }
    }

    public boolean encontraUsuario(String nomeUsuario){
        for (int i = 0; i < boxUsuarios.count(); i++){
            Usuario usuarioAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = usuarioAtual.getNome();
            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                return true;
            }
        }
    return false;
    }

    public void resetauser(View view){
        boxUsuarios.removeAll();
        boxUpdrades.removeAll();

    }

    public void qtd(View view) {
        long qtdUser = boxUsuarios.count();
        long wtd = boxUsuarios.getAll().size();
        Toast.makeText(this, "" + qtdUser +" kkk " + wtd, Toast.LENGTH_SHORT).show();
    }
}
