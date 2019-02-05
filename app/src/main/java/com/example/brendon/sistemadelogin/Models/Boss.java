package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.Box;

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

    public static int hpBossAposClique(Box<Boss> boxBoss, int idUserLogado,int danoPersonagem,int vida_boss) {
        int hpBossAposClique = vida_boss - danoPersonagem;
        Boss boss = boxBoss.get(idUserLogado);
        boss.setVida(hpBossAposClique);
        boxBoss.put(boss);
        return hpBossAposClique;
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

    public static int vidaBossAtual(Box<Boss> boxBoss, int idUserLogado) {
        return  boxBoss.getAll().get(idUserLogado -1).getVida();
    }
}