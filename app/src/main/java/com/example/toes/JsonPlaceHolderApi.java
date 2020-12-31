package com.example.toes;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

interface JsonPlaceHolderApi {


    @GET("users/me/")
    Call<Post> getPost(@Header("Authorization") String token);

    @POST("login/")
    Call<User> createPost(@Body Post post);

    //Getting all worker list for recruiter to choose
    @GET("api/category/{category_name}/")
    Call<List<GetSpecificWorkerModel>> getWorkerInfo(@Header("Authorization") String authToken,
                                                     @Path("category_name") String a);

    @Multipart
    @POST("profile/image")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part part,
                                   @Part("profile_image") RequestBody userId);


    @FormUrlEncoded
    @POST("users/")
    Call<Post> createUser(@Field("is_superuser") boolean is_superuser,
                          @Field("is_admin") boolean is_admin,
                          @Field("first_name") String fName,
                          @Field("last_name") String lName,
                          @Field("username") String username,
                          @Field("password") String password,
                          @Field("dob") String dob,
                          @Field("gender") String gender,
                          @Field("aadhar_no") String adhar_no,
                          @Field("address") String address,
                          @Field("phone") String phone,
                          @Field("re_password") String re_password
    );


    @GET("/api/user/{Phone_no}")
    Call<User> sendOTP(@Path(value = "Phone_no") String Phone_no);

    //Recruiter clicks search, post get uploaded at worker side
    @FormUrlEncoded
    @POST("job/")
    Call<GetRecruiterJobInfo> getRecruiterJobInfo(@Header("Authorization") String token,
                                                  @Field("job_title") String jobTitle,
                                                  @Field("job_Description") String jbDesc,
                                                  @Field("status") int status,
                                                  @Field("recruiter") int id);

    //Recruiter clicks hire button
    @FormUrlEncoded
    @POST("recruiter/req")
    Call<RecruiterHirePostRequest> postHireReq(@Header("Authorization") String token,
                                               @Field("recruiter") int userMeId,
                                               @Field("status") int status,
                                               @Field("job_detail") int jbDetail,
                                               @Field("worker") int workerId);


}