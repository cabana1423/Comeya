package com.example.comeya;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.comeya.utils.EndPoints.MAPVIEW_BUNDLE_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gestionar_rest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gestionar_rest extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Gestionar_rest() {
        // Required empty public constructor
    }

    public static Gestionar_rest newInstance(String param1, String param2) {
        Gestionar_rest fragment = new Gestionar_rest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FloatingActionsMenu grupoBotones;
    FloatingActionButton editar_rest,eliminar_rest;
    TextView nombre_rest,propietario,nit,telefono,direccion;
    ImageView fotoMyrest;
    MapView mapaMyrest;
    String lat,lon;

    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_gestionarrest, container, false);
        nombre_rest=(TextView) root.findViewById(R.id.GestionarRest_nombre);
        propietario=root.findViewById(R.id.GestionarRest_propietario);
        nit=root.findViewById(R.id.GestionarRest_nit);
        telefono=(TextView) root.findViewById(R.id.GestionarRest_telefono);
        direccion=(TextView) root.findViewById(R.id.GestionarRest_direccion);
        fotoMyrest=root.findViewById(R.id.GestionarRest_img);
        grupoBotones=root.findViewById(R.id.groupbotones_editrest);
        editar_rest =root.findViewById(R.id.groupbotonactualizar_rest);
        editar_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        eliminar_rest=root.findViewById(R.id.groupbotoneliminar_rest);
        eliminar_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ":( eliminaste este rest", Toast.LENGTH_LONG).show();
            }
        });
        mapaMyrest= root.findViewById(R.id.GestionarRest_mapa);
        vistarest();
        initGoogleMap(savedInstanceState);
        return root;
    }
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapaMyrest.onCreate(mapViewBundle);
        mapaMyrest.getMapAsync(this);
    }

    private void vistarest() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST+ RestData.ID_AUX_REST,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                    try {
                        JSONObject obj =response.getJSONObject(0);
                        nombre_rest.setText(obj.getString("nombre_rest"));
                        propietario.setText(obj.getString("propietario"));
                        nit.setText(obj.getString("nit"));
                        telefono.setText(obj.getString("lat"));
                        direccion.setText(obj.getString("calle"));
                        Glide.with(getActivity()).load(obj.getString("foto_lugar")).centerCrop().into(fotoMyrest);
                        lat=obj.getString("lat");
                        //Toast.makeText(context,lat,Toast.LENGTH_SHORT).show();
                        lon=obj.getString("lon");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void openVentana() {
        Ventana_editar_rest ventana_editar_rest=new Ventana_editar_rest();
        ventana_editar_rest.show(getActivity().getSupportFragmentManager(),"example dialogo");
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapaMyrest.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapaMyrest.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapaMyrest.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapaMyrest.onStop();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("marker"));
    }
    @Override
    public void onPause(){
        super.onPause();
        mapaMyrest.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapaMyrest.onDestroy();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapaMyrest.onLowMemory();
    }
}