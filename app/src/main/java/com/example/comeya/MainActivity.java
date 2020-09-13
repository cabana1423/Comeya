package com.example.comeya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Button boton_login ;
    Button boton_singin;
    private MainActivity root=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();

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