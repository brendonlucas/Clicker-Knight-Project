package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.Box;

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

    public static int retornaIdUsuarioLogado(Box<Usuario> boxUsuarios, String nomeUsuario, String senhaUsuario) {
        for (int i = 0; i < boxUsuarios.count(); i++) {
            Usuario usuarioAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = usuarioAtual.getNome();
            String senhaUsuarioAtual = usuarioAtual.getSenha();
            int idUsuarioAtual = usuarioAtual.getNun_id();

            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                if (senhaUsuario.equals(senhaUsuarioAtual)) {
                    return idUsuarioAtual;
                }
            }
        }
        return -1;
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

    public static boolean logar(Box<Usuario> boxUsuarios, String nomeUsuario, String senhaUsuario) {
        for (int i = 0; i < boxUsuarios.count(); i++) {
            Usuario usuarioAtual = boxUsuarios.getAll().get(i);
            String nomeUsuarioAtual = usuarioAtual.getNome();
            String senhaUsuarioAtual = usuarioAtual.getSenha();
            if (nomeUsuario.equals(nomeUsuarioAtual)) {
                if (senhaUsuario.equals(senhaUsuarioAtual)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setUserNotNew(Box<Usuario> boxUsuarios,int idUser) {
        Usuario usuario = boxUsuarios.get(idUser);
        usuario.setNovoUsuario(false);
        boxUsuarios.put(usuario);
    }

    public static boolean verificaSeUsuarioAtualENovo(Box<Usuario> boxUsuarios, int idUser) {
        return boxUsuarios.getAll().get(idUser - 1).isNovoUsuario();
    }
}