package com.gerry.simkel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<ValueData<User>> login(@Field("username") String username,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/register")
    Call<ValueData<User>> register(@Field("username") String username,
                                   @Field("password") String password);

    @GET("simkel")
    Call<ValueData<List<Tampil>>> getTampil();

    @FormUrlEncoded
    @POST("simkel")
    Call<ValueNoData> add_tampil(@Field("namaBarang") String namaBarang,
                                 @Field("harga") Integer harga,
                                 @Field("desc") String desc,
                                @Field("user_id") String user_id);

    @FormUrlEncoded
    @PUT("simkel")
    Call<ValueNoData> update_tampil(@Field("id") String id,
                                   @Field("namaBarang") String namaBarang,
                                    @Field("harga") Integer harga,
                                    @Field("desc") String desc);

    @DELETE("simkel/{id}")
    Call<ValueNoData> delete_tampil(@Path("id") String id);

}
