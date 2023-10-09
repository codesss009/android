package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {
    ArrayList<Drinks> drinks = new ArrayList<>();
    TextView out_of_drinks;
    Integer curr_drink = 0;
    TextView drink_Size;
    TextView alcohol_per;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drink);
         out_of_drinks = findViewById(R.id.textViewDrinksCount);
         drink_Size = findViewById(R.id.textViewDrinkSize);
         alcohol_per = findViewById(R.id.textViewDrinkAlcoholPercent);

        if(getIntent() != null && getIntent().hasExtra(AddDrink.Drink_key)){
            drinks = (ArrayList<Drinks>) getIntent().getSerializableExtra(AddDrink.Drink_key);
            curr_drink = 0;
            displayDrinks();
        }
        findViewById(R.id.imageViewNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curr_drink == drinks.size()-1){
                    Toast.makeText(ViewDrinksActivity.this, "You are Seeing the last drink", Toast.LENGTH_SHORT).show();
                }
                else{
                    curr_drink += 1;
                }
                displayDrinks();
            }
        });
        findViewById(R.id.imageViewPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curr_drink == 0){
                    curr_drink = 0;
                    displayDrinks();
                }
                else{
                    curr_drink -= 1;
                    displayDrinks();
                }
            }
        });
        findViewById(R.id.imageViewDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinks.size() != 0){
                    drinks.remove(drinks.get(curr_drink));
                    displayDrinks();
                    curr_drink = 0;
                }
                else{
                    Toast.makeText(ViewDrinksActivity.this, "You have No drinks to display", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(AddDrink.Drink_key, drinks);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
    private void displayDrinks(){
        Drinks drink = drinks.get(curr_drink);
        out_of_drinks.setText("Drink  "+ (curr_drink+1) +" out of "+String.valueOf(drinks.size()));
        drink_Size.setText(String.valueOf(drink.getDrink_size()) + "oz");
        alcohol_per.setText(String.valueOf(drink.getAlcohol_per()) + "  %  Alcohol ");
    }
}