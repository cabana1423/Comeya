package com.example.comeya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.FacData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class MainActivity extends AppCompatActivity {
    Button boton_login ;
    Button boton_singin;
    public static final String TAG="MyFirebaseMsgService";
    private MainActivity root=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
        boton_singin =(Button)findViewById(R.id.singin);
        boton_login =(Button)findViewById(R.id.login);

        boton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncHttpClient clien=new AsyncHttpClient();
                EditText email =root.findViewById(R.id.email);
                EditText password =root.findViewById(R.id.pass);
                RequestParams params=new RequestParams();
                params.add("email", email.getText().toString());
                params.add("password", password.getText().toString());
                clien.post(EndPoints.SERVICE_LOGIN,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if(response.has("msn"))
                                UserDataServer.MSN=response.getString("msn");
                            if (response.has("token"))
                                UserDataServer.TOKEN=response.getString("token");
                            if(response.has("id"))
                                UserDataServer.ID=response.getString("id");
                            if(UserDataServer.TOKEN.length()>150){
                                obtenertoken();
                                obtenerTopic();
                                startActivity(new Intent(MainActivity.this, entrada.class ));
                            }else{
                                Toast.makeText(getApplicationContext(),response.getString("msn"),Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        boton_singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        crearcarpetaPDFs();

    }

    private void crearcarpetaPDFs() {
        File folder=new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if(!folder.exists()){
            folder.mkdir();
        }
        //Uri uri2=Uri.fromFile(folder);
        FacData.PathPdfs=folder;
        FacData.PathPdfs_Uri=Uri.fromFile(folder);
        log.d("CARPETA CREADA",""+folder);
        //log.d("copia CREADA",""+FacData.PathPdfs);
    }

    private void obtenerTopic() {
        Log.d("MainActivity","registerlng");
        FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("MainActivity","suscribed");
                }else
                    Log.d("MainActivity","nor suscribed"+task.getException());
            }
        });
    }

    private void obtenertoken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    log.d("Mainactivity","error al obtener el token",task.getException());
                    return;
                }
                String token=task.getResult().getToken();
                UserDataServer.TOKEN_FB=token;
                Log.d("MainActivity",UserDataServer.TOKEN_FB);
                putToken();
            }
        });
    }

    private void putToken() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        RequestParams params=new RequestParams();
        params.add("tokenFB", UserDataServer.TOKEN_FB);
        client.put(EndPoints.SERVICE_PUT_USER +UserDataServer.ID,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),errorResponse+"",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openVentana() {
        Ventana_registerUser ventana_registerUser=new Ventana_registerUser();
        ventana_registerUser.show(getSupportFragmentManager(),"Usuario Registrado");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
    }
}