package com.example.exam1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText val_a;
    EditText val_b;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        val_a = findViewById(R.id.val_a);
        val_b = findViewById(R.id.val_b);
        result = findViewById(R.id.result);
        findViewById(R.id.buttoncalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double var;

                try{
                    var = Double.valueOf(val_a.getText().toString());
                    Double var2 = Double.valueOf(val_b.getText().toString());
                    Double ans = var/var2;
                    result.setText(String.format("%.2f", ans));
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Enter Valid Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("N/A");
                val_a.setText("");
                val_b.setText("");
            }
        });

    }
}