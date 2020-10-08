package com.example.comeya.notificaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comeya.R;
import com.example.comeya.utils.FacData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

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
    ImageButton print;
    ConstraintLayout constraintLayout;
    //ImageView imageView;
    GoogleMap nMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket__localizacion);
        calle=findViewById(R.id.Ticket_Text_Calle);
        mapa=findViewById(R.id.Ticket_mapView);
        QR=findViewById(R.id.Ticket_imgQR);
        print=findViewById(R.id.Ticket_imageButton);
        constraintLayout=findViewById(R.id.Ticketlayout);
        //imageView=findViewById(R.id.imageView0616);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captura();
                //CaptureMapScreen();
            }
        });
        initGoogleMap(savedInstanceState);
        generarQR();
    }

    private void captura() {
        /*Bitmap bitmap =Bitmap.createBitmap(mapa.getWidth(),mapa.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        mapa.draw(canvas);
        imageView.setImageBitmap(bitmap);*/
        Date date =new Date();
        CharSequence now=android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);
        String filename = Environment.getExternalStorageDirectory()+"/ScreenShooter/"+now+".jpg";

        View root =getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap =Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file=new File(filename);
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Uri uri=Uri.fromFile(file);
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            try {
                startActivityForResult(intent.createChooser(intent, "Selecione la aplicacion"), 10);
                //activity.startActivity(intent);
            }catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }

            //intent.setDataAndType(uri, "image/*");
            //startActivity(intent);
        }catch (FileNotFoundException e){
            e.printStackTrace();

        } catch (IOException e) {
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
    }

}