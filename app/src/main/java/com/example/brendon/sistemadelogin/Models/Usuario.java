package com.example.brendon.sistemadelogin.Models;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Usuario {

    @Id
    public long id;
    private String nome;
    private String senha;
    private boolean logado;
    private int nun_id;

    Usuario(){}

    public Usuario ( int nun_id,String nome, String senha, boolean logado){
        this.nun_id = nun_id;
        this.nome = nome;
        this.senha = senha;
        this.logado = logado;

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

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}