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

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {

    EditText updateDataEdt;
    AppCompatButton submitBtn;
    Toolbar toolbar;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);
        init();
        setupToolbar();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateData = updateDataEdt.getText().toString();
                if (updateData.equals("")){
                    updateDataEdt.setError("Fill the field");
                }else{
                    int id = getIntent().getIntExtra("p_id", 0);
                    updatepoetrydata(updateData, id);
                }
            }
        });
    }

    private void updatepoetrydata(String updateData, int id){

        apiInterface.updatepoetry(updateData, id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")){
                        Toast.makeText(UpdatePoetry.this, "Data Update Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UpdatePoetry.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        updateDataEdt = findViewById(R.id.updatePoetryDataEdt);
        submitBtn = findViewById(R.id.submitBtn);
        toolbar = findViewById(R.id.toolbar);
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }
}