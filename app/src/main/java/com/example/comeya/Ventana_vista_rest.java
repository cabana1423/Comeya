package com.example.comeya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import com.example.comeya.utils.UserDataServer;
import com.google.android.gms.maps.MapView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ventana_vista_rest extends AppCompatDialogFragment {
    private TextView nombre,propietario,nit,telefono,direccion;
    private ImageView foto_rest;
    //private MapView mapa;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ventana_vista_rest,null);
        builder.setView(view)
                .setTitle("Vista previa restaurante")
                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        nombre =view.findViewById(R.id.VistaRest_nombre);
        propietario =view.findViewById(R.id.VistaRest_propietario);
        nit =view.findViewById(R.id.VistaRest_nit);
        telefono =view.findViewById(R.id.VistaRest_telefono);
        direccion =view.findViewById(R.id.VistaRest_direccion);
        foto_rest =view.findViewById(R.id.Vistarest_img);
        //mapa =view.findViewById(R.id.VistaRest_mapa);
        llenarVista();
        return builder.create();
    }

    private void llenarVista() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GETMYREST+ MenuData.ID_AUX_rest,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(0);
                        nombre.setText(obj.getString("nombre_rest"));
                        propietario.setText(obj.getString("propietario"));
                        nit.setText(obj.getString("nit"));
                        telefono.setText(obj.getString("telefono"));
                        direccion.setText(obj.getString("calle"));
                        Glide.with(getActivity()).load(obj.getString("foto_lugar")).centerCrop().into(foto_rest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
