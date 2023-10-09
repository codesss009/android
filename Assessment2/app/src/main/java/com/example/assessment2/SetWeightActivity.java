package com.example.assessment2;

import static com.example.assessment2.R.id.buttonSetWeight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SetWeightActivity extends AppCompatActivity {
Button setWeight;
TextView weight;
TextView gender;

 final static public String Weight = "weight";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_weight);
        setTitle("Set Weight");
        setWeight = findViewById(R.id.buttonSet);
        weight = findViewById(R.id.weightText);
        setWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String S = weight.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(Weight, S);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Weight,"N/A");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}