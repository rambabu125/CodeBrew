package com.example.rambabu.codebrew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private RecyclerView rv_press;
    private Adapter Adapter;
    private List<Model> Models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_press = (RecyclerView) findViewById(R.id.rv_press);
        RetrofitHandler.getInstance().getFriends("$2y$10$rJseWBlMdz4aoEF7YPcDHO71Rxef66uoTM17XT9nnnlbUCP87LF9a").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccess()) {
                    Log.d(TAG, "onResponse: " + response.raw());
                    Models = new ArrayList<>();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = jsonObject.optJSONArray("Suggestions");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Model models = new Model();

                            models.setFullname(jsonArray.optJSONObject(i).optString("fullname"));



                            models.setProfile_pic(jsonArray.optJSONObject(i).optString("profile_pic"));

                            Models.add(models);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    buildCardList();
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                }

            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }

        });

    }


    public void buildCardList() {
        if (rv_press.getAdapter() == null) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rv_press.setLayoutManager(mLayoutManager);
            Adapter = new Adapter(this, Models);
            rv_press.setAdapter(Adapter);
        } else {
            Adapter.notifyAdapter(Models);
        }
    }
}