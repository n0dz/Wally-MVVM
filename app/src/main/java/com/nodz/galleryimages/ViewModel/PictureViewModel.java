package com.nodz.galleryimages.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.nodz.galleryimages.Model.PictureDataSourceFactory;
import com.nodz.galleryimages.Model.Pictures;

public class PictureViewModel extends AndroidViewModel {

    LiveData<PagedList<Pictures>> picturePagedList;
    LiveData<PageKeyedDataSource<Integer,Pictures>> sourceLiveData;


    public PictureViewModel(@NonNull Application application) {
        super(application);

        PictureDataSourceFactory factory = new PictureDataSourceFactory(application);
        sourceLiveData = factory.getPictureLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(40)
                .build();

        picturePagedList = (new LivePagedListBuilder(factory,config)).build();
    }

    public LiveData<PagedList<Pictures>> getPicturePagedList() {
        return picturePagedList;
    }
}
