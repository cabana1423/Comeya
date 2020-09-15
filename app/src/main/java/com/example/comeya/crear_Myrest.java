package com.example.comeya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

import static com.example.comeya.utils.EndPoints.MAPVIEW_BUNDLE_KEY;

public class crear_Myrest extends AppCompatActivity /*implements OnMapReadyCallback */{
    ImageButton loadimage, imagemap;
    Button cargarimg, crearrest;
    EditText nombre, nit, propietario, calle, telefono;
    private MapView mapaMyrest;
    public static String path, id_imgrest = "";

    static final int CODE_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__myrest);
        loadimage = findViewById(R.id.MyRest_imagenLoad);
        cargarimg = findViewById(R.id.Myrest_Bcargar);
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
        //mapaMyrest = findViewById(R.id.Myrest_mapView);
        //initGoogleMap(savedInstanceState);

        loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarimg();
            }
        });
        cargarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar();
            }
        });
        loadimage.setVisibility(View.INVISIBLE);
        if (permission()) {
            loadimage.setVisibility(View.VISIBLE);
        }
        crearrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaRest();
            }

        });
    }

    /*private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapaMyrest.onCreate(mapViewBundle);
        mapaMyrest.getMapAsync(this);
    }*/

    private void enviar() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        RequestParams req = new RequestParams();
        if (path != null) {
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
            File file = new File(path);
            try {
                req.put("img", file);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error al enviar la imagen al sevidor la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "ingrese una imagen", Toast.LENGTH_SHORT).show();
        }
        client.post(EndPoints.SERVICE_UPIMGREST + UserDataServer.ID, req, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.has("_id"))
                        UserDataServer.IDI = response.getString("_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void creaRest() {
        if (path != null) {
            AsyncHttpClient clientrest = new AsyncHttpClient();
            clientrest.addHeader("Authorization", UserDataServer.TOKEN);
            RequestParams params = new RequestParams();
            params.add("nombre_rest", nombre.getText().toString());
            params.add("nit", nit.getText().toString());
            params.add("propietario", propietario.getText().toString());
            params.add("calle", calle.getText().toString());
            params.add("telefono", telefono.getText().toString());
            clientrest.post(EndPoints.SERV_REG_REST + UserDataServer.ID + "&idi=" + UserDataServer.IDI, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
            //startActivity(new Intent(crear_Myrest.this, add.class ));
        } else {
            Toast.makeText(getApplicationContext(), "agrega una imagen", Toast.LENGTH_SHORT).show();
        }


    }

    private void cargarimg() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecione la imagen"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path1 = data.getData();
            path = getRealPath(path1);
            loadimage.setImageURI(path1);
        }
    }

    String getRealPath(Uri uri) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int i = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(i);
            cursor.close();
        }
        return path;
    }

    private boolean permission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSION);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION == requestCode) {
            if (permissions.length == 3) {
                cargarimg.setVisibility(View.VISIBLE);
            }
        }
    }
/*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
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
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("marker"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);
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
    }*/
}