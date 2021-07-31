package com.indapps.poetry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.indapps.poetry.Api.ApiClient;
import com.indapps.poetry.Api.ApiInterface;
import com.indapps.poetry.Response.DeletePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetNameEdt, poetryDataEdt;
    AppCompatButton submitBtn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        init();
        setuptoolbar();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetName = poetNameEdt.getText().toString();
                String poetryData = poetryDataEdt.getText().toString();
                if (poetryData.equals("")){
                    poetryDataEdt.setError("Fill the field");
                }else{
                    if (poetName.equals("")){
                        poetNameEdt.setError("Fill the Poet Name");
                    }else{
                        getData(poetryData, poetName);
                    }
                    }
                }
        });
    }

    private void getData(String poetryData, String poetName) {
        apiInterface.addpoetry(poetryData, poetName).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")){
                        Toast.makeText(AddPoetry.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddPoetry.this, "Added Data Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("onResponse: ", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeletePoetryResponse> call, Throwable t) {

                Log.e("onFailure: ", t.getLocalizedMessage());
            }
        });
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        poetNameEdt = findViewById(R.id.addPoetNameEdt);
        poetryDataEdt = findViewById(R.id.addPoetryDataEdt);
        submitBtn = findViewById(R.id.submitBtn);
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}