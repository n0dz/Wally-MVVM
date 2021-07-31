package com.nodz.galleryimages.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class PictureDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Pictures>> pictureLiveData = new MutableLiveData<>() ;
    private Application application;

    public PictureDataSourceFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public DataSource create() {
        PictureDataSource pictureDataSource = new PictureDataSource(application);
        pictureLiveData.postValue(pictureDataSource);
        return pictureDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Pictures>> getPictureLiveData() {
        return pictureLiveData;
    }
}
