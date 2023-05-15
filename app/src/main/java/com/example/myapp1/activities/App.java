package com.example.myapp1.activities;

import android.app.Application;

import com.example.myapp1.MSPV;
import com.example.myapp1.MySignal;
import com.example.myapp1.logic.Player;
import com.example.myapp1.logic.TableScore;

import java.util.ArrayList;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MSPV.init(this);
        if(MSPV.getMe().readTopTenPlayers().getPlayers().size() == 0){
            MSPV.getMe().saveTopTenPlayers(new TableScore().setPlayers(createData()));
        }
        MySignal.init(this);
    }

        private ArrayList<Player> createData() {
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player().setPlayerName("Tal").setScore(40).setDistance(60).setLat(20.63).setLng(15.2));
            players.add(new Player().setPlayerName("Raz").setScore(50).setDistance(70).setLat(5.63).setLng(25.1232));

            return players;
        }

}
