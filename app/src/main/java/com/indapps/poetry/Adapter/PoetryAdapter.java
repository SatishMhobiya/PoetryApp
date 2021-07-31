package com.indapps.poetry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.indapps.poetry.Api.ApiClient;
import com.indapps.poetry.Api.ApiInterface;
import com.indapps.poetry.Models.PoetryModel;
import com.indapps.poetry.R;
import com.indapps.poetry.Response.DeletePoetryResponse;
import com.indapps.poetry.UpdatePoetry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {

    Context context;
    List<PoetryModel> list;
    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> list) {
        this.context = context;
        this.list = list;
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.file_item_design, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPoet.setText(list.get(position).getPoet_name());
        holder.tvPoetryData.setText(list.get(position).getPoetry_data());
        holder.tvDateTime.setText(list.get(position).getDate_time());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletepoetry(list.get(position).getId()+"", position);
            }
        });
        
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id", list.get(position).getId());
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPoet, tvPoetryData, tvDateTime;
        AppCompatButton updateBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPoet = itemView.findViewById(R.id.tvPoet);
            tvPoetryData = itemView.findViewById(R.id.tvPoetryData);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    public void deletepoetry(String id, int pose){
        apiInterface.deletepoetry(id).enqueue(new Callback<DeletePoetryResponse>() {
            @Override
            public void onResponse(Call<DeletePoetryResponse> call, Response<DeletePoetryResponse> response) {
                try {
                    if (response != null){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (response.body().getStatus().equals("1")){
                            list.remove(pose);
                            notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

}
