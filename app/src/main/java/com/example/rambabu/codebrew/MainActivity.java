package com.example.rambabu.codebrew;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private RecyclerView rv_press;
    private Adapter Adapter;
    private List<Model> Models;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_press = (RecyclerView) findViewById(R.id.rv_press);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
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
    public boolean isNetworkAvailable() {
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        return nf != null && nf.isConnected();
    }
    private void setupViewPager(ViewPager viewPager) {
         adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LinksFragment(), "HOME");

        viewPager.setAdapter(adapter);

    }

}