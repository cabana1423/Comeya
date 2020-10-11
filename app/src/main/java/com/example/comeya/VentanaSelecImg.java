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
                        enviar();
                    }
                });
        foto_rest=view.findViewById(R.id.Ventana_select_Img);
        foto_rest.setVisibility(View.INVISIBLE);
        if (permission()) {
            foto_rest.setVisibility(View.VISIBLE);
        }
        foto_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarimg();
            }
        });


        return builder.create();
    }
    private void cargarimg() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecione la imagen"), 10);
    }

    private void enviar() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        RequestParams req = new RequestParams();
        if (path != null) {
            Toast.makeText(getActivity(), "imagen subida con Exito", Toast.LENGTH_SHORT).show();
            File file = new File(path);
            try {
                req.put("img", file);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "error al enviar la imagen al sevidor la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "ingrese una imagen", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path1 = data.getData();
            path = getRealPath(path1);
            RestData.PATH_IMG=path1;
            foto_rest.setImageURI(path1);
        }
    }

    String getRealPath(Uri uri) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSION);
        return false;
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION == requestCode) {
            if (permissions.length == 3) {
                builder.set.setVisibility(View.VISIBLE);
            }
        }
    }*/

}


