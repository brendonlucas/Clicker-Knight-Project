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
    private boolean personagemFinalizado;
    private int num_Id;

    Personagem(){}
    public  Personagem(int num_Id, String nome, int gold, int poderClique, boolean personagemFinalizado){
        this.num_Id = num_Id;
        this.nome = nome;
        this.gold = gold;
        this.poderClique = poderClique;
        this.personagemFinalizado = personagemFinalizado;
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

    public boolean isPersonagemFinalizado() {
        return personagemFinalizado;
    }

    public void setPersonagemFinalizado(boolean personagemFinalizado) {
        this.personagemFinalizado = personagemFinalizado;
    }

    public int getNum_Id() {
        return num_Id;
    }
}