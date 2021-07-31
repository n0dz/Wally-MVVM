package com.nodz.galleryimages.Network;

import com.nodz.galleryimages.Model.PictureResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/")
    Call<PictureResponse> getPictures(@Query("key") String key, @Query("q")String query);

    @GET("api/")
    Call<PictureResponse> getPicturesWithPaging(@Query("key") String key, @Query("q")String query,@Query("page") int page);


}
