package com.leomund.blackwhite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class Game extends Activity{

    Button[] buttons = new Button[17];

    int startTime;
    int time;
    float cpm;

    TextView scoreView;
    static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for (int i = 1; i < 17; i++) {
            buttons[i] = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(i), "id", getPackageName()));
            buttons[i].setOnClickListener(onClickListener);
        }

        scoreView = (TextView) findViewById(R.id.score);
        start();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.button1: case R.id.button5: case R.id.button9: case R.id.button13:
                    check(1);
                    break;

                case R.id.button2: case R.id.button6: case R.id.button10: case R.id.button14:
                    check(2);
                    break;

                case R.id.button3: case R.id.button7: case R.id.button11: case R.id.button15:
                    check(3);
                    break;

                case R.id.button4: case R.id.button8: case R.id.button12: case R.id.button16:
                    check(4);
                    break;
            }
        }
    };

    private void check(int i){
        if (buttons[i].getBackground().getConstantState() == getDrawable(R.drawable.button_black).getConstantState()){
            move(buttons[i]);
        }
        else{
            over(buttons[i]);
        }
    }

    private void over(Button b) {
        b.setBackgroundResource(R.drawable.button_red);
        scoreView.setVisibility(View.INVISIBLE);

        AlertDialog show = new AlertDialog.Builder(this)
                .setTitle("Game over...")
                .setMessage("Your score: " + score + "\nYour CPM: " + String.format("%.1f", cpm))
                .setCancelable(false)

                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        start();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void start(){
        score = 0;
        cpm = 0;
        scoreView.setText("Score: " + String.valueOf(score));
        scoreView.setVisibility(View.VISIBLE);

        for (int i = 1; i < 17; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(i), "id", getPackageName()));
            button.setBackgroundResource(R.drawable.button_white);
        }

        for (int i = 1; i < 5; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(i*4-((int)(Math.random()*3)+1)), "id", getPackageName()));
            button.setBackgroundResource(R.drawable.button_black);
        }
        startTime = (int) System.currentTimeMillis();
    }

    private void move(Button b){
        b.setBackgroundResource(R.drawable.button_gray);

        for (int i = 1; i < 13; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(i), "id", getPackageName()));
            Button buttonUpper = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(i+4), "id", getPackageName()));
            button.setBackgroundDrawable(buttonUpper.getBackground());
        }

        for (int i = 13; i < 17; i++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(i), "id", getPackageName()));
            button.setBackgroundResource(R.drawable.button_white);
        }

        Button button = (Button) findViewById(getResources().getIdentifier("button" + String.valueOf(16-((int)(Math.random()*4))), "id", getPackageName()));
        button.setBackgroundResource(R.drawable.button_black);

        score += 1;
        scoreView.setText("Score: " + String.valueOf(score));

        time = (int) System.currentTimeMillis() - startTime;
        cpm = score*1000/Float.valueOf(time) * 60;
    }
}
