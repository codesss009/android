package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddDrink extends AppCompatActivity {
RadioGroup drinkSize;
SeekBar seekbar;
Integer alcohol_per;
Integer sel_drinkSize;
TextView viewPer;
public static final String Drink_key = "drink_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        seekbar = findViewById(R.id.seekBarAlcohol);
        viewPer = findViewById(R.id.textViewAlcoholPercentage);
        drinkSize = findViewById(R.id.radioGroupDrinkSize);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                viewPer.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.buttonAddDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinkSize.getCheckedRadioButtonId() == R.id.radioButton1oz){
                    sel_drinkSize = 1;
                } else if (drinkSize.getCheckedRadioButtonId() == R.id.radioButton5oz) {
                    sel_drinkSize = 5;
                }
                else {
                    sel_drinkSize = 12;
                }
                alcohol_per = seekbar.getProgress();
                Intent intent = new Intent();
                Drinks drinks = new Drinks(sel_drinkSize, alcohol_per);
                intent.putExtra(Drink_key, drinks);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}