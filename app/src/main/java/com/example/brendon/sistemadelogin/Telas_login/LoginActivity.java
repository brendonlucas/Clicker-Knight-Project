package com.example.brendon.sistemadelogin.Telas_login;

import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.example.brendon.sistemadelogin.TelasExtras.HistoriaActivity;
import com.example.brendon.sistemadelogin.Models.UsuarioLogado;
import com.example.brendon.sistemadelogin.Models.Upgrade;
import com.example.brendon.sistemadelogin.Models.Usuario;
import com.example.brendon.sistemadelogin.dal.App;
import com.example.brendon.sistemadelogin.R;

import io.objectbox.Box;

public class LoginActivity extends AppCompatActivity {

    Box<UsuarioLogado> boxDadosUsuariosLogado;
    Box<Usuario> boxUsuarios;
    Box<Upgrade> boxUpdrades;
    EditText usuario, senha;
    MediaPlayer music_fundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boxUsuarios = ((App)getApplication()).getBoxStore().boxFor(Usuario.class);
        boxUpdrades = ((App)getApplication()).getBoxStore().boxFor(Upgrade.class);
        boxDadosUsuariosLogado = ((App)getApplication()).getBoxStore().boxFor(UsuarioLogado.class);

        music_fundo = MediaPlayer.create(LoginActivity.this, R.raw.song_bg_login);
        music_fundo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer music_fundo) {
                music_fundo.start();
            }
        });
        music_fundo.start();

        usuario = findViewById(R.id.usuario_login);
        senha = findViewById(R.id.senha_login);
    }

    public void Cadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void Entrar(View view) {
        String nomeUsuario = usuario.getText().toString();
        String senhaUsuario = senha.getText().toString();

        if (nomeUsuario.equals("") || senhaUsuario.equals("")){
            Toast.makeText(this, "Dados Insuficentes", Toast.LENGTH_SHORT).show();
        }
        else if(!Usuario.encontraUsuario(boxUsuarios,nomeUsuario)){
            Toast.makeText(this, "Usuario não cadastrado", Toast.LENGTH_SHORT).show();
        } else{
            if (Usuario.logar(boxUsuarios,boxDadosUsuariosLogado,nomeUsuario,senhaUsuario)){
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                music_fundo.stop();

                if(Usuario.verificaSeUsuarioAtualENovo(boxUsuarios, boxDadosUsuariosLogado)){
                    Usuario.setUserNotNew(boxUsuarios, boxDadosUsuariosLogado);
                    Intent intentIntroducao = new Intent(this, HistoriaActivity.class);
                    startActivity(intentIntroducao);
                }

                Toast.makeText(this, "Bem vindo!", Toast.LENGTH_SHORT).show();
                finish();

            }else{
                Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show();
            }
/*
            for (int i = 0; i < boxUsuarios.count(); i++){
                Usuario usuarioAtual = boxUsuarios.getAll().get(i);
                String nomeUsuarioAtual = usuarioAtual.getNome();
                String senhaUsuarioAtual = usuarioAtual.getSenha();
                boolean statusUsuarioAtual = usuarioAtual.isNovoUsuario();
                int idUsuarioAtual = usuarioAtual.getNun_id();

                if (nomeUsuario.equals(nomeUsuarioAtual)){
                    if(senhaUsuario.equals(senhaUsuarioAtual)){

                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        boxDadosUsuariosLogado.put(new UsuarioLogado(idUsuarioAtual));

                        if(statusUsuarioAtual){
                            music_fundo.stop();
                            Usuario usuario = boxUsuarios.get(idUsuarioAtual);
                            usuario.setNovoUsuario(false);
                            boxUsuarios.put(usuario);
                            Intent intentIntroducao = new Intent(this, HistoriaActivity.class);
                            startActivity(intentIntroducao);
                        }
                        music_fundo.stop();
                        Toast.makeText(this, "Bem vindo!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            */
        }
    }


/*
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
*/
}
