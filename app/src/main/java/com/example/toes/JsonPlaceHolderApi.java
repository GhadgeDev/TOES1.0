package com.example.toes;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface JsonPlaceHolderApi {

    @GET()
    Call<List<Post>> getPosts();

    @POST("login/")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("users/")
    Call<Post> createPost(@Field("is_superuser") boolean is_superuser,
                          @Field("is_admin") boolean is_admin,
                          @Field("fName") String fName,
                          @Field("lName") String lName,
                          @Field("username") String username,
                          @Field("password") String password,
                          @Field("dob") String dob,
                          @Field("gender") String gender,
                          @Field("adhar_no") String adhar_no,
                          @Field("profile_image") String profile_image,
                          @Field("address") String address,
                          @Field("phone") String phone,
                          @Field("re_password") String re_password
                        );


}
