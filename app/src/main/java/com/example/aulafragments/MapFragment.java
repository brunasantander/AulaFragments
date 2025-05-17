package com.example.aulafragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aulafragments.user.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private TextView tvLong, tvLat;
    private MapView mapView;
    private GoogleMap googleMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private double latitude = 0.0, longitude = 0.0;

    public MapFragment() {}

    public static MapFragment newInstance(User user) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            User user = (User) getArguments().getSerializable("user");
            if (user != null) {
                latitude = Double.parseDouble(user.location.coordinates.latitude);
                longitude = Double.parseDouble(user.location.coordinates.longitude);
            }
        }
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvLong = view.findViewById(R.id.tvLong);
        tvLat = view.findViewById(R.id.tvLat);
        mapView = view.findViewById(R.id.mapView);
        mapView.setClickable(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        tvLat.setText(String.valueOf(latitude));
        tvLong.setText(String.valueOf(longitude));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMinZoomPreference(12);

        UiSettings settings = this.googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled(true);

        LatLng location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(location).title("Localização do usuário"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override public void onResume() { super.onResume(); mapView.onResume(); }
    @Override public void onStart() { super.onStart(); mapView.onStart(); }
    @Override public void onStop() { super.onStop(); mapView.onStop(); }
    @Override public void onDestroy() { super.onDestroy(); mapView.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
}
