package com.example.comeya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MapsMyRest extends Fragment {
    float lat;
    float lon;
    private static final String TAG = "MapsAtivity";
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            cordenadas();
            final LatLng sydney = new LatLng(lat, lon);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Rest"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    private void cordenadas() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST + RestData.ID_AUX_REST, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject obj = response.getJSONObject(0);
                    lat= Float.parseFloat((obj.getString("lat")));
                    lon= Float.parseFloat((obj.getString("lon")));
                    Log.d(TAG,"este es"+lat);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps_my_rest, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}