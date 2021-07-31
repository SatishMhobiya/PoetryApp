package com.indapps.poetry.Api;

import com.indapps.poetry.Response.DeletePoetryResponse;
import com.indapps.poetry.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("get.php")
    Call<GetPoetryResponse> getpoetry();

    @FormUrlEncoded
    @POST("delete.php")
    Call<DeletePoetryResponse> deletepoetry(@Field("id") String id);

    @FormUrlEncoded
    @POST("api.php")
    Call<DeletePoetryResponse> addpoetry(@Field("poetry") String poetryData, @Field("poet_name") String poetName);

    @FormUrlEncoded
    @POST("update.php")
    Call<DeletePoetryResponse> updatepoetry(@Field("poetry_data") String poetryData, @Field("id") int id);
}
