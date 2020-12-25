package com.example.toes;

import android.net.Uri;

import java.io.File;
import java.net.FileNameMap;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

interface JsonPlaceHolderApi {

    @GET()
    Call<List<Post>> getPosts();

    @POST("login/")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("/users/")
    Call<Post> createUser(@Field("is_superuser") boolean is_superuser,
                          @Field("is_admin") boolean is_admin,
                          @Field("first_name") String fName,
                          @Field("last_name") String lName,
                          @Field("username") String username,
                          @Field("password") String password,
                          @Field("dob") String dob,
                          @Field("gender") String gender,
                          @Field("aadhar_no") String adhar_no,
                          @Field("profile_image")String profile_image,
                          @Field("address") String address,
                          @Field("phone") String phone,
                          @Field("re_password") String re_password
                        );


}
