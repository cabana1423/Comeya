package com.example.comeya;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsRest extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private static final String TAG = "MapsAtivity";
    private GoogleMap mMap;
    private Geocoder geocoder;
    private  Location CurrentLocation;
    private static boolean solo_uno= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_rest);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
        getLocalization();
    }

    private void getLocalization() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        LocationManager locationManager = (LocationManager) MapsRest.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions=new MarkerOptions()
                        .position(miUbicacion)
                        .title(String.valueOf(location));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion,16));
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
       }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if(solo_uno==true){
            Log.d(TAG,"onMapLongClick"+latLng.toString());
            try {
                List<Address> addresses= geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                if(addresses.size()>0){
                    Address address=addresses.get(0);
                    String streetAddress=address.getAddressLine(0);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(streetAddress)
                            .draggable(true));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            solo_uno=false;
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d(TAG,"onMarkerDragStart");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG,"onMarkerDrag");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG,"onMarkerDragEnd");
        LatLng latLng=marker.getPosition();
        try {
            List<Address> addresses= geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(addresses.size()>0){
                Address address=addresses.get(0);
                String streetAddress=address.getAddressLine(0);
                marker.setTitle(streetAddress);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



/*
try {
            List<Address> addresses=geocoder.getFromLocationName("london",1);
            if(addresses.size()>0){
                Address address=addresses.get(0);
                LatLng london=new LatLng(address.getLatitude(),address.getLongitude());
                    MarkerOptions markerOptions=new MarkerOptions()
                            .position(london)
                            .title(address.getLocality());
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london,16));
            }
        }catch (IOException e){
            e.printStackTrace();
        }*/