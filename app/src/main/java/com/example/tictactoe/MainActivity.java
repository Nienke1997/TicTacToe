package com.example.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final private int BOARD_SIZE = 3;
    Game game;
    int[][] ids = {
            {R.id.button1, R.id.button2, R.id.button3},
            {R.id.button4, R.id.button5, R.id.button6},
            {R.id.button7, R.id.button8, R.id.button9}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // make sure the tiles are in the correct position after saving or switching of view
        if (savedInstanceState != null){
            game = (Game) savedInstanceState.getSerializable("Game");
            for (int i = 0; i < BOARD_SIZE; i++){
                for (int j = 0; j < BOARD_SIZE; j++){
                    TileState savedState = game.getTileState(i, j);
                    Button savedButton = (Button) findViewById(ids[i][j]);
                    switch(savedState) {
                        case CROSS:
                            savedButton.setText("X");
                            savedButton.setTextColor(Color.parseColor("#e6ffff"));
                            break;
                        case CIRCLE:
                            savedButton.setText("O");
                            savedButton.setTextColor(Color.parseColor("#ffe6e6"));
                            break;
                    }
                }
            }
        }
        else{
            game = new Game();
        }

    }

    public void tileClicked(View view) {
        Button button = (Button)view;

        // get the coordinates of the tile and make them into rows and columns.
        float xcor = button.getX();
        float ycor = button.getY();

        int row = (int) xcor/200;
        if (row == 3){
            --row;
        }
        int column = (int) ycor/200;
        if (column == 3){
            --column;
        }

        TileState state = game.choose(row, column);

        // define what to do when a tile is cross, circle, or invalid
        switch(state) {
            case CROSS:
                button.setText("X");
                button.setTextColor(Color.parseColor("#e6ffff"));
                break;
            case CIRCLE:
                button.setText("O");
                button.setTextColor(Color.parseColor("#ffe6e6"));
                break;
            case INVALID:
                break;
        }
        // button that tells the user what is going on
        Button progress = findViewById(R.id.Progress);

        // button to reset
        Button reset = findViewById(R.id.reset);

        // check if game is won
        GameState gState = game.won();

        // set the UI according to the gamestate.
        if (gState == GameState.IN_PROGRESS) {
            progress.setText("Game in progress");
        }
        else if (gState == GameState.PLAYER_ONE) {
            progress.setText("Game won by player 1!");
            progress.setBackgroundColor(Color.parseColor("#e6ffff"));
            reset.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }
        else if (gState == GameState.PLAYER_TWO){
            progress.setText("Game won by player 2!");
            progress.setBackgroundColor(Color.parseColor("#ffe6e6"));
            reset.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }
        else {
            progress.setText("Draw!");
            progress.setBackgroundColor(Color.parseColor("#ffffe6"));
            reset.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }

    }

    public void resetClicked(View view) {
        game = new Game();
        // make a list of the buttons and make them blank
        List<Button> list = new ArrayList<>();
        Button button1 = findViewById(R.id.button1);
        list.add(button1);
        Button button2 = findViewById(R.id.button2);
        list.add(button2);
        Button button3 = findViewById(R.id.button3);
        list.add(button3);
        Button button4 = findViewById(R.id.button4);
        list.add(button4);
        Button button5 = findViewById(R.id.button5);
        list.add(button5);
        Button button6 = findViewById(R.id.button6);
        list.add(button6);
        Button button7 = findViewById(R.id.button7);
        list.add(button7);
        Button button8 = findViewById(R.id.button8);
        list.add(button8);
        Button button9 = findViewById(R.id.button9);
        list.add(button9);
        Button progress = findViewById(R.id.Progress);


        for (Button i : list){
            i.setText("");
            i.setTextColor(Color.parseColor("#000000"));
        }
        progress.setText("Welcome");
        progress.setBackgroundColor(Color.parseColor("#f2f2f2"));

    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Game", game);
    }


}


