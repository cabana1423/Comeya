package com.example.comeya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Ventanarealizarpedido extends AppCompatDialogFragment {
    private EditText nombre,precio,descripcion;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ventana_realizarpedido,null);
        builder.setView(view)
                .setTitle("cantidad")
                .setPositiveButton("pedido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "realizaste un pedido", Toast.LENGTH_LONG).show();
                    }
                });
        nombre =view.findViewById(R.id.ventanamenu_nombre);
        precio =view.findViewById(R.id.ventanamenu_precio);
        descripcion =view.findViewById(R.id.ventanamenu_decrip);
        return builder.create();
    }
}
