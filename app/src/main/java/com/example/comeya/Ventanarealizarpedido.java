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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Ventanarealizarpedido extends AppCompatDialogFragment {
    private TextView cantidad;
    private SeekBar seekBar;
    String cantidad_text,id_pedido;

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
                        client.get(EndPoints.SERV_GET_TOKER_USER+MenuData.TOKER_ORDER+"&menu="+MenuData.ID_AUX_IDMENU+"&user="+UserDataServer.ID,null,new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                super.onSuccess(statusCode, headers, response);
                                if(response.length() == 0){
                                    post_pedido();
                                }
                                else {
                                    try {
                                        JSONObject object=response.getJSONObject(0);
                                        id_pedido=object.getString("_id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    put_pedido();
                                }
                            }
                        });
                    }
                    private void post_pedido() {
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.addHeader("Authorization", UserDataServer.TOKEN);
                        RequestParams params = new RequestParams();
                        params.add("cantidad", cantidad_text);
                        params.add("toker_order",MenuData.TOKER_ORDER);
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
                    private void put_pedido() {
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.addHeader("Authorization", UserDataServer.TOKEN);
                        RequestParams paramsput = new RequestParams();
                        paramsput.add("cantidad", cantidad_text);
                        client.put(EndPoints.SERV_PUT_ORDER+id_pedido, paramsput, new JsonHttpResponseHandler() {
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
