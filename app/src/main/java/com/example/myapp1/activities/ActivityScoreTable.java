package com.example.myapp1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapp1.R;
import com.example.myapp1.activities.fragments.Fragment_List;
import com.example.myapp1.activities.fragments.Fragment_Map;
import com.example.myapp1.interfaces.CallBack_List;
import com.example.myapp1.MSPV;
import com.example.myapp1.MySignal;
import com.example.myapp1.logic.Player;
import com.example.myapp1.logic.PlayersComparator;
import com.example.myapp1.logic.TableScore;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class ActivityScoreTable extends AppCompatActivity {

    private static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_PLAYER_NAME = "KEY_PLAYER_NAME";
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String KEY_DISTANCE = "KEY_DISTANCE";
    public static final String KEY_STATE = "KEY_STATE";
    public static final String KEY_LAT = "KEY_LAT";
    public static final String KEY_LNG = "KEY_LNG";
    private Bundle bundle;
    private MaterialButton score_BTN_backToMenu;
    private Fragment_List fragmentList;
    private Fragment_Map fragmentMap;
    public static final int TOP = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);
        bundle = getIntent().getBundleExtra(ActivityScoreTable.KEY_BUNDLE);
        findViews();
        checkPlayerState();
        fragmentList = new Fragment_List(MSPV.getMe().readTopTenPlayers().getPlayers());
        replaceFragmentList(fragmentList);
        fragmentList.setCallBackList(callBackList);
        fragmentMap = new Fragment_Map(MSPV.getMe().readTopTenPlayers().getPlayers());
        replaceFragmentMap(fragmentMap);
        onActionBTN();
    }
    private void onActionBTN() {score_BTN_backToMenu.setOnClickListener(v -> backToMenu());}

    private void replaceFragmentMap(Fragment_Map fragmentMap) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.top_players_LAY_frameMap, fragmentMap);
        fragmentTransaction.commit();
    }
    private void replaceFragmentList(Fragment_List fragment_list) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.top_players_LAY_frameTop10, fragment_list);
        fragmentTransaction.commit();

    }

    public void checkPlayerState(){
        if (bundle.getInt(KEY_STATE) == 0)
            addNewPlayerToTopTen();

    }
    private void addNewPlayerToTopTen() {
        double lat = bundle.getDouble(KEY_LAT , 0.0);
        double lng = bundle.getDouble(KEY_LNG, 0.0);
        Player currentPlayer = new Player()
                .setPlayerName(bundle.getString(KEY_PLAYER_NAME))
                .setScore(bundle.getInt(KEY_SCORE))
                .setDistance(bundle.getInt(KEY_DISTANCE))
                .setLng(lng)
                .setLat(lat);
        ArrayList<Player> allPlayers = getHistoryPlayers();
        allPlayers.add(currentPlayer);
        allPlayers.sort(new PlayersComparator());
        if (allPlayers.size() > TOP) {
            for (int i = TOP ; i < allPlayers.size(); i++) {
                allPlayers.remove(i);
            }
        }
        MSPV.getMe().saveTopTenPlayers(new TableScore().setPlayers(allPlayers));
    }
    private void backToMenu() {
        Intent intent = new Intent(this, ActivityMain.class);
        bundle.clear();
        intent.putExtra(ActivityMain.KEY_BUNDLE, bundle);
        startActivity(intent);
        finish();
    }
    static ArrayList<Player> getHistoryPlayers() {
        TableScore players = MSPV.getMe().readTopTenPlayers();
        return players.getPlayers();
    }

    public static int getTenthPlacePlayer() {
        TableScore players = MSPV.getMe().readTopTenPlayers();
        int numOfPlayers = players.getPlayers().size();
        return players.getPlayers().get(numOfPlayers-1).getScore();
    }


    private void findViews() {score_BTN_backToMenu = findViewById(R.id.score_BTN_backToMenu);}
    private CallBack_List callBackList = new CallBack_List() {
        @Override
        public void playerClicked(Player player) {
            MySignal.getInstance().toast("Latitude = "+player.getLat()+"\nLongitude = "+player.getLng());
            fragmentMap.showOnMap(player);
        }
    };

}