package com.example.comeya;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.MapView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static com.example.comeya.crear_Myrest.CODE_PERMISSION;

public class Ventana_editar_rest extends AppCompatDialogFragment {
    private TextView nombre, propietario, telefono, direccion;
    private ImageView foto_rest;
    private Button cargarimg;
    private  String path,url="",id_img="";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ventana_editar_rest, null);
        builder.setView(view)
                .setTitle("ediatr datos restaurante")

                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getContext(), ":) editaste restaurant", Toast.LENGTH_LONG).show();
                        editar_Rest();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        nombre = view.findViewById(R.id.Ventana_editRest_nombre);
        propietario = view.findViewById(R.id.Ventana_editRest_propietario);
        telefono = view.findViewById(R.id.Ventana_editRest_telefomo);
        direccion = view.findViewById(R.id.Ventana_editRest_direccion);
        return builder.create();
    }

    private void editar_Rest() {
            AsyncHttpClient clientrest = new AsyncHttpClient();
            clientrest.addHeader("Authorization", UserDataServer.TOKEN);
            RequestParams params = new RequestParams();
            params.add("nombre_rest", nombre.getText().toString());
            params.add("propietario", propietario.getText().toString());
            params.add("telefono", telefono.getText().toString());
            params.add("calle", direccion.getText().toString());
            clientrest.put(EndPoints.SERV_PUTMYREST + RestData.ID_AUX_REST, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    //Toast.makeText(getActivity(), "" +errorResponse, Toast.LENGTH_SHORT).show();
                }
            });
        }

}
