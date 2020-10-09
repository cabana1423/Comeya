package com.example.comeya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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

public class Gestionar_rest extends Fragment /*implements OnMapReadyCallback */{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    FloatingActionButton editar_rest, eliminar_rest;
    TextView nombre_rest, propietario, nit, telefono, direccion;
    ImageView fotoMyrest;
    Button map;
    //Fragment mapaMyrest;
    private static final String TAG = "MapsAtivity";
    // String lati,longi;

    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gestionarrest, container, false);
        nombre_rest = (TextView) root.findViewById(R.id.GestionarRest_nombre);
        propietario = root.findViewById(R.id.GestionarRest_propietario);
        nit = root.findViewById(R.id.GestionarRest_nit);
        telefono = (TextView) root.findViewById(R.id.GestionarRest_telefono);
        direccion = (TextView) root.findViewById(R.id.GestionarRest_direccion);
        fotoMyrest = root.findViewById(R.id.GestionarRest_img);
        grupoBotones = root.findViewById(R.id.groupbotones_editrest);
        editar_rest = root.findViewById(R.id.groupbotonactualizar_rest);
        editar_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        eliminar_rest = root.findViewById(R.id.groupbotoneliminar_rest);
        eliminar_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elimar();
            }
        });
        /*map=root.findViewById(R.id.button1234);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapsMyRestActivity.class ));
            }
        });*/
        vistarest();
        //mapaMyrest = root.findViewById(R.id.GestionarRest_mapa);
        //initGoogleMap(savedInstanceState);
        return root;
    }

    private void elimar() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.delete(EndPoints.SERV_REST + RestData.ID_AUX_REST, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }
        });
    }

    private void vistarest() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST + RestData.ID_AUX_REST, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject obj = response.getJSONObject(0);
                    nombre_rest.setText(obj.getString("nombre_rest"));
                    propietario.setText(obj.getString("propietario"));
                    nit.setText(obj.getString("nit"));
                    telefono.setText(obj.getString("telefono"));
                    direccion.setText(obj.getString("calle"));
                    Glide.with(getActivity()).load(obj.getString("foto_lugar")).centerCrop().into(fotoMyrest);
                    MenuData.ID_AUX_IMGMENU = obj.getString("foto_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openVentana() {
        Ventana_editar_rest ventana_editar_rest = new Ventana_editar_rest();
        ventana_editar_rest.show(getActivity().getSupportFragmentManager(), "example dialogo");
    }
}