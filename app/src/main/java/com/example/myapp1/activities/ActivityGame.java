package com.example.myapp1.activities;

import static com.example.myapp1.logic.GameLogic.CREEPER;
import static com.example.myapp1.logic.GameLogic.DIAMOND;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.myapp1.R;
import com.example.myapp1.logic.GameLogic;
import com.example.myapp1.MySignal;
import com.example.myapp1.logic.Position;
import com.example.myapp1.StepsDetector;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame extends AppCompatActivity {
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_LEVEL = "KEY_LEVEL";
    private ArrayList<AppCompatImageView> game_hearts;
    private MaterialTextView game_LBL_score;
    private ArrayList<LinearLayoutCompat> linearLayouts;
    private ArrayList<AppCompatImageView> player;
    private AppCompatImageView[][] game_board_IMG;
    private final boolean RIGHT = false;
    private final boolean LEFT = true;
    public static final int CONTROLLERS = 0;
    public static final int TILT = 1;
    public static final int SUCCEED = 0;
    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;
    private GameLogic game;
    private int rows ;
    private int cols ;
    private float sensorNum;
    private StepsDetector stepsDetector;
    public final int SLOW = 800;
    public final int MEDIUM = 400;
    public final int FAST = 200;
    public final int VERY_FAST = 100;
    private AppCompatEditText game_EDT_player;
    private MaterialButton game_BTN_enter;
    private MaterialButton game_BTN_newGame;
    private MaterialButton game_BTN_backToMenu;
    private MaterialButton game_BTN_scoreTable;
    private Bundle bundle;
    private Timer timer = null;
    private int speed;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        bundle = getIntent().getBundleExtra(ActivityMain.KEY_BUNDLE);
        createGame();
        setGame();
    }

    @Override
    protected void onStart(){
        super.onStart();
            if(bundle.getInt(KEY_MODE) ==TILT)
                stepsDetector.start();
        startGame();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(!isGameStart && !isGameOver){
//            startGame();
//        }else if (isGameStart && isGameOver){
//            gameOver();
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bundle.getInt(KEY_MODE) == TILT)
            stepsDetector.stop();
        finishGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void createGame() {
        game = new GameLogic()
                .setBoardRows(rows)
                .setBoardCols(cols)
                .setLife(game_hearts.size());


        game.setGameBoard(new int[game.getBoardRows()][game.getBoardCols()])
                .setPlayerBoard(new int[game.getBoardCols()])
                .setPlayerPlace(game.getBoardCols()/2)
                .setScore(0)
                .setDistance(0);
    }

    private void findViews() {
        game_hearts = new ArrayList<>(
                Arrays.asList(findViewById(R.id.game_IMG_heart1),
                        findViewById(R.id.game_IMG_heart2),
                        findViewById(R.id.game_IMG_heart3)));

        game_LBL_score = findViewById(R.id.game_LBL_score);

        linearLayouts = new ArrayList<>(
                Arrays.asList(findViewById(R.id.game_LL1),
                        findViewById(R.id.game_LL2),
                        findViewById(R.id.game_LL3),
                        findViewById(R.id.game_LL4),
                        findViewById(R.id.game_LL5)));


        game_board_IMG = new AppCompatImageView[][]{
                {
                        findViewById(R.id.game_IMG_1),
                        findViewById(R.id.game_IMG_2),
                        findViewById(R.id.game_IMG_3),
                        findViewById(R.id.game_IMG_4),
                        findViewById(R.id.game_IMG_5),

                },
                {
                        findViewById(R.id.game_IMG_6),
                        findViewById(R.id.game_IMG_7),
                        findViewById(R.id.game_IMG_8),
                        findViewById(R.id.game_IMG_9),
                        findViewById(R.id.game_IMG_10),
                },
                {
                        findViewById(R.id.game_IMG_11),
                        findViewById(R.id.game_IMG_12),
                        findViewById(R.id.game_IMG_13),
                        findViewById(R.id.game_IMG_14),
                        findViewById(R.id.game_IMG_15),
                },
                {

                        findViewById(R.id.game_IMG_16),
                        findViewById(R.id.game_IMG_17),
                        findViewById(R.id.game_IMG_18),
                        findViewById(R.id.game_IMG_19),
                        findViewById(R.id.game_IMG_20),
                },
                {
                        findViewById(R.id.game_IMG_21),
                        findViewById(R.id.game_IMG_22),
                        findViewById(R.id.game_IMG_23),
                        findViewById(R.id.game_IMG_24),
                        findViewById(R.id.game_IMG_25),
                },
                {
                        findViewById(R.id.game_IMG_26),
                        findViewById(R.id.game_IMG_27),
                        findViewById(R.id.game_IMG_28),
                        findViewById(R.id.game_IMG_29),
                        findViewById(R.id.game_IMG_30),
                },
        };

        player = new ArrayList<>(
                Arrays.asList(findViewById(R.id.game_IMG_steve1),
                        findViewById(R.id.game_IMG_steve2),
                        findViewById(R.id.game_IMG_steve3),
                        findViewById(R.id.game_IMG_steve4),
                        findViewById(R.id.game_IMG_steve5)));

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        rows = linearLayouts.get(0).getChildCount();
        cols = player.size();
        game_EDT_player = findViewById(R.id.game_EDT_playerName);
        game_BTN_enter = findViewById(R.id.game_BTN_enter);
        game_BTN_newGame = findViewById(R.id.game_BTN_newGame);
        game_BTN_backToMenu = findViewById(R.id.game_BTN_backToMenu);
        game_BTN_scoreTable = findViewById(R.id.game_BTN_ScoreTable);
    }

    private void setGame() {
        setMode(bundle.getInt(KEY_MODE));
        setSpeedLevel(bundle.getInt(KEY_LEVEL));
    }

    private void setMode(int mode){
        switch (mode){
            case 0:
                this.mode=CONTROLLERS;
                game_BTN_right.setVisibility(View.VISIBLE);
                game_BTN_left.setVisibility(View.VISIBLE);
                initViews();
                break;
            case 1:
                this.mode = TILT;
                game_BTN_right.setVisibility(View.GONE);
                game_BTN_left.setVisibility(View.GONE);
                sensorNum = 0;
                initSensors();
                break;
        }
    }

    private void setSpeedLevel(int speed) {
        switch (speed){
            case 0:setSpeed(SLOW);break;
            case 1:setSpeed(MEDIUM);break;
            case 2:setSpeed(FAST);break;
            case 3:setSpeed(VERY_FAST);break;
        }
    }

    private void setSpeed(int speed) {
        this.speed = speed;
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> turnPlayer(LEFT));
        game_BTN_right.setOnClickListener(v -> turnPlayer(RIGHT));
    }

    private void initSensors() {
        this.stepsDetector = new StepsDetector(this , currentXVal -> {
            if (getPosition(sensorNum) != getPosition(currentXVal)) {
                int lastPosition = getPosition(sensorNum).ordinal();
                int newPosition = getPosition(currentXVal).ordinal();
                int distance = lastPosition - newPosition;
                if (distance > 0) {
                    for (; distance > 0; distance--)
                        turnPlayer(LEFT);
                } else {
                    for (; distance < 0; distance++)
                        turnPlayer(RIGHT);
                }
            }
            sensorNum = currentXVal;
        });
    }

    private Position getPosition(float sensor) {
        if (sensor > 2.5)
            return Position.Left;
        if (2.5 > sensor && sensor > 1)
            return Position.LeftCenter;
        if (1 > sensor && sensor > -1)
            return Position.Center;
        if (-1 > sensor && sensor > -2.5)
            return Position.RightCenter;
        if (-2.5 > sensor)
            return Position.Right;
        return null;
    }

    private void turnPlayer(boolean direction) {
        if (direction) {
            game.turnLeftPlayer();
        }
        else
            game.turnRightPlayer();
        updatePlayer();
    }

    private void updatePlayer() {
        runOnUiThread(() -> {
            for (int i = 0; i < player.size(); i++) {
                player.get(i).setVisibility(game.getPlayerPlace() == i ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    private void startGame() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    game.odometer();
                    checkGameState();
                    if (game.getLife() == 0) {
                        gameOver();
                    }
                    game.refreshGameBoard();
                    updateGameBoard();
                    setScore();
                });
            }
        }, 0, speed);
    }

    private void checkGameState() {
        int[] endOfBoard = new int[game.getBoardCols()];
        System.arraycopy(game.getGameBoard()[game.getBoardRows()-1], 0, endOfBoard, 0, game.getBoardCols());

        if (game.collisionTest(endOfBoard)) {
            MySignal.getInstance().toast("BOOM!");
            MySignal.getInstance().vibrate(200);
            MySignal.getInstance().playSound(ActivityGame.this,R.raw.creeperexplosion);

            updateLife();
        }
    }

    private void updateLife() {
        runOnUiThread(() -> {
            for (AppCompatImageView heart : game_hearts)
                heart.setVisibility(View.INVISIBLE);

            for (int i = 0; i < game.getLife(); i++)
                game_hearts.get(i).setVisibility(View.VISIBLE);
        });
    }

    private void updateGameBoard(){
        runOnUiThread(() -> {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    game_board_IMG[i][j].setVisibility(View.INVISIBLE);
                    if(game.getGameBoard()[i][j] == CREEPER){
                        game_board_IMG[i][j].setImageResource(R.drawable.img_creeper);
                        game_board_IMG[i][j].setVisibility(View.VISIBLE);
                    }
                    else if(game.getGameBoard()[i][j] == DIAMOND){
                        game_board_IMG[i][j].setImageResource(R.drawable.img_diamond);
                        game_board_IMG[i][j].setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setScore() {
        runOnUiThread(() -> game_LBL_score.setText("score : "+game.getScore()));
    }


    private void gameOver(){
        if(bundle.getInt(KEY_MODE) ==TILT) {
            stepsDetector.stop();
        }
        finishGame();
        MySignal.getInstance().toast("Game Over!");
        editEndView();
        initEndViews();
        if(game.getScore() > ActivityScoreTable.getTenthPlacePlayer() || ActivityScoreTable.getHistoryPlayers().size() < ActivityScoreTable.TOP ) {
            game_EDT_player.setVisibility(View.VISIBLE);
            game_BTN_enter.setVisibility(View.VISIBLE);
        }
    }

    private void editEndView(){
        game_BTN_left.setVisibility(View.GONE);
        game_BTN_right.setVisibility(View.GONE);
        game_BTN_newGame.setVisibility(View.VISIBLE);
        game_BTN_scoreTable.setVisibility(View.VISIBLE);
        game_BTN_backToMenu.setVisibility(View.VISIBLE);
    }

    private void initEndViews(){
        game_EDT_player.getText();
        game_BTN_enter.setOnClickListener(v->openWinScoreTable());
        game_BTN_newGame.setOnClickListener(v -> newGame());
        game_BTN_scoreTable.setOnClickListener(v -> openScoreTable());
        game_BTN_backToMenu.setOnClickListener(v -> goToMenu());
    }

    private void finishGame(){
        timer.cancel();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !str.equals("Enter your name:") && !str.trim().isEmpty();
    }

    private void newGame() {
        if(mode == CONTROLLERS) {
            game_BTN_left.setVisibility(View.VISIBLE);
            game_BTN_right.setVisibility(View.VISIBLE);
        }
        clearAllView();
        createGame();
        updatePlayer();
        updateGameBoard();
        updateLife();
        startGame();
    }

    private void clearAllView(){
        game_EDT_player.setVisibility(View.GONE);
        game_BTN_enter.setVisibility(View.GONE);
        game_BTN_newGame.setVisibility(View.GONE);
        game_BTN_scoreTable.setVisibility(View.GONE);
        game_BTN_backToMenu.setVisibility(View.GONE);
    }

    private void openWinScoreTable() {
        if(!isNotNullOrEmpty(game_EDT_player.getText().toString())){
                MySignal.getInstance().toast("Enter your name!");
                return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(game_BTN_enter.getWindowToken(), 0);
        bundle.putString(ActivityScoreTable.KEY_PLAYER_NAME, game_EDT_player.getText().toString());
        bundle.putInt(ActivityScoreTable.KEY_SCORE, game.getScore());
        bundle.putInt(ActivityScoreTable.KEY_DISTANCE, game.getDistance());
        bundle.putInt(ActivityScoreTable.KEY_STATE,SUCCEED);
        openScoreTable();
    }

    private void openScoreTable() {
        Intent intent = new Intent(this, ActivityScoreTable.class);
        intent.putExtra(ActivityMain.KEY_BUNDLE, bundle);
        startActivity(intent);
        finish();
    }

    private void goToMenu() {
        Intent intent = new Intent(this, ActivityMain.class);
        intent.putExtra(ActivityMain.KEY_BUNDLE, bundle);
        startActivity(intent);
        finish();
    }
}

