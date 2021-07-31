package com.nodz.galleryimages.Model;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.nodz.galleryimages.Activity.ImageResultsActivity;
import com.nodz.galleryimages.Activity.SearchImageActivity;
import com.nodz.galleryimages.Network.ApiService;
import com.nodz.galleryimages.Network.RetrofitInstance;
import com.nodz.galleryimages.R;
import com.nodz.galleryimages.Utils.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureDataSource extends PageKeyedDataSource<Integer, Pictures> {

    private Application application;
    private ProgressBar progressBar;

    public PictureDataSource(Application application) {
        this.application = application;
        this.progressBar = ImageResultsActivity.progressBar;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Pictures> callback) {
        //Log.i( "loadinitial: ]", ImageResultsActivity.searchText);

        ApiService apiService = RetrofitInstance.getService();

        Call<PictureResponse> call = apiService.getPicturesWithPaging(application.getApplicationContext().getString(R.string.api_key), ImageResultsActivity.searchText, 1);
        call.enqueue(new Callback<PictureResponse>() {
            @Override
            public void onResponse(Call<PictureResponse> call, Response<PictureResponse> response) {
                PictureResponse pictureResponse = response.body();

                if (pictureResponse != null && pictureResponse.getHits() != null) {
                    List<Pictures> pictures = pictureResponse.getHits();
                    progressBar.setVisibility(View.GONE);

                    //Log.i("TAG", "onResponse: " + pictures.toString());
                    callback.onResult(pictures, null, 2);
                }

            }

            @Override
            public void onFailure(Call<PictureResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Pictures> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Pictures> callback) {
        ApiService apiService = RetrofitInstance.getService();Call<PictureResponse> call = apiService.getPicturesWithPaging(application.getApplicationContext().getString(R.string.api_key), ImageResultsActivity.searchText, (int) params.key);
        //Log.i( "loadAfter: ]", ImageResultsActivity.searchText);
        call.enqueue(new Callback<PictureResponse>() {
            @Override
            public void onResponse(Call<PictureResponse> call, Response<PictureResponse> response) {
                PictureResponse pictureResponse = response.body();

                if (pictureResponse != null && pictureResponse.getHits() != null) {
                    List<Pictures> pictures = pictureResponse.getHits();
                    //Log.i("TAG", "onResponse: " + pictures.toString());
                    callback.onResult(pictures, (int) params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<PictureResponse> call, Throwable t) {

            }
        });
    }
}
