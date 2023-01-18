package com.example.tictacpuig.model;

public class Clasificacion {
    private String player;
    private int wins;
    private int loses;
    private int draws;

    public Clasificacion(String player, int wins, int loses, int draws) {
        this.player = player;
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
    }

    public Clasificacion() {
    }

    public String getPlayer() {
        return player;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getDraws() {
        return draws;
    }

    public void plusWin(){
        this.wins++;
    }

    public void plusLoses(){
        this.loses++;
    }

    public void plusDraws(){
        this.draws++;
    }
}
