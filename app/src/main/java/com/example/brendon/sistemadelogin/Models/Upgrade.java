package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Upgrade {
    @Id
    public long id;

    private String nome;
    private int valor;
    private int num_id;
    private int melhoria;

    Upgrade(){}

    public Upgrade (int num_id, String nome, int valor, int melhoria){
        this.num_id = num_id;
        this.nome = nome;
        this.valor = valor;
        this.melhoria = melhoria;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public int getNum_id() {
        return num_id;
    }

    public int getMelhoria() {
        return melhoria;
    }
}