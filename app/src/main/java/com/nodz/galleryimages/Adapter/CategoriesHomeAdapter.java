package com.nodz.galleryimages.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nodz.galleryimages.Activity.ImageResultsActivity;
import com.nodz.galleryimages.Model.CategoriesHome;
import com.nodz.galleryimages.Network.RetrofitInstance;
import com.nodz.galleryimages.R;
import com.nodz.galleryimages.Utils.CategoriesConstants;

import java.util.List;

public class CategoriesHomeAdapter extends RecyclerView.Adapter<CategoriesHomeAdapter.CategoriesVH> {

    List<CategoriesHome> list;
    Context context;

    public CategoriesHomeAdapter(List<CategoriesHome> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesHomeAdapter.CategoriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_home_items, parent, false);
        return new CategoriesVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHomeAdapter.CategoriesVH holder, int position) {
        CategoriesHome itemCategory = list.get(position);

        holder.textView.setText(itemCategory.getCategory());
        holder.cat_item_back_img.setImageResource(itemCategory.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageResultsActivity.class);
                intent.putExtra(CategoriesConstants.CAT_KEY, holder.textView.getText().toString());
                intent.putExtra("Type", 2);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoriesVH extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView cat_item_back_img;

        public CategoriesVH(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.category_item_home);
            cat_item_back_img = itemView.findViewById(R.id.cat_item_img_id);
        }
    }
}
