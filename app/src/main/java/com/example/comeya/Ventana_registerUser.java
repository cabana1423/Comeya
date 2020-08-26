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

public class Ventana_registerUser extends AppCompatDialogFragment {
    private EditText nombre,email,password;

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
                        Toast.makeText(getContext(), "realizaste un pedido", Toast.LENGTH_LONG).show();
                    }
                });
        nombre =view.findViewById(R.id.ventanaregister_nombre);
        email =view.findViewById(R.id.ventanaregister_email);
        password =view.findViewById(R.id.ventanaregister_password);
        return builder.create();
    }
}
