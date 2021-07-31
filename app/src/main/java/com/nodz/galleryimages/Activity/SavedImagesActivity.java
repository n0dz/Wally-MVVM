package com.nodz.galleryimages.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nodz.galleryimages.Adapter.SavedImagesAdapter;
import com.nodz.galleryimages.Model.SavedImgModel;
import com.nodz.galleryimages.R;

import java.io.File;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;

public class SavedImagesActivity extends AppCompatActivity {

    private List<File> files_list;
    private RecyclerView recyclerView;
    private SavedImagesAdapter adapter;
    private TextView txt_empty_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        getSupportActionBar().setTitle("Your Saved Images");

        txt_empty_list = findViewById(R.id.txt_empty_list);

        files_list = new ArrayList<>();

        File dir = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + "Wally");
        } else {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + "Wally");
        }
        //walkdir(dir);

        recyclerView = findViewById(R.id.saved_images_Rv);
        adapter = new SavedImagesAdapter(SavedImagesActivity.this,getImgList(dir));

        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public List<File> getImgList(File dir) {

        File[] listFile;
        listFile = dir.listFiles();

        if (listFile != null) {
            txt_empty_list.setVisibility(View.GONE);
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getImgList(listFile[i]);
                } else {
                    if (listFile[i].getName().toLowerCase().endsWith(".jpg")){
                        files_list.add(listFile[i]);

                        Log.i( "Directories: ",listFile[i].toString());
                    }
                }
            }
        } else {txt_empty_list.setVisibility(View.VISIBLE);}
        return files_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saved_img_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_wall:
                startActivity(new Intent(SavedImagesActivity.this, SearchImageActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

}
}