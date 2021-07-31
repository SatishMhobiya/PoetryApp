package com.indapps.poetry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.indapps.poetry.Adapter.PoetryAdapter;
import com.indapps.poetry.Api.ApiClient;
import com.indapps.poetry.Api.ApiInterface;
import com.indapps.poetry.Models.PoetryModel;
import com.indapps.poetry.Response.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter adapter;
    ApiInterface apiInterface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSupportActionBar(toolbar);
        getdata();

    }

    public void init(){
        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setAdapter(List<PoetryModel> poetryModels){
        adapter = new PoetryAdapter(this, poetryModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getdata(){
        apiInterface.getpoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {
                try {
                    if( response.body() != null){
                        if (response.body().getStatus().equals("1")) {
                            setAdapter(response.body().getData());
                        }else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.e("onResponse: ", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_poetry_menu :
//                Toast.makeText(this, "Add Poetry", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AddPoetry.class);
                startActivity(intent);
                return true;

            default:
                super.onOptionsItemSelected(item);
        }

        return true;

    }
}