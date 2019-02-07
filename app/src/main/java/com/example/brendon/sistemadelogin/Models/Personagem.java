package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.Box;

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

    public static void criaPersonagem(Box<Personagem> boxPersonagens ,int novaId, String nomeUsuario){
        boxPersonagens.put(new Personagem(novaId, nomeUsuario,0,1,1));
    }

    public static int goldPersonagemAtual(Box<Personagem> boxPersonagens, int idUserLogado) {
        return boxPersonagens.getAll().get(idUserLogado -1).getGold();
    }

    public static int danoPersonagemAtual(Box<Personagem> boxPersonagens, int idUserLogado) {
        return boxPersonagens.getAll().get(idUserLogado -1).getPoderClique();
    }

    public static int goldCliquePersonagemAtual(Box<Personagem> boxPersonagens, int idUserLogado) {
        return boxPersonagens.getAll().get(idUserLogado -1).getGoldPorClique();
    }

    public static int goldAposClique(Box<Personagem> boxPersonagens, int idUserLogado, int goldAtualPersonagem, int goldPorClique) {
        int goldAposClique = goldAtualPersonagem + goldPorClique;
        Personagem personagem = boxPersonagens.get(idUserLogado);
        personagem.setGold(goldAposClique);
        boxPersonagens.put(personagem);
        return goldAposClique;
    }

    public static void setGoldComBonus(Box<Personagem> boxPersonagens, int idUserLogado) {
        int goldComBonus = Personagem.goldPersonagemAtual(boxPersonagens,idUserLogado) * 2;
        Personagem personagem = boxPersonagens.get(idUserLogado);
        personagem.setGold(goldComBonus);
        boxPersonagens.put(personagem);
    }

}