package com.nodz.galleryimages.Utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.nodz.galleryimages.Activity.SearchImageActivity;
import com.nodz.galleryimages.R;

public class CheckConnection {


    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {

             if(SearchImageActivity.noInternet != null && SearchImageActivity.noInternet.isShowing()) {
                 SearchImageActivity.loading.setVisibility(View.GONE);
                 SearchImageActivity.noInternet.dismiss();
             }

            }else{SearchImageActivity.showNoInternetDialog(context);}

        }
        return false;
    }

}
