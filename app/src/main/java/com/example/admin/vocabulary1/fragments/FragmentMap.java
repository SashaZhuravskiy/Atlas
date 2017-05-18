package com.example.admin.vocabulary1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import com.example.admin.vocabulary1.R;


public class FragmentMap extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;

    public FragmentMap() { }

    public static FragmentMap newInstance(String param1, String param2) {
        FragmentMap fragment = new FragmentMap();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMapIfNeeded();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);}}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;}

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);}


    private void setUpMapIfNeeded()
    {
        if (mMap != null)
            return;
        ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                mMap = googleMap;
                if (mMap == null)
                    return;
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
                {
                    @Override
                    public void onMapClick(LatLng latLng)
                    {
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                });
                mMap.setMyLocationEnabled(true);
            }
        });
    }

}
