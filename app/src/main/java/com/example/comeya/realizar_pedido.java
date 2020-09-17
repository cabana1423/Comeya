package com.example.comeya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.comeya.utils.EndPoints;
import com.example.comeya.utils.UserDataServer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class realizar_pedido extends AppCompatActivity {
    RecyclerView recyclerViewpedido;
    LinearLayoutManager lnmypedido;
    Pedido_adapter adaptadorproducto;
    private FloatingActionButton siguiente;
    private CardView ver_rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        recyclerViewpedido =findViewById(R.id.Pedido_productosList);
        lnmypedido =new GridLayoutManager(this,1);
        adaptadorproducto =new Pedido_adapter(this);
        recyclerViewpedido.setLayoutManager(lnmypedido);
        recyclerViewpedido.setAdapter(adaptadorproducto);
        siguiente=(FloatingActionButton)findViewById(R.id.Pedido_buton_siguiente);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(realizar_pedido.this, Confirmar_pedido.class ));
            }
        });
        ver_rest=(CardView)findViewById(R.id.contenedor_rest_pedido);
        ver_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentana();
            }
        });
        obtener_pedido();
    }

    private void obtener_pedido() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Authorization", UserDataServer.TOKEN);
        client.get(EndPoints.SERV_VIEWMENU,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject obj =response.getJSONObject(i);
                        adaptadorproducto.add(new PedidoView(obj.getString("nombre_menu"),obj.getString("descripcion")
                                ,obj.getString("precio"),obj.getString("foto_produc")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void openVentana() {
        Ventana_vista_rest ventana_vista_rest=new Ventana_vista_rest();
        ventana_vista_rest.show(getSupportFragmentManager(),"example dialogo");
    }



    /*private List<PedidoView> obtenerproduc() {
        List<PedidoView> produc = new ArrayList<>();
        produc.add(new PedidoView("San isidro", "un rico manjar lleno de sabor y deliciosa salsa barbacu 100 porciento natural", "200 bs", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));
        produc.add(new PedidoView("San isidro", "200 bs", "un bacalao ccon buen sabor", R.drawable.image_defect));

        return produc;
    }*/
}