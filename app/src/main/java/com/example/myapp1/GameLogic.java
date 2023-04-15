package com.example.myapp1;

import android.util.Log;

import java.util.Random;

public class GameLogic {

    private int playerPlace;
    private int[] playerBoard;
    private int[][] gameBoard;
    private int boardRows;
    private int boardCols;
    private int life;
    static final int ENEMY = 1;

    private int score;

    public GameLogic(){}
    public GameLogic(int row, int col, int life){
        this.gameBoard = new int[row][col];
        this.playerBoard = new int[col];
        this.life = life;
        this.playerPlace = (playerBoard.length)/2;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int[] getPlayerBoard() {
        return playerBoard;
    }

    public int getLife() {
        return life;
    }

    public int getBoardCols() {
        return boardCols;
    }

    public int getBoardRows() {
        return boardRows;
    }

    public int getPlayerPlace() {
        return playerPlace;
    }

    public int getScore() {
        return score;
    }


    public GameLogic setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
        for(int i =0; i < gameBoard.length; i++) {
            for(int j = 0; j < gameBoard[i].length;j++) {
                Log.d("length", String.format("gameBoard.length = %d,gameBoard[i].length = %d \n",gameBoard.length, gameBoard[i].length));
                //Log.d("TAG", String.format("row = %d, col = %d \n", i,j));
                gameBoard[i][j] = 0;
            }
        }
        return this;
    }

    public GameLogic setPlayerBoard(int[] playerBoard) {
        this.playerBoard = playerBoard;
        return this;
    }

    public GameLogic setPlayerPlace(int playerPlace) {
        this.playerPlace = playerPlace;
        this.playerBoard[playerPlace] = 1;
        return this;
    }

    public GameLogic setBoardRows(int boardRows) {
        this.boardRows = boardRows;
        return this;
    }

    public GameLogic setBoardCols(int boardCols) {
        this.boardCols = boardCols;
        return this;
    }

    public GameLogic setLife(int life) {
        this.life = life;
        return this;
    }

    public GameLogic setScore(int score) {
        this.score = score;
        return this;
    }

    public void turnLeftPlayer() {
        if (playerPlace > 0) {
            playerBoard[playerPlace] = 0;
            playerPlace--;
            playerBoard[playerPlace] = 1;
        }
    }

    public void turnRightPlayer(){
        if(playerPlace < playerBoard.length-1){
            playerBoard[playerPlace] = 0;
            playerPlace++;
            playerBoard[playerPlace] = 1;
        }
    }

    public void refreshGameBoard(){
        for (int i = 0 ; i < boardRows-1 ; i++)
            System.arraycopy(gameBoard[i],0,gameBoard[i+1] ,0, boardCols);

        for (int i = 0; i < boardCols; i++)
            gameBoard[0][i] = 0;;

        int rand = new Random().nextInt(boardCols);
        gameBoard[0][rand] = ENEMY;
    }

    public boolean collisionTest(int[] arrayToTest){
        if(arrayToTest[playerPlace] == ENEMY){
            reduceLife();
            return true;
        }
        score++;
        return false;
    }
    public void reduceLife(){
        if (life > 0){
            life--;
        }
    }
}