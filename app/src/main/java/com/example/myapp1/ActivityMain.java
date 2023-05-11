package com.example.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ActivityMain extends AppCompatActivity {
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_LEVEL = "KEY_LEVEL";
    private MaterialButton menu_BTN_score;
    private MaterialButton menu_BTN_settings;
    private MaterialButton menu_BTN_newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        //bundle = getIntent().getBundleExtra()
        onActionBTN();
        findViews();

    }

    private void onActionBTN() {
        menu_BTN_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
    }

    private void findViews(){
        menu_BTN_newGame = findViewById(R.id.menu_BTN_newGame);
        menu_BTN_score = findViewById(R.id.menu_BTN_score);
        menu_BTN_settings = findViewById(R.id.menu_BTN_settings);

    }

    private void openGame() {
        Intent intent = new Intent(this, ActivityGame.class);
//        this.bundle.putInt(GameActivity.KEY_PLAYER_IMAGE,PlayerType);
//        intent.putExtra(MainActivity.KEY_BUNDLE, bundle);
        startActivity(intent);
        finish();
    }

//    private void openScoreTable(int mode) {
//        Intent intent = new Intent(this, PlayerSelectionActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt(GameActivity.KEY_MODE, mode);
//        bundle.putString(TopPlayersActivity.KEY_PLAYER_NAME, menu_EDT_playerName.getText().toString());
//        intent.putExtra(KEY_BUNDLE, bundle);
//        menu_EDT_playerName.setText(null);
//        startActivity(intent);
//    }

}