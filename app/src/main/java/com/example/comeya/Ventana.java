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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static com.example.comeya.crear_Myrest.CODE_PERMISSION;

public class Ventana extends AppCompatDialogFragment implements View.OnClickListener {
    private EditText nombre,precio,descripcion;
    private ImageButton imgmenu;
    private Button cargarimg;
    public static String path;
    Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ventana_crearmenu,null);
        builder.setView(view)
                .setTitle("Agregar nuevo menu")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getContext(), "realizaste un pedido", Toast.LENGTH_LONG).show();
                        crear_menu();
                    }
                });
        nombre =view.findViewById(R.id.ventanamenu_nombre);
        precio =view.findViewById(R.id.ventanamenu_precio);
        descripcion =view.findViewById(R.id.ventanamenu_decrip);
        imgmenu=view.findViewById(R.id.ventanamenu_img);
        cargarimg=view.findViewById(R.id.ventanamenu_cargarimg);
        cargarimg.setOnClickListener(this);
        cargarimg.setVisibility(View.INVISIBLE);
        if (permission()) {
            cargarimg.setVisibility(View.VISIBLE);
        }
        imgmenu.setOnClickListener(this);

        return builder.create();
    }

    private void crear_menu() {
        if (path != null) {
            AsyncHttpClient clientrest = new AsyncHttpClient();
            clientrest.addHeader("Authorization", UserDataServer.TOKEN);
            RequestParams params = new RequestParams();
            params.add("nombre_menu", nombre.getText().toString());
            params.add("precio", precio.getText().toString());
            params.add("descripcion", descripcion.getText().toString());
            clientrest.post(EndPoints.SERV_POSTMENU + RestData.ID_AUX_REST+"&idim="+MenuData.IDIM, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            Toast.makeText(getContext(), "agrega una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ventanamenu_img:
                cargarimg();
                break;
            case R.id.ventanamenu_cargarimg:
                enviar_img();
                break;
        }
    }

    private void enviar_img() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        RequestParams req = new RequestParams();
        if (path != null) {
            Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();
            File file = new File(path);
            try {
                req.put("img", file);
            } catch (Exception e) {
                Toast.makeText(getContext(), "error al enviar la imagen al sevidor la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "ingrese una imagen", Toast.LENGTH_SHORT).show();
        }
        client.post(EndPoints.SERV_CARG_IMGMENU+ RestData.ID_AUX_REST, req, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.has("_id"))
                        MenuData.IDIM = response.getString("_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void cargarimg() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecione la imagen"), 10);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path1 = data.getData();
            path = getRealPath(path1);
            imgmenu.setImageURI(path1);
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
        if (this.getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSION);
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION == requestCode) {
            if (permissions.length == 3) {
                cargarimg.setVisibility(View.VISIBLE);
            }
        }
    }
}
