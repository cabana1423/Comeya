package com.example.comeya.notificaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comeya.CreatePdf.TemplatePDF;
import com.example.comeya.MainActivity;
import com.example.comeya.R;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.FacData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.WriterException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import cz.msebera.android.httpclient.Header;

import static com.example.comeya.utils.EndPoints.MAPVIEW_BUNDLE_KEY;
import static com.loopj.android.http.AsyncHttpClient.log;

public class Ticket_Localizacion extends AppCompatActivity /*implements OnMapReadyCallback*/ {
    TextView Nombre,pedidos;
    MapView mapa;
    ImageView QR;
    double lat,lon;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String enlaceQR="";
    ImageButton share,print;
    ConstraintLayout constraintLayout;
    private TemplatePDF templatePDF;
    private Uri path;
    //ImageView imageView;
    GoogleMap nMap;
    private Ticket_Localizacion root=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket__localizacion);
        //mapa=findViewById(R.id.Ticket_mapView);
        QR=findViewById(R.id.Ticket_imgQR);
        share =findViewById(R.id.Ticket_imageButton_share);
        print =findViewById(R.id.img_print);
        constraintLayout=findViewById(R.id.Ticketlayout);
        pedidos=findViewById(R.id.ticket_pedidos);
        pedidos.setText(FacData.pedidosFac);
        Nombre=findViewById(R.id.ticket_nombre);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captura();
                ver();
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captura();
                imprimir();
                templatePDF.viewAppPDF(root);
            }
        });
        nombre_user();
        //initGoogleMap(savedInstanceState);
        generarQR();
    }

    private void nombre_user() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERVICE_GET_USER+ FacData.id_user,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                    try {
                        JSONObject obj =response.getJSONObject(0);
                        Nombre.setText(obj.getString("nombre"));
                        log.d("","");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    private void imprimir() {
        templatePDF = new TemplatePDF(getApplicationContext());
        templatePDF.OpenDocument();
        templatePDF.copiarIMG(path);
        templatePDF.closeDocument();
    }

    private void captura() {
        /*Bitmap bitmap =Bitmap.createBitmap(mapa.getWidth(),mapa.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        mapa.draw(canvas);
        imageView.setImageBitmap(bitmap);*/
        Date date =new Date();
        CharSequence now=android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);
        String filename = Environment.getExternalStorageDirectory()+"/ScreenShooter/"+FacData.idFac+".jpg";

        View root =getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap =Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file=new File(filename);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Uri uri=Uri.fromFile(file);
            path=uri;
        }catch (FileNotFoundException e){
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void ver(){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "image/*");
        try {
            startActivityForResult(intent.createChooser(intent, "Selecione la aplicacion"), 10);
            //activity.startActivity(intent);
        }catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
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
    /*
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
        nMap=googleMap;
        try {
            lat= Double.parseDouble(FacData.latitud);
            lon= Double.parseDouble(FacData.longitud);
            LatLng direccion=new LatLng(lat,lon);
            Marker mimarker = nMap.addMarker(new MarkerOptions()
                    .title("Este es el Lugar")
                    .position(direccion)
            );
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(direccion,16));

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
    }*/

}