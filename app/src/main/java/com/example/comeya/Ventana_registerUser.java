package com.example.comeya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ventana_registerUser extends AppCompatDialogFragment {
    private EditText nombre,email,password;
    Context context;
    private String comprobante;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ventana_register_user,null);
        builder.setView(view)
                .setTitle("registro")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AsyncHttpClient clien=new AsyncHttpClient();
                        RequestParams params=new RequestParams();
                        params.add("nombre", nombre.getText().toString());
                        params.add("email", email.getText().toString());
                        params.add("password", password.getText().toString());
                        clien.post(EndPoints.SERVICE_REGISTER_USER,params,new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    if(response.has("email")){
                                        Toast.makeText(context,response.getString("email")+" fue registrado",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(context,response.getString("msn")+"",Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
        context=getContext();
        nombre =view.findViewById(R.id.ventanaregister_nombre);
        email =view.findViewById(R.id.ventanaregister_email);
        password =view.findViewById(R.id.ventanaregister_password);
        return builder.create();
    }
}
