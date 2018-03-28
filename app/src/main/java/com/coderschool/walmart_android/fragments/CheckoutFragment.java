package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.activities.MainActivity;

@SuppressLint("ValidFragment")
public class CheckoutFragment extends Fragment {

    private MainActivity mainActivity;

    public CheckoutFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_checkkout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        if (mainActivity.getSupportActionBar() != null) {
            mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mainActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_checkout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                Fragment thankFragment = new ThankFragment(mainActivity);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, thankFragment);
                ft.commit();
                return true;

            case android.R.id.home:
                getFragmentManager().popBackStack(CheckoutFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
            default:
                return false;
        }
    }
}

