package com.example.comeya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.PedidoData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.comeya.utils.EndPoints.MAPVIEW_BUNDLE_KEY;

public class Confirmar_pedido extends AppCompatActivity implements OnMapReadyCallback {
    Context context;
    RecyclerView recyclerViewmenu;
    LinearLayoutManager lnlist;
    confirmarpedido_adapter adaptadorlist;
    MapView mi_hubi;
    private Button pedido;
    private static final String TAG = "MapsAtivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        recyclerViewmenu = findViewById(R.id.listConfirmar);
        lnlist = new GridLayoutManager(context, 1);
        adaptadorlist = new confirmarpedido_adapter(context);
        recyclerViewmenu.setLayoutManager(lnlist);
        recyclerViewmenu.setAdapter(adaptadorlist);
        mi_hubi = findViewById(R.id.confirmarP_mapa);
        pedido = (Button) findViewById(R.id.confirmarP_pedir);
        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    crear_fac();
            }
        });
        obtenerlist();
        initGoogleMap(savedInstanceState);

    }

    private void crear_fac() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        RequestParams params=new RequestParams();
        params.add("lati", PedidoData.PEDIDO_LAT);
        params.add("longi",PedidoData.PEDIDO_LONG);
        Log.d(TAG,"onMapLongClick"+EndPoints.SERV_POST_FAC+UserDataServer.ID+"&toker="+MenuData.TOKER_ORDER);
        client.post(EndPoints.SERV_POST_FAC+MenuData.TOKER_ORDER+"&id="+UserDataServer.ID,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "" + errorResponse, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onMapLongClick"+errorResponse);
            }
        });
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mi_hubi.onCreate(mapViewBundle);
        mi_hubi.getMapAsync(this);
    }

    private void obtenerlist() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GET_ORDER + MenuData.TOKER_ORDER, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        adaptadorlist.add(new confirmarpedidoView(obj.getString("nombre_menu"), obj.getString("cantidad"),
                                obj.getString("pago_total"), obj.getString("_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mi_hubi.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mi_hubi.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mi_hubi.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mi_hubi.onStop();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("marker"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        LocationManager locationManager = (LocationManager) Confirmar_pedido.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions=new MarkerOptions()
                        .position(miUbicacion)
                        .title(String.valueOf(location));
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion,16));
                PedidoData.PEDIDO_LAT= String.valueOf(miUbicacion.latitude);
                PedidoData.PEDIDO_LONG= String.valueOf(miUbicacion.longitude);
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
    public void onPause(){
        super.onPause();
        mi_hubi.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mi_hubi.onDestroy();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mi_hubi.onLowMemory();
    }

}