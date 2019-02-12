package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.Box;

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

    public static void setUpgrads(int id, Box<Upgrade> boxUpgrads){
        boxUpgrads.put(new Upgrade(id,"Força +1: Dano x2",100,5));
        boxUpgrads.put(new Upgrade(id,"Força +2: Dano x2",700, 5));
        boxUpgrads.put(new Upgrade(id,"Destreza +1: Dano x2",1900,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 1: Dano x2",3500,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 2: Dano x3",10000,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 3: Dano x3",25000,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 4: Dano x3",700000,3));
        boxUpgrads.put(new Upgrade(id,"Fortifica Armadura: Dano x2",2000000,2));
        boxUpgrads.put(new Upgrade(id,"Força +3: Dano x2",18000000,2));
        boxUpgrads.put(new Upgrade(id,"Destreza +2: Dano x2",37000000,2));
        boxUpgrads.put(new Upgrade(id,"Força +4: Dano x2",90000000,2));
        boxUpgrads.put(new Upgrade(id,"Força +5: Dano x2",200000000,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 5: Dano x3",600000000,5));
        boxUpgrads.put(new Upgrade(id,"Força +6: Dano x3",1000000000,13));
    }
/*
   //- Upgrads para testes rapidos -
    public static void setUpgrads(int id, Box<Upgrade> boxUpgrads){
        boxUpgrads.put(new Upgrade(id,"Força +1: Dano x2",100,5));
        boxUpgrads.put(new Upgrade(id,"Força +2: Dano x2",700, 5));
        boxUpgrads.put(new Upgrade(id,"Destreza +1: Dano x2",1900,5));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 1: Dano x2",3500,5));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 2: Dano x3",10000,5));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 3: Dano x3",25000,10));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 4: Dano x3",700000,5));
        boxUpgrads.put(new Upgrade(id,"Fortifica Armadura: Dano x2",2000000,5));
        boxUpgrads.put(new Upgrade(id,"Força +3: Dano x2",1800000,5));
        boxUpgrads.put(new Upgrade(id,"Destreza +2: Dano x2",37000000,5));
        boxUpgrads.put(new Upgrade(id,"Força +4: Dano x2",90000000,5));
        boxUpgrads.put(new Upgrade(id,"Força +5: Dano x2",20000000,5));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 5: Dano x3",600000000,5));
        boxUpgrads.put(new Upgrade(id,"Força +6: Dano x3",1000000000,20));
    }
*/
    public static void novaMelhoria(int id, Box<Upgrade> boxUpgrads, String nome, int valor,int melhoria){
        boxUpgrads.put(new Upgrade(id,nome,valor,melhoria));
    }

    public static void setDadosAposMelhoria(Box<Personagem> boxPersonagem, int valorMelhoriaAdiconada, int idUserLogado, int goldAtual,int valorUp) {
        int valorAtualClique = Personagem.danoPersonagemAtual(boxPersonagem,idUserLogado);
        int valorAtualGoldPorClique = Personagem.goldCliquePersonagemAtual(boxPersonagem,idUserLogado);
        int novoValorClique = valorAtualClique * valorMelhoriaAdiconada;
        int novoValorGold = goldAtual - valorUp;
        int novoGoldPorClique = valorAtualGoldPorClique * 3;
        // multiplicador de ouro para testes rapidos
        //int novoGoldPorClique = valorAtualGoldPorClique * 20;

        Personagem personagem = boxPersonagem.get(idUserLogado);
        personagem.setPoderClique(novoValorClique);
        personagem.setGold(novoValorGold);
        personagem.setGoldPorClique(novoGoldPorClique);
        boxPersonagem.put(personagem);
    }
}