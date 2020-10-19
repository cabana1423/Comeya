package com.example.comeya;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class VentanaSelecImg extends AppCompatDialogFragment {
    private ImageButton foto_rest;
    public static String path;
    static final int CODE_PERMISSION = 200;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ventana_selecionar_img,null);
        builder.setView(view)
                .setTitle("")
                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //enviar();
                    }
                });
        foto_rest=view.findViewById(R.id.Ventana_select_Img);
        foto_rest.setVisibility(View.INVISIBLE);
       /* if (permission()) {
            foto_rest.setVisibility(View.VISIBLE);
        }
        foto_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarimg();
            }
        });*/


        return builder.create();
    }


}


