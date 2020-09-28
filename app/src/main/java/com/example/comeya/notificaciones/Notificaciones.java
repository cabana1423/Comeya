package com.example.comeya.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.comeya.Menuview;
import com.example.comeya.R;
import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Notificaciones extends AppCompatActivity {
    RecyclerView recyclerViewSolicitudP;
    LinearLayoutManager lnPed;
    Noti_solicitarpedido_Adapter noti_solicitarpedido_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        recyclerViewSolicitudP =findViewById(R.id.Lista_SolicitudPedido);
        lnPed =new GridLayoutManager(this,1);
        noti_solicitarpedido_adapter =new Noti_solicitarpedido_Adapter(this);
        recyclerViewSolicitudP.setLayoutManager(lnPed);
        recyclerViewSolicitudP.setAdapter(noti_solicitarpedido_adapter);
        cargarvista();
    }

    private void cargarvista() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GET_ADMI_FAC +UserDataServer.ID,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        noti_solicitarpedido_adapter.add(new Noti_solicitarpedido_View("https://image.winudf.com/v2/image1/Y29tZXJjaW8uY29tLnF1YWxpZmF6LlBlZGlkb3NNb3ZlbF9pY29uXzE1NDkxNTMwMTJfMDgz/icon.png?w=170&fakeurl=1"
                                ,"tienes un nuevo pedido.. Revisa para administrar el servicio",obj.getString("_id")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}