package com.example.myapp1.logic;

import java.util.ArrayList;
import java.util.List;

public class TableScore {
    private ArrayList<Player> players;

    public TableScore() {
        this.players = new ArrayList<>();
    }

    public TableScore setPlayers(ArrayList<Player> players) {
        this.players = players;
        return this;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int amountOfPlayers(){
        return players.size();
    }

}

