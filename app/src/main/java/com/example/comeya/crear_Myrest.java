package com.example.comeya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class crear_Myrest extends AppCompatActivity /*implements OnMapReadyCallback */{
    ImageButton loadimage, imagemap;
    Button cargarimg, crearrest;
    EditText nombre, nit, propietario, calle, telefono;
    public static String path, id_imgrest = "";

    static final int CODE_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__myrest);
        loadimage = findViewById(R.id.MyRest_imagenLoad);
        crearrest = findViewById(R.id.Myrest_Bcrear);
        nombre = findViewById(R.id.Myrest_name);
        nit = findViewById(R.id.Myrest_nit);
        propietario = findViewById(R.id.Myrest_propietario);
        calle = findViewById(R.id.Myrestcalle);
        telefono = findViewById(R.id.Myrest_telefono);
        imagemap=findViewById(R.id.Myrest_imgmapView);
        imagemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(crear_Myrest.this, MapsRest.class ));
            }
        });
        crearrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaRest();
            }
        });
        loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
                if(RestData.PATH_IMG!=null){loadimage.setImageURI(RestData.PATH_IMG);}
            }
        });
    }
    private void openVentana() {
        VentanaSelecImg ventanaSelecImg=new VentanaSelecImg();
        ventanaSelecImg.show(this.getSupportFragmentManager(),"example dialogo");
    }


    private void creaRest() {
        if (RestData.PATH_IMG != null) {
            AsyncHttpClient clientrest = new AsyncHttpClient();
            clientrest.addHeader("Authorization", UserDataServer.TOKEN);
            RequestParams params = new RequestParams();
            params.add("nombre_rest", nombre.getText().toString());
            params.add("nit", nit.getText().toString());
            params.add("propietario", propietario.getText().toString());
            params.add("calle", calle.getText().toString());
            params.add("telefono", telefono.getText().toString());
            params.add("lat", RestData.LAT_MAPMYREST);
            params.add("lon", RestData.LON_MAPMYREST);
            clientrest.post(EndPoints.SERV_REST + UserDataServer.ID + "&idi=" + UserDataServer.IDI, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    RestData.PATH_IMG=null;
                    onBackPressed();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(getApplicationContext(), errorResponse+"", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "agrega una imagen", Toast.LENGTH_SHORT).show();
        }


    }


}