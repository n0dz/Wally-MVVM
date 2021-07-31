package com.nodz.galleryimages.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nodz.galleryimages.Model.Pictures;
import com.nodz.galleryimages.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.List;

public class PictureAdapter extends PagedListAdapter<Pictures, PictureAdapter.PictureViewholder> {
    private static DiffUtil.ItemCallback<Pictures> CALLBACK = new DiffUtil.ItemCallback<Pictures>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pictures oldItem, @NonNull Pictures newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Pictures oldItem, @NonNull Pictures newItem) {
            return true;
        }
    };
    Context context;
    String imgUrl;

    public PictureAdapter(Context context) {
        super(CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public PictureViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PictureViewholder(LayoutInflater.from(context).inflate(R.layout.sample_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewholder holder, int position) {
        Pictures pictures = getItem(position);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.progress_animation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                 .dontTransform();

        imgUrl = pictures.getFullHDURL() == null ? pictures.getLargeImageURL():pictures.getFullHDURL();

        Log.i( "Available Resolution: ",pictures.getImageURL() == null ? pictures.getLargeImageURL():pictures.getImageURL());
        if (pictures != null) {
            Glide.with(context)
                    .load(pictures.getLargeImageURL())
                    .apply(options)
                    .centerCrop()
                    .into(holder.image);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog imgDialog = new Dialog(context);
                imgDialog.setContentView(R.layout.show_img_dia);

                FloatingActionButton btnSave = imgDialog.findViewById(R.id.btn_save_img);
                btnSave.setVisibility(View.VISIBLE);

                Glide.with(context)
                        .load(pictures.getLargeImageURL())//.override(ViewGroup.LayoutParams.MATCH_PARENT,500)
                        .into((ImageView) imgDialog.findViewById(R.id.image_preview));

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BitmapDrawable drawable = (BitmapDrawable) holder.image.getDrawable();
                        Bitmap bmp = drawable.getBitmap();

                        //File file = Environment.getExternalStorageDirectory();
                        //File dir = new File(file.getAbsolutePath() + "/IMAGES");

                        /*if (!dir.exists())
                            dir.mkdirs();*/

                        File dir = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + "Wally");
                        } else {
                            dir = new File(Environment.getExternalStorageDirectory() + "/" + "Wally");
                        }
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        Log.i("onClick: ", dir.getAbsolutePath());
                        saveImage(dir.getAbsolutePath(), bmp);
                    }

                });


                imgDialog.show();

            }
        });

    }



    public void saveImage(String dir, Bitmap b) {

        File outFile = new File(dir, "img_" + System.currentTimeMillis() + ".jpg");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outFile);
            b.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Downloaded  :" + dir, Toast.LENGTH_LONG).show();

        Log.i("PATH :", dir);

    }

    public class PictureViewholder extends RecyclerView.ViewHolder {
        ImageView image;//TextView tags;

        public PictureViewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            //tags = itemView.findViewById(R.id.tags);
        }
    }
}

/*                String[] choiceItems;

                if (pictures.getFullHd() != null) {
                    choiceItems = new String[]{"Hd", "standard"};
                } else {
                    choiceItems = new String[]{"Standard"};
                }

                AlertDialog.Builder pickImage = new AlertDialog.Builder(context);
                pickImage.setTitle("Pick Image");
                pickImage.setItems(choiceItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                imgUrl = pictures.getFullHd();
                                break;
                            case 1:
                                imgUrl = pictures.getLargeImageURL();
                                break;
                        }
                    }
                }).show();
*/
