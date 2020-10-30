package com.example.comeya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.FacData;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.comeya.utils.EndPoints.MAPVIEW_BUNDLE_KEY;

public class Ventana_vista_rest extends AppCompatDialogFragment implements OnMapReadyCallback {
    private TextView nombre,propietario,nit,telefono,direccion;
    private ImageView foto_rest;
    private MapView mapa;
    GoogleMap nMap;
    double lat,lon;
    //String lati,longi;
    //private MapView mapa;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ventana_vista_rest,null);
        builder.setView(view)
                .setTitle("Vista previa restaurante")
                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        nombre =view.findViewById(R.id.VistaRest_nombre);
        propietario =view.findViewById(R.id.VistaRest_propietario);
        nit =view.findViewById(R.id.VistaRest_nit);
        telefono =view.findViewById(R.id.VistaRest_telefono);
        direccion =view.findViewById(R.id.VistaRest_direccion);
        foto_rest =view.findViewById(R.id.Vistarest_img);
        mapa =view.findViewById(R.id.VistaRest_mapa);
        llenarVista();
        initGoogleMap(savedInstanceState);
        return builder.create();
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapa.onCreate(mapViewBundle);
        mapa.getMapAsync(this);
    }

    private void llenarVista() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST+ MenuData.ID_AUX_rest,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(0);
                        if(isAdded()) {
                        nombre.setText(obj.getString("nombre_rest"));
                        propietario.setText("Propietario: "+obj.getString("propietario"));
                        nit.setText("NIT: "+obj.getString("nit"));
                        telefono.setText("Telefono: "+obj.getString("telefono"));
                        direccion.setText("Direccion"+obj.getString("calle"));
                        String lati=obj.getString("lat");
                        String longi=obj.getString("lon");
                        llenarMapa(lati,longi);
                        Glide.with(getActivity()).load(obj.getString("foto_lugar")).centerCrop().into(foto_rest);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
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
        mapa.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapa.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapa.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapa.onStop();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        nMap=googleMap;
    }

    private void llenarMapa(String lati, String longi) {
        try {
            lat= Double.parseDouble(lati);
            lon= Double.parseDouble(longi);
            LatLng direccion=new LatLng(lat,lon);
            Marker mimarker = nMap.addMarker(new MarkerOptions()
                    .title("Este es el Lugar")
                    .position(direccion)
            );
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(direccion,16));

        }catch (Exception e){
            Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        mapa.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapa.onDestroy();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapa.onLowMemory();
    }
}
