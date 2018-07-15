package com.bad_coders.moneyconverter.Ui;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.Manifest;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.MapsViewModel;
import com.bad_coders.moneyconverter.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created on 17.12.2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = MapsFragment.class.getSimpleName();
    private int requestCode;
    private OnPermissionResultListener mPermissionResultListener;
    private MapsViewModel mMapsViewModel;

    public interface OnPermissionResultListener {
        void granted();
        void denied();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mPermissionResultListener.granted();
            else mPermissionResultListener.denied();
        }
    }

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMapsViewModel = new MapsViewModel(this);
        FragmentMapsBinding mapsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps,
                container, false);
        return mapsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapsViewModel.onViewCreated();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapsViewModel.onMapReady(googleMap);
    }

    public void requestPermission(String permission, int requestCode, OnPermissionResultListener listener) {
        this.requestCode = requestCode;
        mPermissionResultListener = listener;
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {permission}, requestCode);
        }
        else mPermissionResultListener.granted();
    }
}
