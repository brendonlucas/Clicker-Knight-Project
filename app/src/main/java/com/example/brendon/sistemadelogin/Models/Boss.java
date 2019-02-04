package com.example.brendon.sistemadelogin.Models;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Boss {
    @Id
    public long id;

    private String nome;
    private int vida;
    private int num_Id;

    Boss(){}
    public Boss(int num_Id, String nome, int vida){
        this.num_Id = num_Id;
        this.nome = nome;
        this.vida = vida;

    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getNum_Id() {
        return num_Id;
    }

    public static void criaBoss(Box<Boss> boxBoss,int novaId){
        boxBoss.put(new Boss(novaId, "Dark knight",1000000000));
    }
}