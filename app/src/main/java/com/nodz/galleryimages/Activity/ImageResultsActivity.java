package com.nodz.galleryimages.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nodz.galleryimages.Model.Pictures;
import com.nodz.galleryimages.Adapter.PictureAdapter;
import com.nodz.galleryimages.Utils.CategoriesConstants;
import com.nodz.galleryimages.ViewModel.PictureViewModel;
import com.nodz.galleryimages.R;

public class ImageResultsActivity extends AppCompatActivity {
    public static ProgressBar progressBar;
    public static String searchText;
    private RecyclerView recyclerView;
    private int WRITE_CODE = 1;
    private PictureViewModel pictureViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getIntent().getIntExtra("Type", -1) == 2) {
            searchText = getIntent().getStringExtra(CategoriesConstants.CAT_KEY);
        } else {
            searchText = getIntent().getStringExtra("query");
        }

        getSupportActionBar().setTitle("Results for "+searchText);

        recyclerView = findViewById(R.id.recyclerViewImageResults);
        progressBar = findViewById(R.id.progress_loading_image);

        getData();

        requestWritePermission();

        findViewById(R.id.fab_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void requestWritePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WRITE_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestWritePermission();
        }

    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        pictureViewModel = ViewModelProviders.of(ImageResultsActivity.this).get(PictureViewModel.class);
        PictureAdapter adapter = new PictureAdapter(ImageResultsActivity.this);
        pictureViewModel.getPicturePagedList().observe(this, new Observer<PagedList<Pictures>>() {
            @Override
            public void onChanged(PagedList<Pictures> pictures) {
                adapter.submitList(pictures);

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // ProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.img_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saved_imgs) {
            Toast.makeText(this, "ImageList", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ImageResultsActivity.this, SavedImagesActivity.class));

        }
        return super.onOptionsItemSelected(item);

    }

}