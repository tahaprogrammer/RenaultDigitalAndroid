package com.maroc.renaultdigitalandroid.adapters.cars;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.maroc.renaultdigitalandroid.R;
import com.maroc.renaultdigitalandroid.databinding.ItemCarBinding;
import com.maroc.renaultdigitalandroid.entities.CarEntity;
import com.maroc.renaultdigitalandroid.interfaces.list.OnItemClickingListener;

import java.util.ArrayList;

public class ListingCarModelsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ListingJobsAdapter";

    private final Context mContext;
    public OnItemClickingListener onItemClickingListener;
    private ArrayList<CarEntity> carEntities = new ArrayList<>();

    public ListingCarModelsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static class RecyclerHolderItem extends RecyclerView.ViewHolder {
        ItemCarBinding binding;

        public RecyclerHolderItem(
                @NonNull ItemCarBinding binding, OnItemClickingListener onItemClickingListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> {
                if (onItemClickingListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onItemClickingListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerHolderItem(
                ItemCarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onItemClickingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerHolderItem holderItem = (RecyclerHolderItem) holder;
        CarEntity carEntity = carEntities.get(position);
        holderItem.binding.textViewTitle.setText(carEntity.getTitle());
        startLoading(holderItem);

        Glide.with(mContext)
                .load(carEntity.getImageUrl())
                .placeholder(R.drawable.img_no_image_found)
                .error(R.drawable.img_no_image_found)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holderItem.binding.imageViewHeader.setImageDrawable(resource);
                        stopLoading(holderItem);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        holderItem.binding.imageViewHeader.setImageDrawable(errorDrawable);
                        stopLoading(holderItem);
                        Log.e(TAG, "onError: Failed to load image");
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // No Need
                    }
                });
    }

    @Override
    public int getItemCount() {
        return carEntities == null ? 0 : carEntities.size();
    }

    public void setOnItemClickingListener(OnItemClickingListener onItemClickingListener) {
        this.onItemClickingListener = onItemClickingListener;
    }

    private void startLoading(RecyclerHolderItem holder) {
        holder.binding.imageViewHeader.setVisibility(ViewGroup.GONE);
        holder.binding.shimmerViewContainer.setVisibility(ViewGroup.VISIBLE);
        holder.binding.shimmerViewContainer.startShimmer();
    }

    private void stopLoading(RecyclerHolderItem holder) {
        holder.binding.imageViewHeader.setVisibility(ViewGroup.VISIBLE);
        holder.binding.shimmerViewContainer.setVisibility(ViewGroup.GONE);
        holder.binding.shimmerViewContainer.stopShimmer();
    }

    public void updateList(ArrayList<CarEntity> carEntities) {
        this.carEntities = carEntities;
        notifyDataSetChanged();
    }
}