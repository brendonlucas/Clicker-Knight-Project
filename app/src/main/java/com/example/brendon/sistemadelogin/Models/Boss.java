package com.example.brendon.sistemadelogin.Models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.Box;

@Entity
public class Boss {
    @Id
    public long id;

    private String nome;
    private long vida;
    private int num_Id;

    Boss(){}
    public Boss(int num_Id, String nome, long vida){
        this.num_Id = num_Id;
        this.nome = nome;
        this.vida = vida;
    }

    public String getNome() {
        return nome;
    }

    public long getVida() {
        return vida;
    }

    public void setVida(long vida) {
        this.vida = vida;
    }

    public int getNum_Id() {
        return num_Id;
    }

    public static void criaBoss(Box<Boss> boxBoss,int novaId){
        boxBoss.put(new Boss(novaId, "Dark knight",2000000000L));
    }

    public static long vidaBossAtual(Box<Boss> boxBoss, int idUserLogado) {
        return  boxBoss.getAll().get(idUserLogado -1).getVida();
    }

    public static long hpBossAposClique(Box<Boss> boxBoss, int idUserLogado,int danoPersonagem,long vida_boss) {
        long hpBossAposClique = vida_boss - danoPersonagem;
        Boss boss = boxBoss.get(idUserLogado);
        boss.setVida(hpBossAposClique);
        boxBoss.put(boss);
        return hpBossAposClique;
    }

}