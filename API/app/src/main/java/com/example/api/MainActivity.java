package com.example.api;

import static android.content.ContentValues.TAG;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addContact("Alan","alan@mail","123", "HOME");
        createContact();
    }
    void addContact(String name, String email, String phone, String type){
        FormBody formBody = new FormBody.Builder()
                .add("name",name)
                .add("email",email)
                .add("phone",phone)
                .add("type",type)
                .build();

        Request request =new Request.Builder()
                .url("https://www.theappsdr.com/contact/create")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG,response.body().string());
            }
        });
    }
    void createContact(){


        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/persons/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    try {
                      JSONObject json = new JSONObject(responseBody.string());
                        JSONArray personsArray = json.getJSONArray("persons");
                        for (int i = 0; i < personsArray.length() ; i++) {
                            JSONObject personObject = personsArray.getJSONObject(i);
                            JSONObject address = personObject.getJSONObject("address");

                            Log.d(TAG, "onResponse: "+personObject.getString("name")+address.getString("state"));

                        }
                        Log.d(TAG, "lonResponse" + json);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                }
                else{
                    Log.d(TAG, "onResponse: Error");
                }

            }
        });
    }
}