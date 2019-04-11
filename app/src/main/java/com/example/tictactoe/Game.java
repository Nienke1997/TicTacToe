package com.example.tictactoe;

import android.util.Log;

import java.io.Serializable;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private TileState[][] board;

    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private int totalButton = 9;
    private Boolean gameOver;

    public Game() {
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        playerOneTurn = true;
        gameOver = false;
        movesPlayed = 0;
    }

    public TileState choose(int row, int column) {
        if (gameOver)
            return TileState.INVALID;
        else if (board[row][column] == TileState.BLANK){
            if (playerOneTurn){
                board[row][column] = TileState.CROSS;
                playerOneTurn = false;
                movesPlayed++;
                return TileState.CROSS;
            }
            else {
                board[row][column] = TileState.CIRCLE;
                playerOneTurn = true;
                movesPlayed++;
                return TileState.CIRCLE;
            }
        }
        return TileState.INVALID;
    }

    public GameState won(){

        // check for wins in rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            int crossCountRow = 0, circleCountRow = 0;
            int crossCountColumn = 0, circleCountColumn = 0;

            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == TileState.CROSS) ++crossCountRow;
                else if (board[i][j] == TileState.CIRCLE) ++circleCountRow;
                if (board[j][i] == TileState.CROSS) ++crossCountColumn;
                else if (board[j][i] == TileState.CIRCLE) ++circleCountColumn;
            }
            if (crossCountRow == 3 || circleCountRow == 3) {
                gameOver = true;
                if (crossCountRow == 3) return GameState.PLAYER_ONE;
                else return GameState.PLAYER_TWO;
            }
            if (crossCountColumn == 3 || circleCountColumn == 3) {
                gameOver = true;
                if (crossCountColumn == 3) return GameState.PLAYER_ONE;
                else return GameState.PLAYER_TWO;
            }
        }
        // check for diagonal options
        if (board[0][0] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][2] == TileState.CROSS){
            gameOver = true;
            return GameState.PLAYER_ONE;
        }
        else if (board[0][0] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][2] == TileState.CIRCLE){
            gameOver = true;
            return GameState.PLAYER_TWO;
        }
        else if (board[0][2] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][0] == TileState.CROSS){
            gameOver = true;
            return GameState.PLAYER_ONE;
        }
        else if (board[0][2] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][0] == TileState.CIRCLE){
            gameOver = true;
            return GameState.PLAYER_TWO;
        }

        if (movesPlayed < totalButton) return GameState.IN_PROGRESS;

        return GameState.DRAW; // if game is not in progress and not won by player one or two it is a draw
    }

    public TileState getTileState (int row, int column){
        return board[row][column];
    }
}