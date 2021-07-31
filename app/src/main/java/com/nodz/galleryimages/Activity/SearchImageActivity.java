package com.nodz.galleryimages.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nodz.galleryimages.Adapter.CategoriesHomeAdapter;
import com.nodz.galleryimages.Model.CategoriesHome;
import com.nodz.galleryimages.R;
import com.nodz.galleryimages.Utils.CategoriesConstants;
import com.nodz.galleryimages.Utils.CheckConnection;
import com.nodz.galleryimages.Utils.MyPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchImageActivity extends AppCompatActivity {

    public static Dialog noInternet;
    public static ProgressBar loading;
    TextInputEditText editText;
    private int emptyTap = 0;
    private RecyclerView recyclerView;
    private CategoriesHomeAdapter adapter;


    private BroadcastReceiver mCheckInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckConnection.isConnectingToInternet(SearchImageActivity.this);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_image);

        editText = findViewById(R.id.edit_text_query);
/*        MyPreferences prefs = new MyPreferences(SearchImageActivity.this);
        prefs.saveData(this,"Key",editText.getText().toString());*/
        ImageView btn = findViewById(R.id.search_button);

        recyclerView = findViewById(R.id.home_recyclerView);
        loadDataIntoRV();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    Intent in = new Intent(SearchImageActivity.this, ImageResultsActivity.class);
                    in.putExtra("query", editText.getText().toString().trim());
                    in.putExtra("Type", 1);
                    startActivity(in);
                } else {
                    emptyTap++;
                    Toast to;
                    to = Toast.makeText(SearchImageActivity.this, "Please Enter some text", Toast.LENGTH_SHORT);
                    to.show();
                    if(emptyTap>2){
                        to = Toast.makeText(SearchImageActivity.this, "Stop Spamming the button dude!!!!", Toast.LENGTH_SHORT);
                        to.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        to.show();
                    }

                }
            }
        });
        registerReceiver(mCheckInternet, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    private void loadDataIntoRV() {
        List<CategoriesHome> list = new ArrayList<>();
        list.add(new CategoriesHome(CategoriesConstants.CAT_A, R.drawable.ic_nature));
        list.add(new CategoriesHome(CategoriesConstants.CAT_B, R.drawable.ic_science_24));
        list.add(new CategoriesHome(CategoriesConstants.CAT_C, R.drawable.ic_education));
        list.add(new CategoriesHome(CategoriesConstants.CAT_D, R.drawable.ic_feeling));
        list.add(new CategoriesHome(CategoriesConstants.CAT_E, R.drawable.ic_health));
        list.add(new CategoriesHome(CategoriesConstants.CAT_F, R.drawable.ic_religion));
        list.add(new CategoriesHome(CategoriesConstants.CAT_G, R.drawable.ic_places));
        list.add(new CategoriesHome(CategoriesConstants.CAT_H, R.drawable.ic_travel));
        list.add(new CategoriesHome(CategoriesConstants.CAT_I ,R.drawable.ic_sports));
        list.add(new CategoriesHome(CategoriesConstants.CAT_J, R.drawable.ic_computer));
        list.add(new CategoriesHome(CategoriesConstants.CAT_K, R.drawable.ic_cat_item_back));
        list.add(new CategoriesHome(CategoriesConstants.CAT_L, R.drawable.ic_cat_item_back));
        list.add(new CategoriesHome(CategoriesConstants.CAT_M, R.drawable.ic_cat_item_back));


        adapter = new CategoriesHomeAdapter(list, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public static void showNoInternetDialog(Context context) {
        noInternet = new Dialog(context);
        noInternet.setContentView(R.layout.dialog_network);
        noInternet.setCancelable(false);
        noInternet.show();

        loading = noInternet.findViewById(R.id.loading_no_internet);

        loading.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.img_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saved_imgs)
            startActivity(new Intent(SearchImageActivity.this, SavedImagesActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mCheckInternet);

    }
}

