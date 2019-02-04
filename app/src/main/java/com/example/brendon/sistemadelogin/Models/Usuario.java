package com.example.brendon.sistemadelogin.Models;

import android.content.Intent;
import android.widget.Toast;

import com.example.brendon.sistemadelogin.TelasExtras.HistoriaActivity;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Usuario {

    @Id
    public long id;
    private String nome;
    private String senha;
    private boolean novoUsuario;
    private int nun_id;

    Usuario(){}
    public Usuario ( int nun_id,String nome, String senha, boolean novoUsuario){
        this.nun_id = nun_id;
        this.nome = nome;
        this.senha = senha;
        this.novoUsuario = novoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public int getNun_id() {
        return nun_id;
    }

    public boolean isNovoUsuario() {
        return novoUsuario;
    }

    public void setNovoUsuario(boolean novoUsuario) {
        this.novoUsuario = novoUsuario;
    }

    public static boolean encontraUsuario(Box<Usuario> boxUsuarios, String nomeUsuario){
        for (int i = 0; i < boxUsuarios.count(); i++) {
            Usuario userAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = userAtual.getNome();
            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                return true;
            }
        }
        return false;
    }

    public static void adicionarUsuario(Box<Usuario> boxUsuarios, int novaId, String nomeUsuario, String senhaUsuario){
        boxUsuarios.put(new Usuario(novaId, nomeUsuario, senhaUsuario,true));
    }

    public static boolean logar(Box<Usuario> boxUsuarios, Box<UsuarioLogado> boxDadosUsuariosLogado, String nomeUsuario, String senhaUsuario) {
        for (int i = 0; i < boxUsuarios.count(); i++) {
            Usuario usuarioAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = usuarioAtual.getNome();
            String senhaUsuarioAtual = usuarioAtual.getSenha();
            int idUsuarioAtual = usuarioAtual.getNun_id();

            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                if (senhaUsuario.equals(senhaUsuarioAtual)) {
                    UsuarioLogado.adicionaUsuarioAtual(boxDadosUsuariosLogado, idUsuarioAtual);
                    return true;
                }
            }
        }
        return false;
    }

    public static void setUserNotNew(Box<Usuario> boxUsuarios, Box<UsuarioLogado> boxDadosUsuariosLogado) {
        int idUsuarioAtual = boxDadosUsuariosLogado.getAll().get(0).getNun_id();
        Usuario usuario = boxUsuarios.get(idUsuarioAtual);
        usuario.setNovoUsuario(false);
        boxUsuarios.put(usuario);
    }

    public static boolean verificaSeUsuarioAtualENovo(Box<Usuario> boxUsuarios, Box<UsuarioLogado> boxDadosUsuariosLogado) {
        return boxUsuarios.getAll().get(UsuarioLogado.retornaIdUserLogado(boxDadosUsuariosLogado) - 1).isNovoUsuario();
    }
}