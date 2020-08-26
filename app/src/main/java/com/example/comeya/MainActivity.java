package com.example.comeya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button boton_login ;
    Button boton_singin;
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
                startActivity(new Intent(MainActivity.this, entrada.class ));
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