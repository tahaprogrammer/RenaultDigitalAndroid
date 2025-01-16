package com.maroc.renaultdigitalandroid.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.maroc.renaultdigitalandroid.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private FragmentProfileBinding binding;
    private Context mContext;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        mContext = getContext();
        mActivity = getActivity();

        return binding.getRoot();
    }
}
