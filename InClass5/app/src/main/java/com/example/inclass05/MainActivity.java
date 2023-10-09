package com.example.inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AppCategoriesFragment.AppCategoriesListener, AppsListFragment.appsListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     getSupportFragmentManager().beginTransaction()
             .add(R.id.rootView, new AppCategoriesFragment())
             .commit();
    }

    @Override
    public void sendCategory(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppsListFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendApp(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppDetailsFragment.newInstance(app))
                .addToBackStack(null)
                .commit();
    }
}