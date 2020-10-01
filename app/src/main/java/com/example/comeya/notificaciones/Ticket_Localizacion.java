package com.example.comeya.notificaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comeya.Confirmar_pedido;
import com.example.comeya.R;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.FacData;
import com.example.comeya.utils.PedidoData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.WriterException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import cz.msebera.android.httpclient.Header;

import static com.example.comeya.utils.EndPoints.MAPVIEW_BUNDLE_KEY;
import static com.loopj.android.http.AsyncHttpClient.log;

public class Ticket_Localizacion extends AppCompatActivity implements OnMapReadyCallback {
    TextView calle;
    MapView mapa;
    ImageView QR;
    double lat,lon;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String enlaceQR="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket__localizacion);
        calle=findViewById(R.id.Ticket_Text_Calle);
        mapa=findViewById(R.id.Ticket_mapView);
        QR=findViewById(R.id.Ticket_imgQR);
        initGoogleMap(savedInstanceState);
        generarQR();
    }

    private void generarQR() {
        enlaceQR="geo:"+FacData.latitud+","+FacData.longitud+"?q="+FacData.latitud+","+FacData.longitud;
        if(enlaceQR.length()>0){
            WindowManager manager=(WindowManager)getSystemService(WINDOW_SERVICE);
            Display display=manager.getDefaultDisplay();
            Point point=new Point();
            display.getSize(point);
            int width=point.x;
            int height=point.y;
            int smalldimension=width<height ? width:height;
            smalldimension=smalldimension*3/4;
            qrgEncoder=new QRGEncoder(enlaceQR,null, QRGContents.Type.TEXT,smalldimension);
            try {
                bitmap=qrgEncoder.encodeAsBitmap();
                QR.setImageBitmap(bitmap);
            } catch (WriterException e) {
                log.v("errores QR",e.toString());
            }
        }else {
            Toast.makeText(getApplicationContext(),"error al ingrsar parametros",Toast.LENGTH_LONG).show();
        }
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapa.onCreate(mapViewBundle);
        mapa.getMapAsync(this);
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
        try {
            lat= Double.parseDouble(FacData.latitud);
            lon= Double.parseDouble(FacData.longitud);
            LatLng direccion=new LatLng(lat,lon);
            Marker mimarker = googleMap.addMarker(new MarkerOptions()
                    .title("Este es el Lugar")
                    .position(direccion)
            );
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(direccion,16));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
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