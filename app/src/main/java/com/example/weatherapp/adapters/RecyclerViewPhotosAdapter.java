package com.example.weatherapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewPhotosAdapter extends RecyclerView.Adapter<RecyclerViewPhotosAdapter.PhotosViewHolder> {
    private List<String> mPhotosUrlList;

    public static class PhotosViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewCityPhoto;
        public PhotosViewHolder(View v) {
            super(v);

            mImageViewCityPhoto = v.findViewById(R.id.image_city_photo);
        }
    }

    public RecyclerViewPhotosAdapter(List<String> photosUrlList) {
        mPhotosUrlList = photosUrlList;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);

        PhotosViewHolder vh = new PhotosViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder photosViewHolder, int i) {
        String url = mPhotosUrlList.get(i);
        Picasso.get().load(url).fit().into(photosViewHolder.mImageViewCityPhoto);
    }

    @Override
    public int getItemCount() {
        return mPhotosUrlList.size();
    }
}
