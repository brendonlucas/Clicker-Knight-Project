package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UsuarioLogado {

    @Id
    public long id;
    private int nun_id;

    UsuarioLogado(){}

    public UsuarioLogado ( int nun_id){
        this.nun_id = nun_id;
    }

    public int getNun_id() {
        return nun_id;
    }

    public void setNun_id(int nun_id) {
        this.nun_id = nun_id;
    }
}