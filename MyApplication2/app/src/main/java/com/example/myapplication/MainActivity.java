package com.example.myapplication;

import static com.example.myapplication.SetProfile.Profile_Key;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView WeightGender;
    TextView textViewBACLevel;
    TextView No_of_Drinks;
    TextView textViewStatus;
    View viewStatus;

    Profile profile;
    Drinks drink;
    ArrayList<Drinks> drinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView WeightGender = findViewById(R.id.textViewWeightGender);
        No_of_Drinks = findViewById(R.id.textViewNoDrinks);
        textViewBACLevel = findViewById(R.id.textViewBACLevel);
        textViewStatus = findViewById(R.id.textViewStatus);
        viewStatus = findViewById(R.id.viewStatus);


        ActivityResultLauncher<Intent> startForProfile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    profile = (Profile) result.getData().getSerializableExtra(SetProfile.Profile_Key);
                    WeightGender.setText(profile.getWeight() + "(" + profile.getGender() + ")");
                    BAC();
                }
            }
        });

        ActivityResultLauncher<Intent> startForAddDrinks = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    drink = (Drinks) result.getData().getSerializableExtra(AddDrink.Drink_key);
                    drinks.add(drink);
                    No_of_Drinks.setText("# Drinks:   " + String.valueOf(drinks.size()));
                    BAC();
                }
            }
        });

        ActivityResultLauncher<Intent> startForViewDrinks = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    drink = (Drinks) result.getData().getSerializableExtra(AddDrink.Drink_key);
                    No_of_Drinks.setText("# Drinks:   " + String.valueOf(drinks.size()));
                    BAC();
                }
            }
        });
        findViewById(R.id.buttonSetProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetProfile.class);
                startForProfile.launch(intent);
            }
        });
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightGender.setText("N/A");
                No_of_Drinks.setText("# Drinks:   0");

            }
        });
        findViewById(R.id.buttonAddDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDrink.class);
                startForAddDrinks.launch(intent);
            }
        });
        findViewById(R.id.buttonViewDrinks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDrinksActivity.class);
                intent.putExtra(AddDrink.Drink_key, drinks);
                startForViewDrinks.launch(intent);
            }
        });
    }

    private void BAC() {
        No_of_Drinks.setText("# Drinks: " + drinks.size());

        if (drinks.size() == 0) {
            textViewBACLevel.setText("BAC Level: 0.000");
            textViewStatus.setText("You're safe");
            viewStatus.setBackgroundResource(R.color.safe_color);
        } else {
            double valueA = 0.0;
            double valueR = 0.66;
            if (profile.getGender() == "Male") {
                valueR = 0.73;
            }

            for (Drinks drink : drinks) {
                valueA = valueA + drink.getAlcohol_per() * drink.getDrink_size() / 100.0;
            }

            double bac = valueA * 5.14 / (Double.valueOf(profile.getWeight())*valueR);

            textViewBACLevel.setText("BAC Level: " + String.format("%.3f", bac));

            if (bac <= 0.08) {
                textViewStatus.setText("You're safe");
                viewStatus.setBackgroundResource(R.color.safe_color);
            } else if (bac <= 0.2) {
                textViewStatus.setText("Be careful");
                viewStatus.setBackgroundResource(R.color.becareful_color);
            } else {
                textViewStatus.setText("Over Limit");
                viewStatus.setBackgroundResource(R.color.overlimit_color);
            }
        }
    }
}