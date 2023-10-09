package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ticket_value;
    TextView Discounted_percent;
    TextView Discounted_price;
    Integer val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ticket_value = findViewById(R.id.editTextTextEmailAddress2);
        Discounted_percent = findViewById(R.id.textView7);
        Discounted_price = findViewById(R.id.textView8);
        Button per5 = findViewById(R.id.button2);
        Button per10 = findViewById(R.id.button3);
        Button per15 = findViewById(R.id.button4);
        Button per20 = findViewById(R.id.button5);
        Button per50 = findViewById(R.id.button6);
        Button clear = findViewById(R.id.button7);
        per5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    discountCalculator(10);
            }
        });
        per10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountCalculator(10);
            }
        });
        per15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountCalculator(15);
            }
        });
        per20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountCalculator(20);
            }
        });
        per50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountCalculator(50);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Discounted_percent.setText("");
                Discounted_price.setText("");
                ticket_value.setText("");
            }
        });
    }
    private void discountCalculator(Integer Discounted_per){
//        String enteredAmount = ticket_value.getText().toString();
//        try{
//            Integer amount = Integer.valueOf(enteredAmount);
//            Integer discountedPrice = (100 - Discounted_per) * amount / 100;
//            Discounted_percent.setText(String.valueOf(Discounted_per) + "%");
//            Discounted_price.setText(String.valueOf(discountedPrice));
//        } catch (NumberFormatException exception){
//            Toast.makeText(MainActivity.this, "Enter a valid number !!", Toast.LENGTH_SHORT).show();
//        }
        val = Integer.valueOf(ticket_value.getText().toString());
        val -= (100*Discounted_per)/100;
        Discounted_price.setText(String.valueOf(val)+"%");
        Discounted_percent.setText(String.valueOf(Discounted_per));
    }

}