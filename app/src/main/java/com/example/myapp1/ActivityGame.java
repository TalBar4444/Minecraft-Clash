package com.example.myapp1;

import static com.example.myapp1.GameLogic.ENEMY;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame extends AppCompatActivity {

    private ArrayList<AppCompatImageView> game_hearts;

    private ArrayList<LinearLayoutCompat> linearLayouts;
    private ArrayList<AppCompatImageView> player;
    private AppCompatImageView[][] game_board_IMG_creeper;
    private final boolean RIGHT = false;
    private final boolean LEFT = true;

    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_right;

    private GameLogic game;
    private int rows ;
    private int cols ;

    private final int SPEED = 800;
    private MaterialButton game_BTN_newGame;

    private Vibrator v;

    private boolean isGameOver;
    private boolean isGameStart;

    private Timer timer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        findViews();
        isGameOver = false;
        createGame();
        //setPlayer();
        initViews();

    }
    @Override
    protected void onStart(){
        super.onStart();
        if(!isGameOver)
            startGame();
        else
            gameOver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isGameStart && !isGameOver){
            startGame();
        }else if (isGameStart && isGameOver){
            gameOver();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                .setPlayerPlace(game.getBoardCols()/2);
                //.setScore(0);
    }

    private void findViews() {
        game_hearts = new ArrayList<>(
            Arrays.asList(findViewById(R.id.game_IMG_heart1),
                    findViewById(R.id.game_IMG_heart2),
                    findViewById(R.id.game_IMG_heart3)));

        linearLayouts = new ArrayList<>(
                Arrays.asList(findViewById(R.id.game_LL1),
                        findViewById(R.id.game_LL2),
                        findViewById(R.id.game_LL3)));


        game_board_IMG_creeper = new AppCompatImageView[][]{
                {
                        findViewById(R.id.game_IMG_creeper1),
                        findViewById(R.id.game_IMG_creeper2),
                        findViewById(R.id.game_IMG_creeper3),
                  //      findViewById(R.id.game_IMG_creeper4),

                },
                {
                        findViewById(R.id.game_IMG_creeper4),
                        findViewById(R.id.game_IMG_creeper5),
                        findViewById(R.id.game_IMG_creeper6),
                },
                {
                        findViewById(R.id.game_IMG_creeper7),
                        findViewById(R.id.game_IMG_creeper8),
                        findViewById(R.id.game_IMG_creeper9),
                },
                {
                        findViewById(R.id.game_IMG_creeper10),
                        findViewById(R.id.game_IMG_creeper11),
                        findViewById(R.id.game_IMG_creeper12),
                },
        };

        player = new ArrayList<>(
                Arrays.asList(findViewById(R.id.game_IMG_steve1),
                        findViewById(R.id.game_IMG_steve2),
                        findViewById(R.id.game_IMG_steve3)));

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        rows = linearLayouts.get(0).getChildCount();
        cols = player.size();
        game_BTN_newGame = findViewById(R.id.game_BTN_newGame);
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> turnPlayer(LEFT));
        game_BTN_right.setOnClickListener(v -> turnPlayer(RIGHT));
        game_BTN_newGame.setOnClickListener(v -> newGame());

    }

    private void newGame() {
        game_BTN_left.setVisibility(View.VISIBLE);
        game_BTN_right.setVisibility(View.VISIBLE);
        game_BTN_newGame.setVisibility(View.GONE);
        createGame();
        updatePlayer();
        updateGameBoard();
        updateLife();
        isGameOver = false;
        startGame();
    }

    private void turnPlayer(boolean direction) {
        //Log.d("TAG", String.format("The value of name is: %b ", direction));
        if (direction) {
            game.turnLeftPlayer();
        }

        else
            game.turnRightPlayer();
        updatePlayer();

    }

    private void updatePlayer() {
        for (int i = 0; i < player.size(); i++) {
            player.get(i).setVisibility(game.getPlayerPlace() == i ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void updateGameBoard(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                game_board_IMG_creeper[i][j].setVisibility(game.getGameBoard()[i][j] == ENEMY ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }

    private void updateLife(){
        for (AppCompatImageView heart: game_hearts) {
            heart.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < game.getLife(); i++) {
            game_hearts.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void startGame() {
        isGameStart = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int[] endOfBoard = new int [game.getBoardCols()];
                        System.arraycopy(game.getGameBoard()[game.getBoardRows()-1], 0,endOfBoard , 0, game.getBoardCols());
                        if(game.collisionTest(endOfBoard)){
                            Toast.makeText(ActivityGame.this, "BOOM!", Toast.LENGTH_SHORT).show();
                            v.vibrate(400);
                            updateLife();
                        }
                        if(game.getLife() == 0) {
                            gameOver();
                        }
                        game.refreshGameBoard();
                        updateGameBoard();
                    }
                });
            }
        }, 0, SPEED);
    }

    private void gameOver(){
        isGameOver = true;
        timer.cancel();
        game_BTN_left.setVisibility(View.GONE);
        game_BTN_right.setVisibility(View.GONE);
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        game_BTN_newGame.setVisibility(View.VISIBLE);

    }

}


