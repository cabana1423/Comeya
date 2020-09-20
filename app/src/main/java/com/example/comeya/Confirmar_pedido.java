package com.example.comeya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.MenuData;
import com.example.comeya.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Confirmar_pedido extends AppCompatActivity {
    Context context;
    RecyclerView recyclerViewmenu;
    LinearLayoutManager lnlist;
    confirmarpedido_adapter adaptadorlist;
    private Button pedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        pedido=(Button)findViewById(R.id.confirmarP_pedir);

        recyclerViewmenu=findViewById(R.id.listConfirmar);
        lnlist =new GridLayoutManager(context,1);
        adaptadorlist =new confirmarpedido_adapter(context);
        recyclerViewmenu.setLayoutManager(lnlist);
        recyclerViewmenu.setAdapter(adaptadorlist);
        obtenerlist();

    }
    private void obtenerlist() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_GET_ORDER+ MenuData.TOKER_ORDER,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        adaptadorlist.add(new confirmarpedidoView(obj.getString("nombre_menu"),obj.getString("cantidad"),
                                obj.getString("pago_total"),obj.getString("_id")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}