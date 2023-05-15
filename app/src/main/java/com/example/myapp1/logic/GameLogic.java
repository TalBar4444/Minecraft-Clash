package com.example.myapp1.logic;

import androidx.annotation.NonNull;

import java.util.Random;

public class GameLogic {

    private int playerPlace;
    private int[] playerBoard;
    private int[][] gameBoard;
    private int boardRows;
    private int boardCols;
    private int life;

    private int score;
    private int distance;
    public static final int NONE = 0;
    public static final int CREEPER = 1;
    public static final int DIAMOND = 2;


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

    public int getDistance() {
        return distance;
    }


    public GameLogic setGameBoard(@NonNull int[][] gameBoard) {
        this.gameBoard = gameBoard;
        for(int i =0; i < gameBoard.length; i++) {
            for(int j = 0; j < gameBoard[i].length;j++) {
                gameBoard[i][j] = NONE;
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

    public void setDistance(int distance){
        this.distance=distance;
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

    public void refreshGameBoard() {
        for (int i = boardRows - 1; i > 0; i--)
            System.arraycopy(gameBoard[i - 1], 0, gameBoard[i], 0, boardCols);

        for (int i = 0; i < boardCols; i++)
            gameBoard[0][i] = NONE;


        int rand = new Random().nextInt(boardCols);
        boolean object = true;
        if (score%4 == 0)
            object = new Random().nextBoolean();
        if (object)
            gameBoard[0][rand] = CREEPER;
        else
            gameBoard[0][rand] = DIAMOND;
    }

    public boolean collisionTest(@NonNull int[] arrayToTest){
        if(arrayToTest[playerPlace] == CREEPER){
            reduceLife();
            return true;
        }
        if(arrayToTest[playerPlace] == DIAMOND){
            score+=4;
        }
        score++;
        return false;
    }
    public void reduceLife(){
        if (life > 0){
            life--;
        }
    }

    public void odometer(){
        distance +=2;
    }
}