package com.example.brendon.sistemadelogin.Models;

import io.objectbox.Box;
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

    public static void setUpgrads(int id, Box<Upgrade> boxUpgrads){
        boxUpgrads.put(new Upgrade(id,"Força +1: Dano x2",100,10));
        boxUpgrads.put(new Upgrade(id,"Força +2: Dano x2",700,10));
        boxUpgrads.put(new Upgrade(id,"Destreza +1: Dano x2",1900,10));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 1: Dano x2",3500,10));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 2: Dano x3",10000,10));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 3: Dano x3",25000,10));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 4: Dano x3",43300,3));
        boxUpgrads.put(new Upgrade(id,"Fortifica Armadura: Dano x2",70000,2));
        boxUpgrads.put(new Upgrade(id,"Força +3: Dano x2",100000,2));
        boxUpgrads.put(new Upgrade(id,"Destreza +2: Dano x2",260000,2));
        boxUpgrads.put(new Upgrade(id,"Força +4: Dano x2",600000,2));
        boxUpgrads.put(new Upgrade(id,"Força +5: Dano x2",999999,2));
        boxUpgrads.put(new Upgrade(id,"Espada nivel 5: Dano x3",2700000,10));
        boxUpgrads.put(new Upgrade(id,"Força +6: Dano x3",4400000,60));
    }
}