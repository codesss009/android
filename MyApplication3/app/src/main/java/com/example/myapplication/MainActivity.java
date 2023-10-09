package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    public final OkHttpClient client = new OkHttpClient();
    String text;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    UserRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<User> arrayList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addAPI();


        arrayList.add(new User("text","BB","CC"));
        arrayList.add(new User("11","22","33"));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new UserRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    public void addAPI(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                try {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        String text = responseBody.string();
                        Log.d("demo", text);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public class User{
        String name;
        String appName;
        String date;

        public User(String name, String appName, String date) {
            this.name = name;
            this.appName = appName;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}