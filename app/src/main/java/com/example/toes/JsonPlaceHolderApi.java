package com.example.toes;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

interface JsonPlaceHolderApi {
    //GettingUsersId
    @GET("users/me/")
    Call<Post> getPost(@Header("Authorization") String token);

    //Login
    @POST("login/")
    Call<User> createPost(@Body Post post);

    //Getting all worker list for recruiter to choose
    @GET("api/category/{category_name}/")
    Call<List<GetSpecificWorkerModel>> getWorkerInfo(@Header("Authorization") String authToken,
                                                     @Path("category_name") String a);

    //PostingNewUser
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
                          @Field("re_password") String re_password);

    @GET("/api/otp/{Phone_no}")
    Call<User> sendOTP(@Path(value = "Phone_no") String Phone_no);

    //InsertWorkerDetails
    @FormUrlEncoded
    @POST("/worker/")
    Call<WorkerJobDetails> insertWorkerJobInfo(@Header("Authorization") String token,
                                               @Field("city") String city,
                                               @Field("category_1") String category_1,
                                               @Field("category_1_vc") String category_1_vc,
                                               @Field("category_1_exp") int category_1_exp,
                                               @Field("category_2") String category_2,
                                               @Field("category_2_vc") String category_2_vc,
                                               @Field("category_2_exp") int category_2_exp,
                                               @Field("category_3") String category_3,
                                               @Field("category_3_vc") String category_3_vc,
                                               @Field("category_3_exp") int category_3_exp,
                                               @Field("user") int user
    );

    //Update Worker Job Details
    @FormUrlEncoded
    @PATCH("/workerdetail/{id}/")
    Call<WorkerJobDetails> updateWorkerJobInfo(@Header("Authorization") String token,
                                               @Field("city") String city,
                                               @Field("category_1") String category_1,
                                               @Field("category_1_vc") String category_1_vc,
                                               @Field("category_1_exp") int category_1_exp,
                                               @Field("category_2") String category_2,
                                               @Field("category_2_vc") String category_2_vc,
                                               @Field("category_2_exp") int category_2_exp,
                                               @Field("category_3") String category_3,
                                               @Field("category_3_vc") String category_3_vc,
                                               @Field("category_3_exp") int category_3_exp,
                                               @Field("user") int user,
                                               @Path("id") int id
    );

    //Recruiter clicks search, post get uploaded at worker side
    @FormUrlEncoded
    @POST("job/")
    Call<GetRecruiterJobInfo> getRecruiterJobInfo(@Header("Authorization") String token,
                                                  @Field("job_title") String jobTitle,
                                                  @Field("job_Description") String jbDesc,
                                                  @Field("status") int status,
                                                  @Field("recruiter") int id);

    //Recruiter sends request to worker
    @FormUrlEncoded
    @POST("recruiter/req")
    Call<RecruiterHirePostRequest> postHireReq(@Header("Authorization") String token,
                                               @Field("recruiter") int userMeId,
                                               @Field("status") int status,
                                               @Field("job_detail") int jbDetail,
                                               @Field("worker") int workerId);

   /* //ProfileImage
    @Multipart
    @POST("profile/image")
    Call<GetProfileImage> uploadImage(@Part MultipartBody.Part part,
                                      @Part("profile_image") int userId);
*/
    //SelectRoleApi
    @GET("/worker/")
    Call<List<Post>> getId(@Header("Authorization") String token);

    //Getting all recruiter list for worker to choose
    @GET("/api/specificjobs/{user_id}")
    Call<List<GetSpecificRecruiterModel>> getRecruiterInfo(@Header("Authorization") String aToken,
                                                           @Path("user_id") int uId);

    //Worker sends request to recruiter
    @FormUrlEncoded
    @POST("worker/req/")
    Call<WorkerSendPostRequest> sendPostRequest(@Header("Authorization") String token,
                                                @Field("recruiter") int recruiterId,
                                                @Field("amount") int amount,
                                                @Field("status") int status,
                                                @Field("job_detail") int jbDetail,
                                                @Field("worker") int workerId);

    //Get view all request on worker side (View request WorkerSide)
    @GET("api/workers/requests/{userMeId}")
    Call<List<GetWorkerViewRequestModel>> getWorkerViewRequest(@Header("Authorization") String token,
                                                               @Path("userMeId") int userMeId);

    //Get view all request on recruiter side(View request RecruiterSide)
    @GET("api/recuriters/requests/{userMeId}")
    Call<List<GetRecruiterViewRequestModel>> getRecruiterViewRequest(@Header("Authorization") String token,
                                                                     @Path("userMeId") int userMeId);

    //Edit profile api
    @FormUrlEncoded
    @PATCH("users/{me}/")
    Call<Post> editProfile(@Header("Authorization") String token,
                           @Path("me") int id,
                           @Field("first_name") String fName,
                           @Field("last_name") String lName,
                           @Field("gender") String g,
                           @Field("address") String address);

    //Get worker side accept reject button click
    @GET("api/worreq/{status}/{viewJob_id}")
    Call<GetAcceptRejectBtnClick> getAcceptRejectBtnClick(@Header("Authorization") String token,
                                                          @Path("status") int status,
                                                          @Path("viewJob_id") int viewJob_Id);

    //Get worker side response list of request he had sent
    @GET("api/workers/responses/{user_me}")
    Call<List<GetWorkerResponses>> getWorkerResponse(@Header("Authorization") String token,
                                                     @Path("user_me") int userMeId);

    //Get Jobs into profile
    @GET("/api/specific/workerdetails/{user_id}")
    Call<List<Worker>> getJobs(@Header("Authorization") String aToken,
                               @Path("user_id") int uId);

    //logout
    @POST("/token/logout/")
    Call<User> logOut(@Header("Authorization") String aToken);

    //RecentJobList
    @GET("api/recruiterinfo/{recruiter_id}")
    Call<List<GetRecruiterJobDetails>> getRecentJob(@Header("Authorization") String token,
                                                    @Path("recruiter_id") int recId);

    //Get recruiter side accept reject button click
    @GET("api/recreq/{status}/{job_id}")
    Call<GetAcpRejClickRecruiter> getAccRejClickRecruiter(@Header("Authorization") String token,
                                                          @Path("status") int status,
                                                          @Path("job_id") int jbId);

    //Get recruiter side response list of request he had sent
    @GET("api/recruiters/responses/{userMeId}")
    Call<List<GetRecruiterResponses>> getRecruiterResponses(@Header("Authorization") String token,
                                                            @Path("userMeId") int userMeid);

    //Delete Profession

    @DELETE("/workerdetail/{id}/")
    Call<WorkerJobDetails> deleteProfession1(@Header("Authorization") String token,
                                             @Path("id") int id);

    //Post Emergency Call
    @FormUrlEncoded
    @POST("/emergency")
    Call<EmergencyContact> postEmergencyContact(@Field("contact_no") String contact_no,
                                                @Field("user") int user);

    //Update Emergency Call
  //  @FormUrlEncoded
   // @PATCH("/emergency/{id}/")
 /*   Call<EmergencyContact> updateEmergencyContact(@Path("id") int id,
                                                  @Field("contact_no") String contact_no
                                                );*/


    //get Emergency Contact
    @GET("/api/get/emergency/{id}")
    Call<EmergencyContact> getEmergencyContact(@Header("Authorization") String token,
                                               @Path("id") int id);

}