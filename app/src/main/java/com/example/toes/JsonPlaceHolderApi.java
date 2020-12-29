package com.example.toes;

import androidx.core.app.ComponentActivity;

import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

interface JsonPlaceHolderApi {

 /*   @Headers({
            "Content-Type: application/json",
            "Authorization:Token 7bad7ac4696b7ff76f1513c2879079ecce887c80"
    })*/
    @GET("users/me/")
    Call<Post> getPost(@Header("Authorization") String token);

    @POST("login/")
    Call<User> createPost(@Body Post post);

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

    @GET("/api/user/{Phone_no}")
    Call<User> sendOTP(@Path(value = "Phone_no") String Phone_no);


  /* @GET("api/user/")
   Call<User> sendOTP();*/
}