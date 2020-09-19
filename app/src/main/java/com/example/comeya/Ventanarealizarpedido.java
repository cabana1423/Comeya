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

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.RestData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ventanarealizarpedido extends AppCompatDialogFragment {
    private TextView cantidad, GetCantidad;
    private SeekBar seekBar;
    String cantidad_text;

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
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.addHeader("Authorization", UserDataServer.TOKEN);
                        RequestParams params = new RequestParams();
                        params.add("cantidad", cantidad_text);
                        client.post(EndPoints.SERV_POST_ORDER+MenuData.ID_AUX_IDMENU+"&id_u="+UserDataServer.ID, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }
                        });
                    }
                });
        GetCantidad=view.findViewById(R.id.groupviewpedido);
        cantidad =view.findViewById(R.id.ventanapedido_title);
        seekBar =view.findViewById(R.id.ventanapedido_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cantidad.setText(i+"/20");
                cantidad_text= String.valueOf(i);
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
