package com.example.bac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView alcohol_perc, No_of_drinks, BAC_level, textViewStatus, WeightDisplay;
    EditText editTextWeight;
    RadioGroup radioGroupGender, radioGroupDrinkSize;
    RadioButton radioButtonMale, radioButtonFemale, radioButton1oz, radioButton5oz, radioButton10oz;
    Button buttonSetWeight, buttonReset, buttonAddDrink;
    SeekBar seekBar;
    Profile profile;
    DrinkDetails drinkDetails;
    ArrayList<DrinkDetails> drinks = new ArrayList<DrinkDetails>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alcohol_perc = findViewById(R.id.alcohol_perc);
        No_of_drinks = findViewById(R.id.No_of_drinks);
        BAC_level = findViewById(R.id.BAC_level);
        textViewStatus = findViewById(R.id.textViewStatus);
        editTextWeight = findViewById(R.id.editTextWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupDrinkSize = findViewById(R.id.radioGroupDrinkSize);
        WeightDisplay = findViewById(R.id.Weightdisplay);
        buttonAddDrink = findViewById(R.id.buttonAddDrink);
                seekBar = findViewById(R.id.seekBar);

        findViewById(R.id.buttonSetWeight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String enteredWeight = String.valueOf(editTextWeight.getText());
                    double weight = Double.valueOf(enteredWeight);
                    String gender;
                    if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale){
                        gender = "Male";
                    }
                    else{
                        gender = "Female";
                    }
                    profile = new Profile(gender, weight);
                    WeightDisplay.setText(enteredWeight + "lbs" +"("+gender+")");
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alcohol_perc.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double size = 1;
                if (profile == null){
                    Toast.makeText(MainActivity.this, "Enter your Weight and Gender", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton5oz){
                        size = 5;
                    }
                    else if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton12oz)
                        size = 12;
                }
                double alcohol_percentage = seekBar.getProgress();
                if (alcohol_percentage > 0){
                    DrinkDetails drink = new DrinkDetails(size, alcohol_percentage);
                    drinks.add(drink);
                    calculateBAC();
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter Alcohol % !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                profile = null;
                drinks.clear();
                editTextWeight.setText("");
                seekBar.setProgress(12);
                calculateBAC();
                 }
                catch(ArithmeticException e){
                Toast.makeText(MainActivity.this, "Arithmetic exception has occured", Toast.LENGTH_SHORT).show();
}
            }
        });
        }
    void calculateBAC(){
        No_of_drinks.setText(String.valueOf(drinks.size()));
        double a = 0.00;
        for(DrinkDetails drink : drinks){
            a += drink.getPercentage() * drink.getSize() / 100.0;
        }
        double valg = 0.73;
        if (profile.getGender() == "Female"){
            valg = 0.66;
        }
        else{
            valg = 0.73;
        }
        Double ans = a * 5.14/profile.getWeight() * valg;
        BAC_level.setText(String.format("%.3f",ans));
        if (ans <= 0.08){
            textViewStatus.setText("You are Safe");
            textViewStatus.setBackgroundColor(getColor(R.color.safe));
        }
        else if(ans  <= 0.2){
            textViewStatus.setText("Be Careful");
            textViewStatus.setBackgroundColor(getColor(R.color.be_careful));
        }
        else{
            textViewStatus.setText("Over the limit");
            textViewStatus.setBackgroundColor(getColor(R.color.over_limit));
        }
    }
}