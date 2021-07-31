package com.nodz.galleryimages.Adapter;

import android.app.Dialog;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nodz.galleryimages.Model.SavedImgModel;
import com.nodz.galleryimages.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SavedImagesAdapter extends RecyclerView.Adapter<SavedImagesAdapter.SavedVH> {
    Context context;
    List<File> list;

    public SavedImagesAdapter(Context context, List<File> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SavedVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_saved_img, parent, false);
        return new SavedVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedImagesAdapter.SavedVH holder, int position) {

        Log.i("onBindViewHolder: ", list.get(position).toString());
        String imgLoc = list.get(position).toString();

        Glide.with(context)
                .load(imgLoc)
                .into(holder.img);

        // .override(WindowManager.LayoutParams.MATCH_PARENT,300)

        holder.imgLocation.setText(imgLoc);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.show_img_dia);
                dialog.show();

                FloatingActionButton btnShare = dialog.findViewById(R.id.btn_save_img);

                ImageView imageView = dialog.findViewById(R.id.image_preview);

                Glide.with(context)
                        .load(imgLoc)
                        .into(imageView);

                btnShare.setVisibility(View.VISIBLE);

                btnShare.setImageResource(R.drawable.ic_baseline_share_24);

                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap icon = drawable.getBitmap();*/

                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();

                        StrictMode.setVmPolicy(builder.build());

                        final Intent shareIntent = new Intent(Intent.ACTION_SEND);

                        shareIntent.setType("image/jpg");

                         File photoFile = new File(imgLoc);

                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));

                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        context.startActivity(Intent.createChooser(shareIntent, "Share image using"));
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SavedVH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView imgLocation;

        public SavedVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView_item);
            imgLocation = itemView.findViewById(R.id.tv_tags);
        }
    }
}
