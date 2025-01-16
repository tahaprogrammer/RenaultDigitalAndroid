package com.maroc.renaultdigitalandroid.dialogs.cars;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.maroc.renaultdigitalandroid.R;
import com.maroc.renaultdigitalandroid.databinding.DialogCarModelDetailBinding;
import com.maroc.renaultdigitalandroid.entities.CarEntity;

public class JobDetailDialog {

    private static final String TAG = "JobDetailDialog";
    private final DialogCarModelDetailBinding binding;
    private final Activity mActivity;
    private final Context mContext;
    private final CarEntity carEntity;
    private BottomSheetDialog bottomSheetDialog;

    public JobDetailDialog(Activity mActivity, Context mContext, CarEntity carEntity) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.carEntity = carEntity;
        binding = DialogCarModelDetailBinding.inflate(LayoutInflater.from(mContext));
        this.initViews();
        this.initListener();
    }

    public static JobDetailDialog getInstance(Activity mActivity, Context mContext, CarEntity carEntity) {
        return new JobDetailDialog(mActivity, mContext, carEntity);
    }

    private void initViews() {
        bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.getBehavior().setDraggable(false);
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.setCancelable(true);
        if (bottomSheetDialog.getWindow() != null) {
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
        }
        binding.textViewTitle.setText(carEntity.getTitle());
        binding.textViewBody.setText(carEntity.getDescription());
        this.startLoading();
        Glide.with(mContext)
                .load(carEntity.getImageUrl())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        stopLoading();
                        binding.imageViewHeader.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        //No Need
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        stopLoading();
                        Glide.with(mContext)
                                .load(R.drawable.img_no_image_found)
                                .into(binding.imageViewHeader);
                    }
                });


    }

    private void initListener() {
        binding.imageViewBack.setOnClickListener(v -> dismiss());
        binding.floatingActionButtonShare.setOnClickListener(v -> {
            Toast.makeText(mActivity, "Demo App", Toast.LENGTH_SHORT).show();
        });
    }

    private void startLoading() {
        binding.imageViewHeaderContainer.setVisibility(ViewGroup.GONE);
        binding.shimmerViewContainer.setVisibility(ViewGroup.VISIBLE);
        binding.shimmerViewContainer.startShimmer();
    }

    private void stopLoading() {
        binding.imageViewHeaderContainer.setVisibility(ViewGroup.VISIBLE);
        binding.shimmerViewContainer.setVisibility(ViewGroup.GONE);
        binding.shimmerViewContainer.stopShimmer();
    }

    public void show() {
        if (!bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }
}
