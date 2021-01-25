package com.project.eevent.remote;


import com.project.eevent.Constant;
import com.project.eevent.Photographer.Product.model.ProductPhotographer;
import com.project.eevent.Photographer.Product.model.ProductUploadPhotographer;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("retrofit/POST/readcontactsPhotographer.php")
    Call<List<ProductPhotographer>> getContacts();

    @FormUrlEncoded
    @POST("retrofit/POST/addcontactPhotographer.php")
    public Call<ProductPhotographer> insertUser(
            @Field("name") String name,
            @Field("email") String email);

    @FormUrlEncoded
    @POST("retrofit/POST/editcontactPhotographer.php")
    public Call<ProductPhotographer> editUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email);


    @FormUrlEncoded
    @POST("retrofit/POST/deletecontactPhotographer.php")
    Call<ProductPhotographer> deleteUser(
            @Field("id") int id
    );


    //for live data search
    @GET("productPhotographer.php")
    Call<List<ProductPhotographer>> getProduct(
            @Query("item_type") String item_type,
            @Query("key") String keyword,
            @Query("cell") String cell
    );

    //for live data search
    @GET("all_productsPhotographer.php")
    Call<List<ProductPhotographer>> getAllProduct(
            @Query("item_type") String item_type,
            @Query("key") String keyword,
            @Query("cell") String cell
    );


    //for upload image and info
    @Multipart
    @POST("upload_productPhotographer.php")
    Call<ProductUploadPhotographer> uploadFile(@Part MultipartBody.Part file,
                                               @Part(Constant.KEY_FILE) RequestBody name,
                                               @Part(Constant.KEY_PRODUCT_NAME) RequestBody product_name,
                                               @Part(Constant.KEY_CATEGORY) RequestBody category,
                                               @Part(Constant.KEY_QUANTITY) RequestBody stock,
                                               @Part(Constant.KEY_PRICE) RequestBody price,
                                               @Part(Constant.KEY_DESCRIPTION) RequestBody description,
                                               @Part(Constant.KEY_PHOTOGRAPHER_CELL) RequestBody photographer_cell);


}