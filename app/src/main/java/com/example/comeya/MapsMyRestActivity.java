package com.example.comeya;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
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

public class MapsMyRestActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String lat,lon;
    private double latitud,longitud;
    private static final String TAG = "MapsAtivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_my_rest);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        cordenadas();
        latitud=Double.parseDouble(lat);
        longitud=Double.parseDouble(lon);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng sydney = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void cordenadas() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST + RestData.ID_AUX_REST, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject obj = response.getJSONObject(0);
                    lat=(obj.getString("lat"));
                    lon=(obj.getString("lon"));
                    Log.d(TAG,"este es"+lat);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}