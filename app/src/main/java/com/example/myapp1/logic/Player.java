package com.example.myapp1.logic;

public class Player {

    private String playerName;
    private int score;
    private int distance;
    private Double lat;
    private Double lng;

    public Player(){}

    public Player(String playerName,int score,int distance){
        this.playerName=playerName;
        this.score=score;
        this.distance=distance;

    }
    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {return score;}

    public int getDistance() {return distance;}

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Player setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public Player setScore(int score){
        this.score= score;
        return this;
    }

    public Player setDistance(int distance) {
        this.distance=distance;
        return this;
    }

    public Player setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Player setLng(Double lng) {
        this.lng = lng;
        return this;
    }

}
