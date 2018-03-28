package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.activities.MainActivity;

@SuppressLint("ValidFragment")
public class CartContainerFragment extends Fragment {

    private MainActivity mainActivity;

    public CartContainerFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fragment cartFragment = new CartFragment(mainActivity);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, cartFragment);
        ft.commit();
    }
}
