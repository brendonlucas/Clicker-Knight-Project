package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Personagem {
    @Id
    public long id;

    private String nome;
    private int gold;
    private int poderClique;
    private int goldPorClique;
    private int num_Id;

    Personagem(){}
    public  Personagem(int num_Id, String nome, int gold, int poderClique,int goldPorClique){
        this.num_Id = num_Id;
        this.nome = nome;
        this.gold = gold;
        this.goldPorClique= goldPorClique;
        this.poderClique = poderClique;
    }

    public String getNome() {
        return nome;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPoderClique() {
        return poderClique;
    }

    public void setPoderClique(int poderClique) {
        this.poderClique = poderClique;
    }


    public int getNum_Id() {
        return num_Id;
    }

    public int getGoldPorClique() {
        return goldPorClique;
    }

    public void setGoldPorClique(int goldPorClique) {
        this.goldPorClique = goldPorClique;
    }
}