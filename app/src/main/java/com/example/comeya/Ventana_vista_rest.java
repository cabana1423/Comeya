package com.example.comeya;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.google.android.gms.maps.MapView;

public class Ventana_vista_rest extends AppCompatDialogFragment {
    private TextView nombre,propietario,nit,telefono,direccion;
    private ImageView foto_rest;
    private MapView mapa;

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
        mapa =view.findViewById(R.id.VistaRest_mapa);
        return builder.create();
    }
}
