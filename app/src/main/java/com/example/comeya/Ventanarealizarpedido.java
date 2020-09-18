package com.example.comeya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Ventanarealizarpedido extends AppCompatDialogFragment {
    private TextView cantidad;
    private SeekBar seekBar;
    private String resultado;

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
                        //Toast.makeText(getContext(), "realizaste un pedido", Toast.LENGTH_LONG).show();
                        Bundle bundle=new Bundle();
                        bundle.putString("cantidad",resultado.trim());
                        getParentFragmentManager().setFragmentResult("key",bundle);
                    }
                });
        cantidad =view.findViewById(R.id.ventanapedido_title);
        seekBar =view.findViewById(R.id.ventanapedido_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cantidad.setText(i+"/20");
                resultado= String.valueOf(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return builder.create();
    }
}
